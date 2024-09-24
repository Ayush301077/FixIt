package com.example.fixit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewRequestsAdapter extends RecyclerView.Adapter<NewRequestsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RequestModel> requestList;

    public NewRequestsAdapter(Context context, ArrayList<RequestModel> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.serviceprovider_request_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestModel request = requestList.get(position);
        holder.from.setText(request.getFrom());
        holder.contact.setText(request.getContact());
        holder.area.setText(request.getArea());
        holder.city.setText(request.getCity());
        holder.service.setText(request.getService());
        holder.dateofbooking.setText(request.getBookdate());
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView from, contact, area, city, service, dateofbooking;

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

