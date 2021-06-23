package com.example.tekkoproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tekkoproject.Activity.DetailActivity;
import com.example.tekkoproject.Models.Product;
import com.example.tekkoproject.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
    Context context; //màn hình mà muốn đổ vào
    List<Product> arrayList;

    public void setData(List<Product> list){
        this.arrayList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_product, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    public ProductAdapter(Context context, List<Product> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ItemHolder holder, int position) {
        Product product = arrayList.get(position);
        holder.tvNameProduct.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPriceProduct.setText(String.valueOf(decimalFormat.format(product.getPrice()))+"Đ");
        Picasso.with(context).load(product.getImg()).placeholder(R.drawable.warning).into(holder.imgProduct);
        holder.parentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("detail", arrayList.get(position));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        ImageView imgProduct;
        TextView tvNameProduct, tvPriceProduct;
        ConstraintLayout parentLayout;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvPriceProduct = itemView.findViewById(R.id.tvPriceProduct);
            parentLayout = itemView.findViewById(R.id.lineProduct);
        }
    }







}