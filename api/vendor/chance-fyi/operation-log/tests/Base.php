<?php
/**
 * Created by PhpStorm
 * IUser Chance
 * Date 2021/12/31 16:40
 */

namespace Chance\Log\Test;

use Chance\Log\orm\illuminate\MySqlConnection;
use Chance\Log\orm\think\Query;
use Illuminate\Database\Capsule\Manager;
use Illuminate\Database\Connection;
use PHPUnit\Framework\TestCase;
use think\db\builder\Mysql;
use think\facade\Db;

class Base extends TestCase
{
    public function __construct(string $name)
    {
        parent::__construct($name);
        $this->connectionI();
        $this->connectionT();
        $this->createTable();
    }

    // 连接数据库 illuminate orm
    protected function connectionI()
    {
        $capsule = new Manager();
        $capsule->addConnection([
            "driver" => "mysql",
            "host" => "mysql",
            "database" => "test",
            "username" => "root",
            "password" => "root",
            "charset" => "utf8",
            "collation" => "utf8_unicode_ci",
            "prefix" => "tb_",
            "modelNamespace" => "Chance\Log\Test\model\illuminate",
            "logKey" => "id",
        ]);
        $capsule->addConnection([
            "driver" => "mysql",
            "host" => "mysql1",
            "database" => "test1",
            "username" => "root",
            "password" => "root",
            "charset" => "utf8",
            "collation" => "utf8_unicode_ci",
            "prefix" => "tb_",
            "modelNamespace" => "Chance\Log\Test\model\illuminate",
            "logKey" => "id",
        ], "default1");
        $capsule->setAsGlobal();
        $capsule->bootEloquent();
        Connection::resolverFor('mysql', function ($connection, $database, $prefix, $config) {
            return new MySqlConnection($connection, $database, $prefix, $config);
        });
    }

    // 连接数据库 think orm
    protected function connectionT()
    {
        Db::setConfig([
            "connections" => [
                "mysql" => [
                    "type" => \Chance\Log\orm\think\MySqlConnection::class,
                    "hostname" => "mysql",
                    "database" => "test",
                    "username" => "root",
                    "password" => "root",
                    "charset" => "utf8",
                    "collation" => "utf8_unicode_ci",
                    "prefix" => "tb_",
                    "builder" => Mysql::class,
                    "query" => Query::class,
                    "modelNamespace" => "Chance\Log\Test\model\\think",
                    "logKey" => "id",
                ],
                "default1" => [
                    "type" => \Chance\Log\orm\think\MySqlConnection::class,
                    "hostname" => "mysql1",
                    "database" => "test1",
                    "username" => "root",
                    "password" => "root",
                    "charset" => "utf8",
                    "collation" => "utf8_unicode_ci",
                    "prefix" => "tb_",
                    "builder" => Mysql::class,
                    "query" => Query::class,
                    "modelNamespace" => "Chance\Log\Test\model\\think",
                    "logKey" => "id",
                ],
            ]
        ]);
    }

    // 创建表
    protected function createTable()
    {
        if (!Manager::select("show tables like 'tb_user';")) {
            Manager::select("
                create table tb_user
                (
                    id          int auto_increment
                        primary key,
                    name        varchar(20)       null comment '姓名',
                    sex         tinyint default 0 null comment '性别',
                    json        json              null comment 'json',
                    create_time datetime,
                    update_time datetime
                )
                    comment '用户';
            ");
        }
        if (!Manager::connection("default1")->select("show tables like 'tb_user';")) {
            Manager::connection("default1")->select("
                create table tb_user
                (
                    id          int auto_increment
                        primary key,
                    name        varchar(20)       null comment '姓名1',
                    sex         tinyint default 0 null comment '性别1',
                    json        json              null comment 'json1',
                    create_time datetime,
                    update_time datetime
                )
                    comment '用户1';
            ");
        }
    }
}