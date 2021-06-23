package com.example.tekkoproject.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tekkoproject.Models.Product;
//DATABASE TRONG ROOM XỬ LÍ SQLITE
@Database(entities = {Product.class}, version = 1)
public abstract class ProductDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "product.db";
    private static ProductDataBase instance;

    public static synchronized ProductDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ProductDataBase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract ProductDAO productDAO();
}
