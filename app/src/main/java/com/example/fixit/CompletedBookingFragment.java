package com.example.fixit;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CompletedBookingFragment extends Fragment {

    ArrayList<ServiceProviderInfo> completedServiceModelArrayList = new ArrayList<>();
    FirebaseFirestore db;
    CollectionReference collectionRef;
    ServiceProviderInfoAdapter completedAdapter;  // Using the existing ServiceProviderInfoAdapter

    public CompletedBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_completed_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the user ID from the intent or shared preferences
        String userIdFromIntent = getActivity().getIntent().getStringExtra("userId");
        final String userId;
        if (userIdFromIntent == null) {
            SharedPreferences prefs = getActivity().getSharedPreferences("FixItApp", MODE_PRIVATE);
            userId = prefs.getString("userId", null);
        } else {
            userId = userIdFromIntent;
        }

        // Initialize RecyclerView and adapter
        RecyclerView completedRecycler = view.findViewById(R.id.completedrecycler);
        completedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        completedAdapter = new ServiceProviderInfoAdapter(getContext(), completedServiceModelArrayList);
        completedRecycler.setAdapter(completedAdapter);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        collectionRef = db.collection("Customers").document(userId).collection("CompletedBookings");

        // Fetch the completed bookings from Firestore
        fetchCompletedBookings();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the completed bookings list when the fragment is resumed
        fetchCompletedBookings();
    }

    private void fetchCompletedBookings() {
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<ServiceProviderInfo> itemList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Convert Firestore document to ServiceProviderInfo object
                        ServiceProviderInfo item = document.toObject(ServiceProviderInfo.class);
                        item.setServiceProviderId(document.getId());  // Set the document ID if needed
                        itemList.add(item);
                    }

                    // Update the adapter's data
                    completedServiceModelArrayList.clear();
                    completedServiceModelArrayList.addAll(itemList);
                    completedAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to get Completed list", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
