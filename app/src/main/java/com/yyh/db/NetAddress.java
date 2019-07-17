package com.yyh.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class NetAddress extends LitePalSupport {

    @Column(nullable = false)
    private String Address_apartment; // 网址所在部门(主键) TODO: 外键？？
    private String Address_inform; // 通知网址
    private String Address_rule; // 制度网址

    public String getAddress_apartment() {
        return Address_apartment;
    }

    public void setAddress_apartment(String address_apartment) {
        Address_apartment = address_apartment;
    }

    public String getAddress_inform() {
        return Address_inform;
    }

    public void setAddress_inform(String address_inform) {
        Address_inform = address_inform;
    }

    public String getAddress_rule() {
        return Address_rule;
    }

    public void setAddress_rule(String address_rule) {
        Address_rule = address_rule;
    }
}
