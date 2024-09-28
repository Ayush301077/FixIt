package com.example.fixit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.ArrayList;

public class EarningsFragment extends Fragment {

    private FirebaseFirestore db;
    private ArrayList<RecentEarningsModel> recentEarningsArray;
    private RecentEarningsAdapter adapter;

    public EarningsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_earnings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve the userId from SharedPreferences or Intent
        String userIdFromIntent = getActivity().getIntent().getStringExtra("userId");
        final String userId;
        if (userIdFromIntent == null) {
            SharedPreferences prefs = getActivity().getSharedPreferences("FixItApp", getContext().MODE_PRIVATE);
            userId = prefs.getString("userId", null);  // Default userId from SharedPreferences
        } else {
            userId = userIdFromIntent;  // UserId from Intent
        }

        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();

        // Initialize the recent earnings array list
        recentEarningsArray = new ArrayList<>();

        // Set up the RecyclerView and Adapter
        RecyclerView recyclerView = view.findViewById(R.id.recentEarningsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RecentEarningsAdapter(getContext(), recentEarningsArray);
        recyclerView.setAdapter(adapter);

        // TextView for displaying total earnings
        TextView totalEarnedMoneyTextView = view.findViewById(R.id.totalEarnedMoney);

        // Fetch earnings data from Firestore and calculate total earnings
        db.collection("Service providers")
                .document(userId) // Fetch earnings for the logged-in service provider
                .collection("Earnings")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;  // Exit if there's an error
                        }

                        // Clear old earnings data
                        recentEarningsArray.clear();
                        double totalEarnings = 0;  // Initialize total earnings

                        // Loop through the earnings documents
                        for (QueryDocumentSnapshot doc : snapshots) {
                            // Convert Firestore document to RecentEarningsModel object
                            RecentEarningsModel earning = doc.toObject(RecentEarningsModel.class);
                            recentEarningsArray.add(earning);

                            // Convert earned string to a double value for summation
                            double earnedAmount = Double.parseDouble(earning.getEarned());
                            totalEarnings += earnedAmount;
                        }

                        // Update the total earnings TextView
                        totalEarnedMoneyTextView.setText(String.valueOf(totalEarnings));

                        // Notify the adapter that data has changed
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
