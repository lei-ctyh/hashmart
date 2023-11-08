package com.wangyameng.service;

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
     * 记录日志信息到hashmart_admin_log表中
     */
    void writeLog(String title, String content);

}
