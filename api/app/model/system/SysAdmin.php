<?php

namespace app\model\system;

use app\model\BaseModel;

class SysAdmin extends BaseModel
{
    public function role()
    {
        return $this->hasOne(SysRole::class, 'id', 'role_id');
    }
}