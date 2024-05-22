package com.wangyameng.service.admin;

import com.alibaba.fastjson2.JSONArray;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.SysAttachmentCateDao;
import com.wangyameng.entity.SysAttachmentCate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName AttachmentCateServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-06 23:17:00
 */

@Service("adminAttachmentCateService")
public class AttachmentCateServiceImpl implements AttachmentCateService {
    @Autowired
    private SysAttachmentCateDao sysAttachmentCateDao;
    @Override
    public AjaxResult getCateList() {
        List<SysAttachmentCate> sysAttachmentCates = sysAttachmentCateDao.selectList(null);
        JSONArray jsonArray = new JSONArray();
        for (SysAttachmentCate sysAttachmentCate : sysAttachmentCates) {
            jsonArray.add(PubfuncUtil.parseToUnderlineJson(sysAttachmentCate));
        }
        return AjaxResult.dataReturn(0,"success", PubfuncUtil.makeTree(jsonArray));
    }
}
