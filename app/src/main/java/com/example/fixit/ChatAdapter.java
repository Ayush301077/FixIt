package com.example.fixit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    ArrayList<ChatModel> chatArray;
    Context context;

    public ChatAdapter(ArrayList<ChatModel> chatArray, Context context) {
        this.chatArray = chatArray;
        this.context = context;
    }

    public void setFilteredList(ArrayList<ChatModel> filteredList){
        this.chatArray = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {

        holder.name.setText(chatArray.get(position).name);
        holder.message.setText(chatArray.get(position).message);
        holder.timestamp.setText(chatArray.get(position).timestamp);


    }

    @Override
    public int getItemCount() {
        return chatArray.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, message, timestamp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
            timestamp = itemView.findViewById(R.id.timestamp);

        }
    }
}
