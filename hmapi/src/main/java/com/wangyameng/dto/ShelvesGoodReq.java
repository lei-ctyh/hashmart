package com.wangyameng.dto;

import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName ShelvesGoodReq.java
 * @Description TODO
 * @createTime 2024-05-10 23:54:00
 */
public class ShelvesGoodReq {
    List<Integer> ids;
    Integer status;
    Integer type;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
