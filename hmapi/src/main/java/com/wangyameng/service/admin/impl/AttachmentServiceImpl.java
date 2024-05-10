package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.SysAttachmentDao;
import com.wangyameng.entity.SysAttachment;
import com.wangyameng.service.admin.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName AttachmentServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-06 23:02:00
 */
@Service("adminAttachmentService")
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private SysAttachmentDao sysAttachmentDao;
    @Override
    public AjaxResult getAttachmentList(String cateId, int page, int limit) {
        LambdaQueryWrapper<SysAttachment> queryWrapper = new LambdaQueryWrapper<>();
        if (cateId!= null) {
            queryWrapper.eq(SysAttachment::getCateId, cateId);
        }
        queryWrapper.orderByDesc(SysAttachment::getCreateTime);
        Page<SysAttachment> iPage = sysAttachmentDao.selectPage(new Page<>(page, limit), queryWrapper);
        JSONObject rtnData = new JSONObject();
        rtnData.put("total", iPage.getTotal());
        rtnData.put("per_page", iPage.getSize());
        rtnData.put("current_page", iPage.getCurrent());
        rtnData.put("last_page", iPage.getPages());
        List<SysAttachment> records = iPage.getRecords();
        for (SysAttachment record : records) {
            record.setUrl(PubfuncUtil.replaceBecomeServerHost(record.getUrl()));
        }
        rtnData.put("rows", PubfuncUtil.paginate(iPage));
        return AjaxResult.dataReturn(0,"success", rtnData);
    }

    @Override
    public AjaxResult uploadPicture(MultipartFile file, String cateId) {
        /*
         // 可上传的默认图片后缀
         'ext' =>'jpg|png|bmp|jpeg|gif',
         // 图片允许类型
         'acceptMime' =>'image/jpg,image/png,image/bmp,image/jpeg,image/gif',
         // 上传的视频的后缀
         'video_ext' =>'mp4|avi|flv',
         // 上传的文件后缀
         'txt' =>'txt|pem'
         */
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件后缀
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

        String[] extArr = StringUtils.split("jpg|png|bmp|jpeg|gif", "|");
        // 判断文件后缀是否允许上传
        if (!Arrays.asList(extArr).contains(fileExt)){
            return AjaxResult.dataReturn(0,"success", "图片类型有误");
        }

        return null;
    }
}
