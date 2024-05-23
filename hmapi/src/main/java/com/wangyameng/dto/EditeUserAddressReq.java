package com.wangyameng.dto;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName EditeUserAddressReq.java
 * @Description TODO
 * @createTime 2024-05-24 01:06:00
 */
public class EditeUserAddressReq {
    /**
     * {"id":1,"receiver":"酸钠","phone":"13256610521","province_code":140000,"city_code":140800,"area_code":140824,"address":"撒大声地稍等啊aaa"}
     */
    private Integer id;
    private String receiver;
    private String phone;
    private String province_code;
    private String city_code;
    private String area_code;
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
