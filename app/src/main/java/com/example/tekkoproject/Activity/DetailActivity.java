package com.example.tekkoproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.example.tekkoproject.Adapter.Recycleviewadapter;
import com.example.tekkoproject.DataBase.ProductDataBase;
import com.example.tekkoproject.Models.Cart;
import com.example.tekkoproject.Models.Product;
import com.example.tekkoproject.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

//xử lí activity chi tiết sản phẩm
public class DetailActivity extends AppCompatActivity {
    TextView tvNameHead, tvNameBody, tvPriceHead, tvPriceBody, tvTotalPrice, tvTotalQuantity, tvCode, tvQuantityCart;
    String img="", name="", code="";
    ImageView imgProduct, btnPlus, btnMinus, btnAddToCart;
    int price=0, quantities=0, totalCart = 0;
    Product productcurrent;
    Context context;
    Cart cart;
    List<Cart> carts;
    RecyclerView recyclerView;
    Recycleviewadapter recycleviewadapter;

    //hàm kiểm tra internet
    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Map();
        GetInforProduct();
        AddItem();
        SubtractItem();
        addToCart();
        //làm cho recycleview lướt ngang
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleviewadapter);
        if(isConnected()){
            Toast.makeText(this,"Internet connected, your products will be newest", Toast.LENGTH_SHORT).show();
            //todo load dữ liệu từ api mới nhất
            recycleviewadapter.notifyDataSetChanged();

        }
        else{
            Toast.makeText(this,"no internet, retry connect to update your products", Toast.LENGTH_SHORT).show();
            //todo không kết nối load dữ liệu từ sqlite

            MainActivity.products = ProductDataBase.getInstance(this).productDAO().getListProduct();
            recycleviewadapter.setData(MainActivity.products);
            for(int i = 0;i<10;i++){
                Log.d("okokok",""+MainActivity.products.get(i).toString());
            }

        }

    }
    //hàm xủ lí khi nhấn nút + sẽ tăng số lượng sp và đặt lại tổng giá tiền
    private void AddItem() {
        btnPlus.setOnClickListener(v -> {
            quantities++;
            tvTotalQuantity.setText(String.valueOf(quantities));
            price = productcurrent.getPrice()*quantities;
            tvTotalPrice.setText(String.valueOf(price) + " Đ");

        });


    }
    //hàm xủ lí khi nhấn nút - sẽ tăng số lượng sp và đặt lại tổng giá tiền
    private void SubtractItem() {
        btnMinus.setOnClickListener(v -> {
            if(quantities>0){
                quantities--;
                tvTotalQuantity.setText(String.valueOf(quantities));
            }
            price = productcurrent.getPrice()*quantities;
            tvTotalPrice.setText(String.valueOf(price) + " Đ");

        });

    }
    //hàm nhấn nút mua hàng thì sẽ toast lên đã thêm giỏ hàng
    private void addToCart(){
        btnAddToCart.setOnClickListener(v -> {
//            int id, String tensp
            Toast.makeText(this,"đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

        });

    }
    //hàm lấy thông tin sản phẩm
    private void GetInforProduct() {
        Product product =(Product) getIntent().getSerializableExtra("thongtinsp");
        productcurrent = product;
        name = product.getName();
        img = product.getImg();
        code = product.getCode();
        price = product.getPrice();
        tvNameHead.setText(name);
        tvNameBody.setText(name);
        tvCode.setText(code);

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvPriceHead.setText(String.valueOf(decimalFormat.format(price)) + "Đ");
        tvPriceBody.setText(String.valueOf(decimalFormat.format(price)) + "Đ");
        Picasso.with(getApplicationContext()).load(img).placeholder(R.drawable.warning).error(R.drawable.warning).into(imgProduct);
    }
    //hàm ánh xạ
    private void Map(){
        tvCode = (TextView) findViewById(R.id.tvDetailCode);
        tvNameBody = (TextView) findViewById(R.id.tvDetailNameBody);
        tvNameHead = (TextView) findViewById(R.id.tvDetailNameHead);
        tvPriceHead = (TextView) findViewById(R.id.tvDetailPriceHead);
        tvPriceBody = (TextView) findViewById(R.id.tvDetailPriceBody);
        tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        tvTotalQuantity = (TextView) findViewById(R.id.tvQuantity);
        imgProduct = (ImageView) findViewById(R.id.imgDetailProduct);
        btnPlus = (ImageView) findViewById(R.id.btnPlus);
        btnMinus = (ImageView) findViewById(R.id.btnMinus);
        btnAddToCart = (ImageView) findViewById(R.id.btnAddToCart);
        carts = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.lvSameProduct);
        recycleviewadapter = new Recycleviewadapter(this, MainActivity.products);
        recycleviewadapter.setData(MainActivity.products);

    }
}