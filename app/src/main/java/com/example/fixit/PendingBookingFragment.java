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

public class PendingBookingFragment extends Fragment {

    ArrayList<PendingServiceModel> pendingServiceModelArrayList = new ArrayList<>();
    FirebaseFirestore db;
    CollectionReference collectionRef;
    PendingServiceAdapter pendingAdapter;

    public PendingBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String userIdFromIntent = getActivity().getIntent().getStringExtra("userId");
        final String userId;
        if (userIdFromIntent == null) {
            SharedPreferences prefs = getActivity().getSharedPreferences("FixItApp", MODE_PRIVATE);
            userId = prefs.getString("userId", null);
        } else {
            userId = userIdFromIntent;
        }

        RecyclerView pendingRecycler = view.findViewById(R.id.pendingrecycler);
        pendingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        pendingAdapter = new PendingServiceAdapter(getContext(), pendingServiceModelArrayList);
        pendingRecycler.setAdapter(pendingAdapter);

        db = FirebaseFirestore.getInstance();
        collectionRef = db.collection("Customers").document(userId).collection("PendingBookings");

        fetchPendingBookings();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the pending bookings list when the fragment is resumed
        fetchPendingBookings();
    }

    private void fetchPendingBookings() {
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<PendingServiceModel> itemList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String documentId = document.getId();
                        PendingServiceModel item = document.toObject(PendingServiceModel.class);
                        item.setServiceProviderId(documentId);
                        itemList.add(item);
                    }
                    pendingAdapter.setFilteredList(itemList);
                } else {
                    Toast.makeText(getContext(), "Failed to get Service provider list", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
