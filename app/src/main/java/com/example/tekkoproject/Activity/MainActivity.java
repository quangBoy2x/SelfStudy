package com.example.tekkoproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tekkoproject.Adapter.ProductAdapter;

import com.example.tekkoproject.DataBase.ProductDataBase;
import com.example.tekkoproject.Models.Product;
import com.example.tekkoproject.R;
import com.example.tekkoproject.Utilities.CheckInternet;
import com.example.tekkoproject.Utilities.Server;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    androidx.appcompat.widget.SearchView searchView;
    RecyclerView recyclerView;
    ImageView btnBack;
    Context context;
    List<Product> products;
    ProductAdapter productAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map();

        recyclerView.setAdapter(productAdapter);
        if(isConnected()){
            Toast.makeText(this,"Internet connected, your products will be newest", Toast.LENGTH_SHORT).show();
            //todo load dữ liệu từ api mới nhất
            GetData();
        }
        else{
            Toast.makeText(this,"no internet, retry connect to update your products", Toast.LENGTH_SHORT).show();
            //todo không kết nối load dữ liệu từ sqlite
            products = ProductDataBase.getInstance(this).productDAO().getListProduct();
            productAdapter.setData(products);


        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        recyclerView.setHasFixedSize(true);



    }



    private void Map(){
        products = new ArrayList<>();
        searchView = (androidx.appcompat.widget.SearchView) findViewById(R.id.searchBar);
        recyclerView = (RecyclerView) findViewById(R.id.lvProducts);
        btnBack = (ImageView) findViewById(R.id.btnBack);


        productAdapter = new ProductAdapter(getApplicationContext(), products);
        productAdapter.setData(products);
//        mydb = new DataBaseHelper(this);


    }

    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void GetData(){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlGetProducts, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("TabHome", "response: " + response);
                if(response != null){
                    int ID = 0;
                    String TenSP = "";
                    Integer GiaSp = 0;
                    String HinhAnhSp = "";
                    String dateUpdated = "";
                    String dateAdded = "";
                    String brand = "";
                    String code = "";


                    for(int i = 0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            TenSP = jsonObject.getString("name");
                            GiaSp = jsonObject.getInt("price");
                            HinhAnhSp = jsonObject.getString("imageUrl");
                            dateAdded = jsonObject.getString("dateAdded");
                            dateUpdated = jsonObject.getString("dateUpdated");
                            brand = jsonObject.getString("brand");
                            code = jsonObject.getString("code");
//                            int id, String name, String img, String dateAdded, String dateUpdated, Integer price, String brand, String code
//                            int id, String name, String img, Integer price
                            Product productCurrent = new Product(ID, TenSP, HinhAnhSp, dateAdded, dateUpdated, GiaSp, brand, code);
                            products.add(productCurrent);
//                            products.add(new Product(ID, TenSP, HinhAnhSp, GiaSp));
                            if(!isProductExist(productCurrent)){
                                addProducts(ID, TenSP, HinhAnhSp, dateAdded, dateUpdated, GiaSp, brand, code);
                                Log.d("database","succ");
                            }
                            else{
                                Log.d("database","erroe");
                            }
                            //cập nhật lại giao diện
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TabHome", "response: " + error);
            }
        });

        requestQueue.add(jsonArrayRequest);
    }


    private void addProducts(int ID, String TenSP, String HinhAnhSp, String dateAdded, String dateUpdated, int GiaSp, String brand, String code){
        Product product = new Product(ID, TenSP, HinhAnhSp, dateAdded, dateUpdated, GiaSp, brand, code);
        ProductDataBase.getInstance(this).productDAO().insertProduct(product);
    }

    private boolean isProductExist(Product product){
        List<Product> products = ProductDataBase.getInstance(this).productDAO().checkProduct(product.getId());
        return product!=null && !products.isEmpty();
    }



}