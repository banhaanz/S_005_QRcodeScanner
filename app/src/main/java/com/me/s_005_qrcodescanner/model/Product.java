package com.me.s_005_qrcodescanner.model;

public class Product {
    private String id;
    private String qrscan;
    private int status;

    public Product(String id, String qrscan, int status){
        this.id = id;
        this.qrscan = qrscan;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQrscan() {
        return qrscan;
    }

    public void setQrscan(String qrscan) {
        this.qrscan = qrscan;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
