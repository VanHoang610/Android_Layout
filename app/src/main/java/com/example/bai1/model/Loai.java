package com.example.bai1.model;

public class Loai {
    private int idLoai;
    private String tenLoai;
    private int trangThai;

    public Loai(int idLoai, String tenLoai, int trangThai) {
        this.idLoai = idLoai;
        this.tenLoai = tenLoai;
        this.trangThai = trangThai;
    }

    public int getIdLoai() {
        return idLoai;
    }

    public void setIdLoai(int idLoai) {
        this.idLoai = idLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
