<?php

namespace app\command\task;

use app\api\service\user\UserBalanceRechargeService;
use think\console\Command;
use think\console\Input;
use think\console\Output;

/**
 * 余额充值订单超时检测
 */
class BalanceOrderCheck extends Command
{
    protected function configure()
    {
        $this->setName('余额充值订单超时检测');
    }

    protected function execute(Input $input, Output $output)
    {
        // TODO 超时时间定时暂定1小时
        (new UserBalanceRechargeService())->overdueClose(3600);

        $output->writeln('盲盒订单关闭完成');
    }
}
