package com.example.fixit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AcceptedRequestsAdapter extends RecyclerView.Adapter<AcceptedRequestsAdapter.ViewHolder> {

    private ArrayList<RequestModel> requestArray; // List of accepted requests
    private Context context;

    // Constructor for AcceptedRequestsAdapter
    public AcceptedRequestsAdapter(Context context, ArrayList<RequestModel> requestArray) {
        this.context = context;
        this.requestArray = requestArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.serviceprovider_request_accepted, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestModel request = requestArray.get(position);
        holder.from.setText(request.getUserName());
        holder.contact.setText(request.getContact());
        holder.area.setText(request.getArea());
        holder.city.setText(request.getCity());
        holder.service.setText(request.getService());
        holder.dateofbooking.setText(request.getBookingDate());
    }

    @Override
    public int getItemCount() {
        return requestArray.size();
    }

    // Method to add a request to the accepted list
    public void addAcceptedRequest(RequestModel request) {
        requestArray.add(request); // Add the new accepted request to the list

        requestArray.add(request); // Add the new accepted request to the list
        int newPosition = requestArray.size() - 1;
        if (newPosition >= 0) {
            notifyItemInserted(newPosition);
        }
    }

    // ViewHolder class to hold each accepted request's views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView from, contact, area, city, service;
        TextView dateofbooking;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.from);
            contact = itemView.findViewById(R.id.contact);
            area = itemView.findViewById(R.id.area);
            city = itemView.findViewById(R.id.city);
            service = itemView.findViewById(R.id.service);
            dateofbooking = itemView.findViewById(R.id.dateofbooking);
        }
    }
}
