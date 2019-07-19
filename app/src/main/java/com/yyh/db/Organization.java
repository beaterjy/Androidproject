package com.yyh.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Organization extends LitePalSupport {

    @Column(unique = true, nullable = false)
    private String Origan_name; // 组织机构名称

    public String getOrigan_name() {
        return Origan_name;
    }

    public void setOrigan_name(String origan_name) {
        Origan_name = origan_name;
    }
}
