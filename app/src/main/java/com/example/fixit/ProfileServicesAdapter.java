package com.example.fixit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileServicesAdapter extends RecyclerView.Adapter<ProfileServicesAdapter.ViewHolder>{

    Context context;
    ArrayList<ServicesModel> servicesArray;

    public ProfileServicesAdapter(Context context, ArrayList<ServicesModel> servicesArray) {
        this.context = context;
        this.servicesArray = servicesArray;
    }

    @NonNull
    @Override
    public ProfileServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.services_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileServicesAdapter.ViewHolder holder, int position) {
        holder.services.setText(servicesArray.get(position).services);
    }

    @Override
    public int getItemCount() {
        return servicesArray.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView services;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            services = itemView.findViewById(R.id.service);
        }
    }
}
