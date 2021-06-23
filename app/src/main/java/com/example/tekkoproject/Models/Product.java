package com.example.tekkoproject.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
//lớp sản phẩm
@Entity(tableName = "PRODUCTS")
public class Product implements Serializable {
    @PrimaryKey
    int id;
    String name;
    String img;
    String dateAdded;
    String dateUpdated;
    Integer price;
    String brand;
    String code;

    //declare to use in sqlite
    public static final String TABLE_NAME = "PRODUCTS";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMG = "image";
    public static final String COLUMN_DATE_ADD = "dateAdded";
    public static final String COLUMN_DATE_UPDATE = "dateUpdated";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_CODE = "code";

    // Create table SQL query
//    public static final String CREATE_TABLE =
//            "CREATE TABLE " + TABLE_NAME + "("
//                    + COLUMN_ID + " INTEGER,"
//                    + COLUMN_NAME + " TEXT,"
//                    + COLUMN_IMG + " TEXT,"
//                    + COLUMN_DATE_ADD + " TEXT,"
//                    + COLUMN_DATE_UPDATE + " TEXT,"
//                    + COLUMN_PRICE + " INTEGER,"
//                    + COLUMN_BRAND + " TEXT,"
//                    + COLUMN_CODE + " TEXT"
//                    + ")";


    public Product(int id, String name, String img, String dateAdded, String dateUpdated, Integer price, String brand, String code) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.dateAdded = dateAdded;
        this.dateUpdated = dateUpdated;
        this.price = price;
        this.brand = brand;
        this.code = code;
    }

    public Product() {

    }

    public Product(int id, String name, String img, Integer price) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.price = price;
    }

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", dateAdded='" + dateAdded + '\'' +
                ", dateUpdated='" + dateUpdated + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", code='" + code + '\'' +
                '}';
    }


}
