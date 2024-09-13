package com.example.fixit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecentEarningsAdapter extends RecyclerView.Adapter<RecentEarningsAdapter.ViewHolder> {

    Context context;
    ArrayList<RecentEarningsModel> recentEarningsArray;

    public RecentEarningsAdapter(Context context, ArrayList<RecentEarningsModel> recentEarningsArray) {
        this.context = context;
        this.recentEarningsArray = recentEarningsArray;
    }

    @NonNull
    @Override
    public RecentEarningsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recent_earnings_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentEarningsAdapter.ViewHolder holder, int position) {
        holder.date.setText(recentEarningsArray.get(position).date);
        holder.customer.setText(recentEarningsArray.get(position).customer);
        holder.service.setText(recentEarningsArray.get(position).service);
        holder.contact.setText(recentEarningsArray.get(position).contact);
        holder.earned.setText(recentEarningsArray.get(position).earned);

    }

    @Override
    public int getItemCount() {
        return recentEarningsArray.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView date, customer, service, contact, earned;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            date = itemView.findViewById(R.id.date);
            customer = itemView.findViewById(R.id.customer);
            service = itemView.findViewById(R.id.service);
            contact = itemView.findViewById(R.id.contact);
            earned = itemView.findViewById(R.id.earned);

        }
    }
}
