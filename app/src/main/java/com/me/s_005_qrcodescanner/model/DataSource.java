package com.me.s_005_qrcodescanner.model;

import android.provider.BaseColumns;

public class DataSource {
    //Database
    public static final String DATABASE_NAME = "tb_data.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "data";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String TITLE = "title";
        public static final String DATA = "data";
        public static final String URL = "url";
    }

    private int id;
    private String title;
    private String data;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
