package com.example.tekkoproject.Models;

//lớp giỏ hàng
public class Cart {
    int id;
    String tensp;
    long giasp;
    String hinhanh;
    int soluong;


    public Cart(int id, String tensp) {
        this.id = id;
        this.tensp = tensp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getGiasp() {
        return giasp;
    }

    public void setGiasp(long giasp) {
        this.giasp = giasp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", tensp='" + tensp + '\'' +
                '}';
    }
}