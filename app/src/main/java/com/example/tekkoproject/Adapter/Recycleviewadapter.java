package com.example.tekkoproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tekkoproject.Models.Product;
import com.example.tekkoproject.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class Recycleviewadapter extends RecyclerView.Adapter<Recycleviewadapter.ViewHolder> {
    Context context; //màn hình mà muốn đổ vào
    List<Product> products;

    public Recycleviewadapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    public void setData(List<Product> list) {
        this.products = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_item, null);
        ViewHolder itemHolder = new ViewHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvSameName.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvSamePrice.setText(String.valueOf(decimalFormat.format(product.getPrice())) + "Đ");
        Picasso.with(context).load(product.getImg()).placeholder(R.drawable.warning).into(holder.imgSameProduct);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgSameProduct;
        TextView tvSameName, tvSamePrice;
        ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSameProduct = itemView.findViewById(R.id.imgSameProduct);
            tvSameName = itemView.findViewById(R.id.tvSameName);
            tvSamePrice = itemView.findViewById(R.id.tvSamePrice);
            parentLayout = itemView.findViewById(R.id.layoutSame);
        }
    }
}
