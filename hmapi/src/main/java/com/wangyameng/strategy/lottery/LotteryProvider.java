package com.wangyameng.strategy.lottery;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.OrderParamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LotteryProvider {
    private LotteryStrategy lotteryStrategy;
    @Autowired
    private Map<String, LotteryStrategy>lotteryStrategyMap = new HashMap<>();

    public AjaxResult draw(OrderParamDTO orderParamDTO) {
        if (orderParamDTO.getPlayId() == 1) {
            this.lotteryStrategy = lotteryStrategyMap.get("hashLotteryStrategy");
        }
        return lotteryStrategy.draw(orderParamDTO);
    }
}
