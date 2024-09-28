package com.example.fixit;

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

public class PendingServiceAdapter extends RecyclerView.Adapter<PendingServiceAdapter.ViewHolder>{

    Context context;
    ArrayList<PendingServiceModel> pendingRequestArrayList = new ArrayList<>();

    public PendingServiceAdapter(Context context, ArrayList<PendingServiceModel> pendingRequestArrayList) {
        this.context = context;
        this.pendingRequestArrayList = pendingRequestArrayList;
    }

    public void setFilteredList(ArrayList<PendingServiceModel> filteredList) {
//        this.serviceProviderInfoArrayList = filteredList;
        this.pendingRequestArrayList.clear();  // Clear the old list
        this.pendingRequestArrayList.addAll(filteredList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PendingServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_provider_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingServiceAdapter.ViewHolder holder, int position) {
        PendingServiceModel info = pendingRequestArrayList.get(position);

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
                Intent intent = new Intent(holder.itemView.getContext(), PendingSPDetailsActivity.class);
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
        return pendingRequestArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, service, contact, location,charges;
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
