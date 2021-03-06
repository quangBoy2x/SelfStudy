package com.example.tekkoproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import android.view.Menu;
import android.view.MenuItem;
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

    RecyclerView recyclerView;
    ImageView btnBack;
    Context context;
    androidx.appcompat.widget.SearchView searchView;
    public static List<Product> products;
    RecyclerView.ItemDecoration itemDecoration;
    ProductAdapter productAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map();

        recyclerView.setAdapter(productAdapter);
        if(isConnected()){
            Toast.makeText(this,"Internet connected, your products will be newest", Toast.LENGTH_SHORT).show();
            //todo load d??? li???u t??? api m???i nh???t
            GetData();

        }
        else{
            Toast.makeText(this,"no internet, retry connect to update your products", Toast.LENGTH_SHORT).show();
            //todo kh??ng k???t n???i load d??? li???u t??? sqlite
            products = ProductDataBase.getInstance(this).productDAO().getListProduct();
            productAdapter.setData(products);


        }
        //x??? l?? recycleView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(itemDecoration);




    }



    //h??m ??nh x???
    private void Map(){
        products = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.lvProducts);

        productAdapter = new ProductAdapter(getApplicationContext(), products);
        productAdapter.setData(products);
        //th??m d??ng k??? ????? ph??n c??ch c??c item
        itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

    }
    //ki???m tra k???t n???i INTERNET C?? HAY KH??NG C??
    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    //h??m ?????c data t??? api
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
                            //c???p nh???t l???i giao di???n
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

    //h??m th??m c??c s???n ph???m v??o sqlite
    private void addProducts(int ID, String TenSP, String HinhAnhSp, String dateAdded, String dateUpdated, int GiaSp, String brand, String code){
        Product product = new Product(ID, TenSP, HinhAnhSp, dateAdded, dateUpdated, GiaSp, brand, code);
        ProductDataBase.getInstance(this).productDAO().insertProduct(product);
    }
    //h??m keierm tra s???n ph???m c?? t???n t???i trong sqlite hay kh??ng
    private boolean isProductExist(Product product){
        List<Product> products = ProductDataBase.getInstance(this).productDAO().checkProduct(product.getId());
        return product!=null && !products.isEmpty();
    }

    //x??? l?? khi searchbar ??ang expand th?? user backPress th?? thu l???i searchbar
    //r???i m???i tho??t h???n
    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    //h??m g???i menu v?? x??? l?? khi ch???n search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}