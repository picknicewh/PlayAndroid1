package com.mayi.yun.playandroid.db;

/**
 * 作者： wh
 * 时间：  2018/3/3
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class History {
    private int id;
    private String name;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
