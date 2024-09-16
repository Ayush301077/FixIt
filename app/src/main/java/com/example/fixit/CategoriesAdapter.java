package com.example.fixit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    ArrayList<CategoryModel> categoryArray = new ArrayList<>();
    Context context;

    public CategoriesAdapter(ArrayList<CategoryModel> categoryArray, Context context) {
        this.categoryArray = categoryArray;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.categories_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {

        holder.service.setText(categoryArray.get(position).service);
        holder.serviceicon.setImageResource(categoryArray.get(position).serviceicon);

    }

    @Override
    public int getItemCount() {
        return categoryArray.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView service;
        ImageView serviceicon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            service = itemView.findViewById(R.id.service);
            serviceicon = itemView.findViewById(R.id.serviceicon);

        }
    }
}
