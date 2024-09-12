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

    ArrayList<RequestModel> requestArray;
    Context context;
    public NewRequestsAdapter(Context context, ArrayList<RequestModel> requestArray){
        this.context = context;
        this.requestArray = requestArray;

    }

    @NonNull
    @Override
    public NewRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.serviceprovider_request_new, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewRequestsAdapter.ViewHolder holder, int position) {
        Log.d("RecyclerView", "Binding item at position: " + position);

        holder.from.setText(requestArray.get(position).from);
        holder.contact.setText(requestArray.get(position).contact);
        holder.area.setText(requestArray.get(position).area);
        holder.city.setText(requestArray.get(position).city);
        holder.service.setText(requestArray.get(position).service);

    }

    @Override
    public int getItemCount() {
        return requestArray.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView from, contact, area, city, service;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            from = itemView.findViewById(R.id.textView1);
            contact = itemView.findViewById(R.id.textView2);
            area = itemView.findViewById(R.id.textView3);
            city = itemView.findViewById(R.id.textView4);
            service = itemView.findViewById(R.id.textView5);

        }
    }
}
