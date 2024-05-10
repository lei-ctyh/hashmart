package com.wangyameng.api.admin.system;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.admin.AttachmentCateService;
import com.wangyameng.service.admin.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName AttachmentCateController.java
 * @Description TODO
 * @createTime 2024-05-06 23:15:00
 */
@RestController
public class AttachmentCateController {
    @Autowired
    private AttachmentCateService attachmentCateService;
    @GetMapping("/admin/system.attachmentCate/index")
    public AjaxResult index() throws Exception {
        return   attachmentCateService.getCateList();
    }

}
