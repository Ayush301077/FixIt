package com.example.fixit;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class RequestedBookingFragment extends Fragment {

    ArrayList<ServiceProviderInfo> serviceProviderInfoArrayList = new ArrayList<>();
    FirebaseFirestore db;
    CollectionReference collectionRef;

    public RequestedBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requested_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String userIdFromIntent = getActivity().getIntent().getStringExtra("userId");
        final String userId;
        if (userIdFromIntent == null) {
            SharedPreferences prefs = getActivity().getSharedPreferences("FixItApp", MODE_PRIVATE);
            userId = prefs.getString("userId", null); // Retrieve saved userId
        } else {
            userId = userIdFromIntent;
        }


        RecyclerView requestedrecycler = view.findViewById(R.id.requestedrecycler);
        requestedrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        ServiceProviderInfoAdapter requestedadapter = new ServiceProviderInfoAdapter(getContext(),serviceProviderInfoArrayList);
        requestedrecycler.setAdapter(requestedadapter);

        db = FirebaseFirestore.getInstance();
        collectionRef = db.collection("Customers").document(userId).collection("RequestedBookings");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<ServiceProviderInfo> itemList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String documentId = document.getId(); // Get document ID
                        ServiceProviderInfo item = document.toObject(ServiceProviderInfo.class);
                        item.setServiceProviderId(documentId); // Store document ID in your model
                        itemList.add(item);  // Add item to the list
                    }
                    // Pass the list to your RecyclerView adapter
                    requestedadapter.setFilteredList(itemList);
                }
                else
                    Toast.makeText(getContext(), "Failed to get Service provider list", Toast.LENGTH_SHORT).show();
            }
        });


    }
}