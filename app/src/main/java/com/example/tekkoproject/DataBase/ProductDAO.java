package com.example.tekkoproject.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tekkoproject.Models.Product;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ProductDAO {
    @Insert
    void insertProduct(Product product);
    @Query("SELECT * FROM products")
    List<Product> getListProduct();

    @Query("SELECT * FROM products WHERE id = :id")
    List<Product> checkProduct(int id);
}
