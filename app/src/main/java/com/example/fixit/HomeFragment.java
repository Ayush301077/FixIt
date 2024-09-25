package com.example.fixit;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    // UI Elements
    TextView newRequestsDropdown, acceptedRequestsDropdown;
    ArrayList<RequestModel> requestArray = new ArrayList<>();
    ArrayList<RequestModel> acceptedArray = new ArrayList<>();
    NewRequestsAdapter newRequestsAdapter;
    AcceptedRequestsAdapter acceptedRequestsAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Firebase Firestore setup
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userIdFromIntent = getActivity().getIntent().getStringExtra("userId");
        final String userId;
        if (userIdFromIntent == null) {
            SharedPreferences prefs = getActivity().getSharedPreferences("FixItApp", MODE_PRIVATE);
            userId = prefs.getString("userId", null);
        } else {
            userId = userIdFromIntent;
        }

        // Setting up the dropdown for new requests
        newRequestsDropdown = view.findViewById(R.id.newRequestsDropdown);
        acceptedRequestsDropdown = view.findViewById(R.id.acceptedRequestsDropdown);

        // Setting up RecyclerView for accepted requests
        RecyclerView recyclerView2 = view.findViewById(R.id.recyclerview2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the acceptedArray and acceptedRequestsAdapter
        acceptedArray = new ArrayList<>();
        acceptedRequestsAdapter = new AcceptedRequestsAdapter(getContext(), acceptedArray);
        recyclerView2.setAdapter(acceptedRequestsAdapter);

        // Setting up RecyclerView for new requests
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the newRequestsAdapter and pass the acceptedRequestsAdapter
        requestArray = new ArrayList<>();
        newRequestsAdapter = new NewRequestsAdapter(getContext(), requestArray, acceptedRequestsAdapter, userId); // Now acceptedRequestsAdapter is initialized
        recyclerView.setAdapter(newRequestsAdapter);



        // Listen to changes in the "newRequests" collection for the current service provider
        db.collection("Service providers")
                .document(userId)  // Assuming you have the service provider's ID
                .collection("newRequests")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        // Clear the old request data
                        requestArray.clear();

                        // Loop through new booking requests and add to the array
                        for (QueryDocumentSnapshot doc : snapshots) {
                            String serviceProviderId = doc.getId();  // Get the document ID

                            // Convert Firestore document to BookingRequest object
                            BookingRequest request = doc.toObject(BookingRequest.class);

                            // Add the request to the array along with the serviceProviderId
                            requestArray.add(new RequestModel(
                                    request.getUserName(),
                                    request.getContact(),
                                    request.getArea(),
                                    request.getCity(),
                                    request.getService(),
                                    request.getBookingDate(),
                                    serviceProviderId  // Pass the serviceProviderId here
                            ));
                        }

                        // Notify the adapter that data has changed
                        newRequestsAdapter.notifyDataSetChanged();
                    }
                });

        // Handle new requests dropdown toggle behavior
        newRequestsDropdown.setOnClickListener(new View.OnClickListener() {
            boolean isVisible = true;

            @Override
            public void onClick(View view) {
                if (isVisible) {
                    newRequestsDropdown.setText("New work requests                                                            ▼");
                    recyclerView.setVisibility(View.GONE);
                } else {
                    newRequestsDropdown.setText("New work requests                                                            ▲");
                    recyclerView.setVisibility(View.VISIBLE);
                }
                isVisible = !isVisible;
            }
        });

        // Listen to changes in the "acceptedRequests" collection for the current service provider
        db.collection("Service providers")
                .document(userId)
                .collection("acceptedRequests")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        // Clear the old accepted requests data
                        acceptedArray.clear();

                        // Loop through accepted booking requests and add to the array
                        for (QueryDocumentSnapshot doc : snapshots) {
                            String serviceProviderId = doc.getId();  // Get the document ID

                            // Convert Firestore document to BookingRequest object
                            BookingRequest request = doc.toObject(BookingRequest.class);

                            // Add the request to the accepted array along with the serviceProviderId
                            acceptedArray.add(new RequestModel(
                                    request.getUserName(),
                                    request.getContact(),
                                    request.getArea(),
                                    request.getCity(),
                                    request.getService(),
                                    request.getBookingDate(),
                                    serviceProviderId  // Pass the serviceProviderId here
                            ));
                        }

                        // Notify the adapter that data has changed
                        acceptedRequestsAdapter.notifyDataSetChanged();
                    }
                });

        // Handle accepted requests dropdown toggle behavior
        acceptedRequestsDropdown.setOnClickListener(new View.OnClickListener() {
            boolean isVisible2 = true;

            @Override
            public void onClick(View view) {
                if (isVisible2) {
                    acceptedRequestsDropdown.setText("Accepted requests                                                             ▼");
                    recyclerView2.setVisibility(View.GONE);
                } else {
                    acceptedRequestsDropdown.setText("Accepted requests                                                             ▲");
                    recyclerView2.setVisibility(View.VISIBLE);
                }
                isVisible2 = !isVisible2;
            }
        });
    }
}
