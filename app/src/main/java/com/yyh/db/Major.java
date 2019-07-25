package com.yyh.db;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class Major extends LitePalSupport {

    @Column(unique = true, nullable = false)
    private String Major_code; // 专业代号(主键)
    @Column(unique = true, nullable = false)
    private String Major_name; // 专业名称
    @Column(nullable = false)
    private String Major_from_apartment; // 专业所属部门名称(外键)

    public String getMajor_code() {
        return Major_code;
    }

    public void setMajor_code(String major_code) {
        Major_code = major_code;
    }

    public String getMajor_name() {
        return Major_name;
    }

    public void setMajor_name(String major_name) {
        Major_name = major_name;
    }

    public String getMajor_from_apartment() {
        return Major_from_apartment;
    }

    /*
        (外键)根据部门表判断是否可以设置
        返回true --> 设置成功
        返回false --> 无作为
     */
    public boolean setMajor_from_apartment(String major_from_apartment) {
        boolean success = false;
        List<Apartment> apartments = LitePal.where("Apartment_code = ?", major_from_apartment).find(Apartment.class);
        if(apartments != null){
            Major_from_apartment = major_from_apartment;
            success = true;
        }
        return success;
    }
}
