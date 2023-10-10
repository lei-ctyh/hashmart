<?php

namespace app\command;

use Fairy\HttpCrontab;
use think\console\Command;
use think\console\Input;
use think\console\input\Argument;
use think\console\Output;

class Crontab extends Command
{
    protected function configure()
    {
        $this->setName('定时任务')->addArgument('option', Argument::OPTIONAL, "your option");
    }

    protected function execute(Input $input, Output $output)
    {
        date_default_timezone_set('PRC');

        $dbConfig = [
            'hostname' => env('database.hostname', '127.0.0.1'),
            'hostport' => env('database.hostport', '3306'),
            'username' => env('database.username', 'root'),
            'password' => env('database.password', ''),
            'database' => env('database.database', ''),
            'charset' => env('database.charset', 'utf8')
        ];

        $logo =<<<EOL
 _               _                          _   
| |__   __ _ ___| |__  _ __ ___   __ _ _ __| |_ 
| '_ \ / _` / __| '_ \| '_ ` _ \ / _` | '__| __|
| | | | (_| \__ \ | | | | | | | | (_| | |  | |_ 
|_| |_|\__,_|___/_| |_|_| |_| |_|\__,_|_|   \__|
EOL;

        $output->writeln($logo . PHP_EOL);

        (new HttpCrontab())->setDebug(true)
            ->setName('定时任务服务器')
            ->setDbConfig($dbConfig)
            ->run();
    }
}