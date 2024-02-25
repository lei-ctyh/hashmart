package com.wangyameng.api.uniapp.common;

import com.alibaba.fastjson2.JSONArray;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.UserAgreeDao;
import com.wangyameng.dao.UserDao;
import com.wangyameng.entity.UserAgree;
import com.wangyameng.service.uniapp.impl.CommonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName CommonController.java
 * @Description TODO
 * @createTime 2023-11-14 17:47:00
 */

@RestController
public class CommonController {
    @Resource(name = "uniappCommonService")
    CommonService commonService;

    @GetMapping("uniapp/common/getMusic")
    public AjaxResult getMusic(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        requestURL = requestURL.replace("uniapp/common/getMusic", "voice/music.mp3");
        return AjaxResult.dataReturn(0, "success", requestURL);
    }

    /**
     * 获取用户头像列表
     */
    @GetMapping("uniapp/common/getAvatar")
    public AjaxResult getavatar()
    {
        return AjaxResult.dataReturn(0, "success", JSONArray.parseArray(PubfuncUtil.getRandAvatar(3)));
    }@GetMapping("uniapp/common/userAgreement")
    public AjaxResult userAgreement(String type)
    {
        return commonService.getUserAgreement(type);

    }

}
