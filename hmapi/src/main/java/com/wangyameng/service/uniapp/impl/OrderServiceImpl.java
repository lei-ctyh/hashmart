package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.UserSessionContext;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.BlindboxDao;
import com.wangyameng.dao.UserDao;
import com.wangyameng.entity.Blindbox;
import com.wangyameng.entity.User;
import com.wangyameng.service.uniapp.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName OrderServiceImpl.java
 * @Description TODO
 * @createTime 2024-04-11 10:45:00
 */
@Service("uniappOrderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private BlindboxDao blindboxDao;
    @Autowired
    private UserDao userDao;


    @Override
    public AjaxResult trail(Integer blindboxId, Integer num, Integer useIntegral, Integer type) {
        // 查询盲盒信息
        Blindbox blindbox = blindboxDao.selectById(blindboxId);
        if (blindbox == null) {
            return AjaxResult.dataReturn(-1, "盲盒已下架");
        }
        // 查询用户余额
        Integer userId = UserSessionContext.get().getInteger("id");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, userId);
        queryWrapper.eq(User::getStatus, 1);
        User user = userDao.selectOne(queryWrapper);
        if (user == null) {
            return AjaxResult.dataReturn(-1, "用户信息不存在");
        }

        BigDecimal priceBd = BigDecimal.valueOf(blindbox.getPrice());
        BigDecimal numBigBd = BigDecimal.valueOf(num);
        BigDecimal totalPrice = priceBd.multiply(numBigBd);

        // 可使用的积分
        BigDecimal canUseIntegral = BigDecimal.valueOf(0L);
        // 获取用户可用余额
        BigDecimal canDeductionAmount = BigDecimal.valueOf(0L);
        // 获取兑换比例为 多少ratio兑换成一元人民币，ratio最低为1
        BigDecimal ratio = BigDecimal.valueOf(Long.parseLong(PubfuncUtil.getSdParam("sys_base", "change_ratio")));
        if (ratio.compareTo(BigDecimal.ONE) < 0) {
            return AjaxResult.dataReturn(-1, "Hash币兑换比例异常，请联系管理人员");
        }
        if (user.getIntegral() > 0) {
            // 可抵扣金额
            canDeductionAmount = BigDecimal.valueOf(user.getIntegral()).divide(ratio, 2, RoundingMode.DOWN);

            // 如果可抵扣金额大于订单金额
            if (canDeductionAmount.compareTo(totalPrice) > 0) {
                canUseIntegral = totalPrice.multiply(ratio);
                canDeductionAmount = totalPrice;
                if (useIntegral == 1) {
                    totalPrice = BigDecimal.valueOf(0L);
                }

            } else {
                if (useIntegral == 1) {
                    totalPrice = totalPrice.subtract(canDeductionAmount);
                }
                canUseIntegral = BigDecimal.valueOf(user.getIntegral());
            }

        }
        JSONObject trailData = new JSONObject();
        trailData.put("blindbox_img", PubfuncUtil.replaceBecomeServerHost(blindbox.getPic()));
        trailData.put("price", blindbox.getPrice());
        trailData.put("num", num);
        trailData.put("total_price", totalPrice);
        trailData.put("balance", user.getBalance());
        trailData.put("canUseIntegral", canUseIntegral);
        trailData.put("canDeductionAmount", canDeductionAmount);

        if (type == 2) {
            trailData.put("ratio", ratio);
        }
        return AjaxResult.dataReturn(0, "success", trailData);
    }
}
