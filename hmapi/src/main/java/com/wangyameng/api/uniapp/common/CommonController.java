package com.wangyameng.api.uniapp.common;

import com.wangyameng.common.core.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("uniapp/common/getMusic")
    public AjaxResult getMusic(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        requestURL = requestURL.replace("uniapp/common/getMusic", "voice/music.mp3");
        return AjaxResult.dataReturn(0, "success", requestURL);
    }
}
