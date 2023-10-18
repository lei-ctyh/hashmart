package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.SysAttachmentDao;
import com.wangyameng.entity.SysAttachment;
import com.wangyameng.service.SysAttachmentService;
import org.springframework.stereotype.Service;

/**
 * 附件表(SysAttachment)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("sysAttachmentService")
public class SysAttachmentServiceImpl extends ServiceImpl<SysAttachmentDao, SysAttachment> implements SysAttachmentService {

}

