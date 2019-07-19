package com.yyh.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class RecordType extends LitePalSupport {

    @Column(unique = true, nullable = false)
    private String Record_type_name; // 笔录类型名称(主键)

    public String getRecord_type_name() {
        return Record_type_name;
    }

    public void setRecord_type_name(String record_type_name) {
        Record_type_name = record_type_name;
    }
}
