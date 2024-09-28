package com.example.fixit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ServiceProviderInfoAdapter extends RecyclerView.Adapter<ServiceProviderInfoAdapter.ViewHolder> {

    Context context;
    ArrayList<ServiceProviderInfo> serviceProviderInfoArrayList = new ArrayList<>();

    public ServiceProviderInfoAdapter(Context context, ArrayList<ServiceProviderInfo> serviceProviderInfoArrayList) {
        this.context = context;
        this.serviceProviderInfoArrayList = serviceProviderInfoArrayList;
    }

    public void setFilteredList(ArrayList<ServiceProviderInfo> filteredList) {
//        this.serviceProviderInfoArrayList = filteredList;
        this.serviceProviderInfoArrayList.clear();  // Clear the old list
        this.serviceProviderInfoArrayList.addAll(filteredList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceProviderInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_provider_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceProviderInfoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ServiceProviderInfo info;
        info = serviceProviderInfoArrayList.get(position);

        holder.name.setText(info.name);
        holder.service.setText(info.services);
        holder.contact.setText(info.contact);
        holder.location.setText(info.city);
        holder.charges.setText(info.charges);

        Glide.with(context)
                .load(info.getProfileImage()) // URL of the image
                .placeholder(R.drawable.profile_icon) // Optional placeholder image
                .into(holder.profileImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ServiceProviderDetailsActivity.class);
                intent.putExtra("serviceProviderId", info.getServiceProviderId());
                intent.putExtra("name", info.name);
                intent.putExtra("service", info.services);
                intent.putExtra("contact", info.contact);
                intent.putExtra("location", info.city);
                intent.putExtra("profileImage", info.getProfileImage());
                intent.putExtra("email", info.email);
                intent.putExtra("charges", info.charges);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceProviderInfoArrayList.size();
    }

    // Method to update the list and notify the RecyclerView of data changes
    public void updateList(ArrayList<ServiceProviderInfo> newList) {
        this.serviceProviderInfoArrayList = newList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, service, contact, location, charges;
        ImageView profileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            service = itemView.findViewById(R.id.service);
            contact = itemView.findViewById(R.id.contact);
            location = itemView.findViewById(R.id.location);
            profileImage = itemView.findViewById(R.id.profileImage);
            charges = itemView.findViewById(R.id.charges);
        }
    }
}






//package com.example.fixit;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//public class ServiceProviderInfoAdapter extends RecyclerView.Adapter<ServiceProviderInfoAdapter.ViewHolder> {
//
//    Context context;
//    ArrayList<ServiceProviderInfo> serviceProviderInfoArrayList = new ArrayList<>();
//
//    public ServiceProviderInfoAdapter(Context context, ArrayList<ServiceProviderInfo> serviceProviderInfoArrayList) {
//        this.context = context;
//        this.serviceProviderInfoArrayList = serviceProviderInfoArrayList;
//    }
//
//    public void setFilteredList(ArrayList<ServiceProviderInfo> filteredList) {
//        this.serviceProviderInfoArrayList = filteredList;
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public ServiceProviderInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.service_provider_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ServiceProviderInfoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        ServiceProviderInfo info = serviceProviderInfoArrayList.get(position);
//
//        holder.name.setText(info.name);
//        holder.service.setText(info.services);
//        holder.contact.setText(info.contact);
//        holder.location.setText(info.city);
//
//        Glide.with(context)
//                .load(info.getProfileImage()) // URL of the image
//                .placeholder(R.drawable.profile_icon) // Optional placeholder image
//                .into(holder.profileImage);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(holder.itemView.getContext(), ServiceProviderDetailsActivity.class);
//                intent.putExtra("name", info.name);
//                intent.putExtra("service", info.services);
//                intent.putExtra("contact", info.contact);
//                intent.putExtra("location", info.city);
//                intent.putExtra("profileImage", info.getProfileImage()); // pass the image URL
//                intent.putExtra("email", info.email);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return serviceProviderInfoArrayList.size();
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView name, service, contact, location;
//        ImageView profileImage;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            name = itemView.findViewById(R.id.name);
//            service = itemView.findViewById(R.id.service);
//            contact = itemView.findViewById(R.id.contact);
//            location = itemView.findViewById(R.id.location);
//            profileImage = itemView.findViewById(R.id.profileImage);
//        }
//    }
//}
