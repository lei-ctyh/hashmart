package com.wangyameng.api.admin.system;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dao.SysAttachmentDao;
import com.wangyameng.entity.SysAttachment;
import com.wangyameng.service.admin.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName AttachmentController.java
 * @Description TODO
 * @createTime 2024-05-06 23:00:00
 */
@RestController
public class AttachmentController {
    @Autowired
    AttachmentService attachmentService;

    @GetMapping("/admin/system.attachment/index")
    public AjaxResult index(@RequestParam("cate_id") String cateId, int page, int limit) throws Exception {
        return attachmentService.getAttachmentList(cateId, page, limit);
    }
    @PostMapping("/admin/system.attachment/uploadPicture")
    public AjaxResult uploadPicture(@RequestParam("file") MultipartFile file, @RequestParam("cate_id") String cateId) throws Exception {
        return attachmentService.uploadPicture(file, cateId);
    }
}
