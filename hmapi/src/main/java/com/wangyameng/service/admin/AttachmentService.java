package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName AttachmentService.java
 * @Description TODO
 * @createTime 2024-05-06 23:01:00
 */
public interface AttachmentService {
    AjaxResult getAttachmentList(String cateId, int page, int limit);

    AjaxResult uploadPicture(MultipartFile file, String cateId);
}
