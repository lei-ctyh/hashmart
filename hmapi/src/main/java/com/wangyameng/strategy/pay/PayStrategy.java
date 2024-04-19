package com.wangyameng.strategy.pay;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.PayParamDTO;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName PayProviderStrategy.java
 * @Description TODO
 * @createTime 2024-04-19 22:17:00
 */
public interface PayStrategy {
    AjaxResult pay(PayParamDTO payParamDTO);

}
