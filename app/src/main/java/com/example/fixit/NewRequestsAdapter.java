package com.example.fixit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class NewRequestsAdapter extends RecyclerView.Adapter<NewRequestsAdapter.ViewHolder> {
    private String userId;
    private Context context;
    private ArrayList<RequestModel> requestList;
    private AcceptedRequestsAdapter acceptedRequestsAdapter; // Reference to AcceptedRequestsAdapter
    private FirebaseFirestore db; // Firebase Firestore instance

    // Constructor for NewRequestsAdapter
    public NewRequestsAdapter(Context context, ArrayList<RequestModel> requestList, AcceptedRequestsAdapter acceptedRequestsAdapter, String userId) {
        this.context = context;
        this.requestList = requestList;
        this.acceptedRequestsAdapter = acceptedRequestsAdapter; // Store reference to AcceptedRequestsAdapter
        this.userId = userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.serviceprovider_request_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RequestModel request = requestList.get(position);
        holder.UserName.setText(request.getUserName());
        holder.contact.setText(request.getContact());
        holder.area.setText(request.getArea());
        holder.city.setText(request.getCity());
        holder.service.setText(request.getService());
        holder.dateofbooking.setText(request.getBookingDate());


        db = FirebaseFirestore.getInstance();
        // Handle "Accept" button click
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestModel acceptedRequest = requestList.get(position); // The request being accepted
                String serviceProviderId = acceptedRequest.getServiceProviderId(); // ID of the request to be updated

                db.collection("Service providers")
                        .document(userId)
                        .collection("acceptedRequests")
                        .add(acceptedRequest) // Add the accepted request to the "acceptedRequests" collection
                        .addOnSuccessListener(aVoid -> {
                            db.collection("Service providers")
                                    .document(userId)
                                    .collection("newRequests")
                                    .document(serviceProviderId)  // Ensure the correct ID is being used
                                    .delete()
                                    .addOnSuccessListener(aVoid1 -> {
                                        // 1. Ensure the position is valid before removing or updating the list
                                        if (position < requestList.size()) {
                                            // 2. Update the UI: Remove from NewRequestsAdapter's list and notify the adapter
                                            requestList.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, requestList.size());

                                            // 3. Add the request to AcceptedRequestsAdapter
                                            acceptedRequestsAdapter.addAcceptedRequest(acceptedRequest);
                                        } else {
                                            // Log an error or handle unexpected position issues
                                            Log.e("NewRequestsAdapter", "Invalid position: " + position);
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(context, "Failed deleting new request", Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Failed accepting the request", Toast.LENGTH_LONG).show();
                        });
            }
        });

        // Handle "Reject" button click (optional)
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If you want to add rejection functionality, handle it here
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    // ViewHolder class to hold each request's views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView UserName, contact, area, city, service, dateofbooking;
        ImageView accept, reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            UserName = itemView.findViewById(R.id.from);
            contact = itemView.findViewById(R.id.contact);
            area = itemView.findViewById(R.id.area);
            city = itemView.findViewById(R.id.city);
            service = itemView.findViewById(R.id.service);
            dateofbooking = itemView.findViewById(R.id.dateofbooking);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);
        }
    }

}
