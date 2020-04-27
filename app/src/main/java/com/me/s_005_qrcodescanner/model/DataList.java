package com.me.s_005_qrcodescanner.model;

import java.util.List;

public class DataList {
   private List<String> id;
    private List<String> title;
    private List<String> data;
    private List<String> url;
    private List<String> number;
    private List<String> scanned;
    private List<String> status;
    private List<String> timestamp;

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public List<String> getNumber() {
        return number;
    }

    public void setNumber(List<String> number) {
        this.number = number;
    }

    public List<String> getScanned() {
        return scanned;
    }

    public void setScanned(List<String> scanned) {
        this.scanned = scanned;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<String> timestamp) {
        this.timestamp = timestamp;
    }
}
