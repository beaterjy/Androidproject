package com.yyh.db;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class Apartment extends LitePalSupport {

    @Column(unique = true, nullable = false)
    private String Apartment_code; // 部门编号(主键)
    @Column(unique = true, nullable = false)
    private String Apartment_name; // 部门名称
    @Column(nullable = false)
    private String Apartment_from_organ; // 部门所属组织机构(外键)



    public String getApartment_code() {
        return Apartment_code;
    }

    public void setApartment_code(String apartment_code) {
        Apartment_code = apartment_code;
    }

    public String getApartment_name() {
        return Apartment_name;
    }

    public void setApartment_name(String apartment_name) {
        Apartment_name = apartment_name;
    }

    public String getApartment_from_organ() {
        return Apartment_from_organ;
    }

    /*
        (外键)根据组织信息表判断是否可以设置
        返回true --> 设置成功
        返回false --> 无作为
     */
    public boolean setApartment_from_organ(String apartment_from_organ) {
        boolean success = false;
        List<Organization> organizations = LitePal.where("Origan_name = ?", apartment_from_organ).find(Organization.class);
        if(organizations != null){
            Apartment_from_organ = apartment_from_organ;
            success = true;
        }
        return success;
    }
}
