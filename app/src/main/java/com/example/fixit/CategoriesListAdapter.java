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

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.ViewHolder> {

    Context context;
    ArrayList<CategorieslistItem> categorieslistItemArray = new ArrayList<>();

    public CategoriesListAdapter(Context context, ArrayList<CategorieslistItem> categorieslistItemArray) {
        this.context = context;
        this.categorieslistItemArray = categorieslistItemArray;
    }

    public void setFilteredList(ArrayList<CategorieslistItem> filteredList){
        this.categorieslistItemArray = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(context).inflate(R.layout.categories_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesListAdapter.ViewHolder holder, int position) {

        holder.serviceType.setText(categorieslistItemArray.get(position).serviceType);
        holder.categoryIcon.setImageResource(categorieslistItemArray.get(position).categoryIcon);

    }

    @Override
    public int getItemCount() {
        return categorieslistItemArray.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView serviceType;
        ImageView categoryIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceType = itemView.findViewById(R.id.serviceType);
            categoryIcon = itemView.findViewById(R.id.categoryIcon);

        }
    }
}
