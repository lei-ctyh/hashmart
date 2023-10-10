<?php

namespace app\command\task;

use app\api\service\order\OrderService;
use think\console\Command;
use think\console\Input;
use think\console\Output;

/**
 * 盲盒订单超时检测
 */
class BlindboxOrderCheck extends Command
{
    protected function configure()
    {
        $this->setName('盲盒订单超时检测');
    }

    protected function execute(Input $input, Output $output)
    {
        // TODO 超时时间定时暂定1小时
        (new OrderService())->overdueClose(3600);

        $output->writeln('盲盒订单关闭完成');
    }
}