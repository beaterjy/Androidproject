package com.yyh.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Question extends LitePalSupport {

    @Column(unique = true, nullable = false)
    private int Question_id; // 自增序号(主键) TODO: 自增
    @Column(nullable = false)
    private int Question_year; // 年份
    @Column(nullable = false)
    private int Question_y_id; // 本年问题编号
    @Column(nullable = false)
    private String Question_content; // 问题内容
    @Column(nullable = false)
    private String Question_major; // 所属专业名(参考专业信息表Major)

    public int getQuestion_id() {
        return Question_id;
    }

    public void setQuestion_id(int question_id) {
        Question_id = question_id;
    }

    public int getQuestion_year() {
        return Question_year;
    }

    public void setQuestion_year(int question_year) {
        Question_year = question_year;
    }

    public int getQuestion_y_id() {
        return Question_y_id;
    }

    public void setQuestion_y_id(int question_y_id) {
        Question_y_id = question_y_id;
    }

    public String getQuestion_content() {
        return Question_content;
    }

    public void setQuestion_content(String question_content) {
        Question_content = question_content;
    }

    public String getQuestion_major() {
        return Question_major;
    }

    public void setQuestion_major(String question_major) {
        Question_major = question_major;
    }
}
