package com.wangyameng.service.admin;

import cn.hutool.jwt.JWT;
import com.wangyameng.entity.SysAdminLog;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName AdminLogService.java
 * @Description 日志服务类
 * @createTime 2023-11-06 14:56:00
 */
public interface AdminLogService {
    /**
     * 写日志
     * @param title 标题
     * @param content 内容
     * @param jwt jwt
     */
    public void writeLog(String title, String content, JWT jwt);

}
