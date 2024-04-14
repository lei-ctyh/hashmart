package com.wangyameng.strategy.lottery;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.OrderParamDTO;

public interface LotteryStrategy {
    public AjaxResult draw(OrderParamDTO orderParamDTO);

}
