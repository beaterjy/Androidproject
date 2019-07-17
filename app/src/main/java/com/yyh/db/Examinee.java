package com.yyh.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Examinee extends LitePalSupport {

    @Column(unique = true, nullable = false)
    private String Examinee_code; // 考生编号(主键)
    @Column(nullable = false)
    private String Examinee_name; // 考生姓名
    @Column(nullable = false)
    private String Examinee_major; // 考生专业(参照专业信息表中的专业名称Major)
    @Column(nullable = false)
    private int Examinee_year; // 考试年份

    public String getExaminee_code() {
        return Examinee_code;
    }

    public void setExaminee_code(String examinee_code) {
        Examinee_code = examinee_code;
    }

    public String getExaminee_name() {
        return Examinee_name;
    }

    public void setExaminee_name(String examinee_name) {
        Examinee_name = examinee_name;
    }

    public String getExaminee_major() {
        return Examinee_major;
    }

    public void setExaminee_major(String examinee_major) {
        Examinee_major = examinee_major;
    }

    public int getExaminee_year() {
        return Examinee_year;
    }

    public void setExaminee_year(int examinee_year) {
        Examinee_year = examinee_year;
    }
}
