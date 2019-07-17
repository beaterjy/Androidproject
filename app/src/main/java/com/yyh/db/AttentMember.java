package com.yyh.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class AttentMember extends LitePalSupport {

    @Column(unique = true, nullable = false)
    private int AttentMember_code; // 成员编号(主键)
    @Column(nullable = false)
    private String AttentMember_name; // 成员名称
    private String AttentMember_memo; // 备注

    public int getAttentMember_code() {
        return AttentMember_code;
    }

    public void setAttentMember_code(int attentMember_code) {
        AttentMember_code = attentMember_code;
    }

    public String getAttentMember_name() {
        return AttentMember_name;
    }

    public void setAttentMember_name(String attentMember_name) {
        AttentMember_name = attentMember_name;
    }

    public String getAttentMember_memo() {
        return AttentMember_memo;
    }

    public void setAttentMember_memo(String attentMember_memo) {
        AttentMember_memo = attentMember_memo;
    }
}
