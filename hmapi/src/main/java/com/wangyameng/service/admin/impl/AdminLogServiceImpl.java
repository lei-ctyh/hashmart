package com.wangyameng.service.admin.impl;

import cn.hutool.jwt.JWT;
import com.wangyameng.common.core.ServiceException;
import com.wangyameng.dao.SysAdminLogDao;
import com.wangyameng.entity.SysAdminLog;
import com.wangyameng.service.admin.AdminLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName AdminLogServiceimpl.java
 * @Description TODO
 * @createTime 2023-11-06 15:00:00
 */
@Service
public class AdminLogServiceImpl implements AdminLogService {
    @Autowired
    private SysAdminLogDao sysAdminLogDao;
    @Override
    public void writeLog(String title, String content, JWT jwt) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        Object userInfo = jwt.getPayload("data");
        if (userInfo == null) {
            throw new ServiceException("用户权限信息异常");
        }

        SysAdminLog adminLog = new SysAdminLog();
        adminLog.setAdminId(new Object());
        adminLog.setUsername("");
        adminLog.setUrl(request.getRequestURI());
        adminLog.setTitle("");
        adminLog.setData("");
        adminLog.setIp(request.getLocalAddr());
        adminLog.setUserAgent(request.getHeader("user-agent"));
        adminLog.setCreateTime(new Date());

        sysAdminLogDao.insert(adminLog);

    }
}
