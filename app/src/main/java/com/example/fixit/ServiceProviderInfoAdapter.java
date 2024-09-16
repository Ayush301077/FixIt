package com.example.fixit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ServiceProviderInfoAdapter extends RecyclerView.Adapter<ServiceProviderInfoAdapter.ViewHolder> {

    Context context;
    ArrayList<ServiceProviderInfo> serviceProviderInfoArrayList = new ArrayList<>();

    public ServiceProviderInfoAdapter(Context context, ArrayList<ServiceProviderInfo> serviceProviderInfoArrayList) {
        this.context = context;
        this.serviceProviderInfoArrayList = serviceProviderInfoArrayList;
    }

    @NonNull
    @Override
    public ServiceProviderInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.service_provider_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceProviderInfoAdapter.ViewHolder holder, int position) {
        holder.name.setText(serviceProviderInfoArrayList.get(position).name);
        holder.service.setText(serviceProviderInfoArrayList.get(position).service);
        holder.contact.setText(serviceProviderInfoArrayList.get(position).contact);
        holder.location.setText(serviceProviderInfoArrayList.get(position).location);
        holder.ratings.setText(serviceProviderInfoArrayList.get(position).ratings);
        holder.profilephoto.setImageResource(serviceProviderInfoArrayList.get(position).profilephoto);

    }

    @Override
    public int getItemCount() {
        return serviceProviderInfoArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, service,contact, location,ratings;
        ImageView profilephoto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            service = itemView.findViewById(R.id.service);
            contact = itemView.findViewById(R.id.contact);
            location = itemView.findViewById(R.id.location);
            ratings = itemView.findViewById(R.id.ratings);
            profilephoto = itemView.findViewById(R.id.profilephoto);

        }
    }
}
