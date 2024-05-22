package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.redis.RedisCacheUtil;
import com.wangyameng.dao.SysAdminDao;
import com.wangyameng.dao.SysAdminLogDao;
import com.wangyameng.dao.SysMenuDao;
import com.wangyameng.dao.SysRoleDao;
import com.wangyameng.dto.LoginDTO;
import com.wangyameng.entity.SysAdmin;
import com.wangyameng.entity.SysMenu;
import com.wangyameng.entity.SysRole;
import com.wangyameng.service.admin.LoginService;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName LoginServiceImpl.java
 * @Description TODO
 * @createTime 2023-11-03 13:48:00
 */

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RedisCacheUtil redisCacheUtil;
    @Autowired
    SysAdminDao sysAdminDao;
    @Autowired
    SysRoleDao sysRoleDao;
    @Autowired
    SysMenuDao sysMenuDao;
    @Autowired
    SysAdminLogDao sysAdminLogDao;

    @Override
    public AjaxResult doLogin(LoginDTO loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        String captcha = loginDto.getCaptcha();
        String key = loginDto.getKey();

        // 获取redis中的验证码
        String redisCode = redisCacheUtil.getCacheObject(key);
        // 判断验证码
        if (captcha == null || !StringUtils.equals(redisCode, captcha.trim().toLowerCase())) {
            return AjaxResult.dataReturn(-5, "验证码错误");
        }
        // 获取用户信息
        LambdaQueryWrapper<SysAdmin> adminWrapper = new QueryWrapper<SysAdmin>().lambda();
        adminWrapper.eq(SysAdmin::getUsername, username);
        SysAdmin sysAdmin = sysAdminDao.selectOne(adminWrapper);
        if (sysAdmin == null) {
            return AjaxResult.dataReturn(-2, "用户名密码错误");
        }

        // 验证密码
        String encryptPwd = PubfuncUtil.makePassword(password, sysAdmin.getSalt());
        String dbPwd = sysAdmin.getPassword();
        if (StringUtils.equals(encryptPwd, dbPwd)) {
            return AjaxResult.dataReturn(-3, "用户密码错误");
        }

        if (sysAdmin.getStatus() == 2) {
            return AjaxResult.dataReturn(-4, "该账号已被禁用");
        }

        // roleid为1应该是超级管理员
        LambdaQueryWrapper<SysRole> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.eq(SysRole::getId, sysAdmin.getRoleId())
                   .eq(SysRole::getStatus, 1);
        SysRole sysRole = sysRoleDao.selectOne(roleWrapper);
        if (sysRole == null) {
            return AjaxResult.dataReturn(-5, "该账号已被禁用");
        }

        HashMap<String, Object> payload = new HashMap<>(4);
        payload.put("id", sysAdmin.getId());
        payload.put("role_id", sysAdmin.getRoleId());
        payload.put("nickname", sysAdmin.getNickname());
        payload.put("avatar", sysAdmin.getAvatar());
        String token = PubfuncUtil.setJWT(payload);

        // 获取菜单权限
        JSONArray menuJsonArr = getMenuList(sysRole);
        JSONArray menuTree = PubfuncUtil.makeTree(menuJsonArr);

        // 记录权限map
        String authMapKey = sysAdmin.getId() + "_auth_map";
        Map<String, String> authMap = new HashMap<>(16);
        for (Object o : menuJsonArr) {
            JSONObject menuJson = (JSONObject) o;
            String auth = StringUtils.lowerCase(menuJson.getString("auth"));
            if (com.wangyameng.common.util.text.StringUtils.isNotBlank(auth)) {
                authMap.put(auth, "1");
            }
        }
        redisCacheUtil.setCacheMap(authMapKey, authMap);


        // todo 记录登录日志


        JSONObject userInfo = new JSONObject();
        userInfo.put("id", sysAdmin.getId());
        userInfo.put("role_id", sysAdmin.getRoleId());
        userInfo.put("userName", sysAdmin.getNickname());
        userInfo.put("avatar", sysAdmin.getAvatar());

        JSONObject rtnData = new JSONObject();
        rtnData.put("userInfo", userInfo);
        rtnData.put("token", token);
        rtnData.put("menu", menuTree);
        return AjaxResult.dataReturn(0, "登录成功", rtnData);
    }

    @Override
    public AjaxResult createCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        redisCacheUtil.setCacheObject(key, verCode, 30, TimeUnit.MINUTES);
        // 将key和base64返回给前端
        JSONObject data = new JSONObject();
        data.put("key", key);
        data.put("img", specCaptcha.toBase64());
        return AjaxResult.dataReturn(0, "success", data);
    }

    /**
     * 获取角色权限
     *
     * @param sysRole 角色entity
     * @return 权限集合
     */
    private JSONArray getMenuList(SysRole sysRole) {
        JSONArray menuJsonArr = new JSONArray();
        // 超级管理员直接是全部权限
        LambdaQueryWrapper<SysMenu> menuWrapper = null;
        if (sysRole.getId() != 1) {
            menuWrapper = new LambdaQueryWrapper<>();
            menuWrapper.eq(SysMenu::getStatus, 1)
                       .in(SysMenu::getId, sysRole.getRule())
                       .orderByDesc(SysMenu::getSort);
        }
        List<SysMenu> menuList = sysMenuDao.selectList(menuWrapper);
        for (SysMenu sysMenu : menuList) {
            JSONObject menuJson = new JSONObject();
            menuJson.put("id", sysMenu.getId());
            menuJson.put("pid", sysMenu.getPid());
            menuJson.put("auth", sysMenu.getAuth());
            menuJson.put("type", sysMenu.getType());
            menuJson.put("name", sysMenu.getName());
            menuJson.put("path", sysMenu.getPath());
            menuJson.put("icon", sysMenu.getIcon());

            JSONObject metaJson = new JSONObject();
            metaJson.put("icon", sysMenu.getIcon());
            metaJson.put("title", sysMenu.getName());
            metaJson.put("type", "menu");
            metaJson.put("hidden", sysMenu.getType() == 2);
            menuJson.put("meta", metaJson);

            if (sysMenu.getPid() != 0) {
                menuJson.put("component", sysMenu.getComponent());
            }
            menuJsonArr.add(menuJson);
        }
        return menuJsonArr;

    }


}
