package com.example.fixit;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CustomerFavoritesFragment extends Fragment {

    RecyclerView favoriteRecycler;
    private ServiceProviderInfoAdapter spiadapter;
    private FavoritesStorage favoritesStorage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the current customer's userId
        String userIdFromIntent = getActivity().getIntent().getStringExtra("userId");
        final String userId;
        if (userIdFromIntent == null) {
            SharedPreferences prefs = getActivity().getSharedPreferences("FixItApp", MODE_PRIVATE);
            userId = prefs.getString("userId", null);
        } else {
            userId = userIdFromIntent;
        }

        // Initialize storage helper with customer-specific userId
        favoritesStorage = new FavoritesStorage(getContext(), userId);

        // Get the list of favorites for this customer
        List<ServiceProviderInfo> favorites = favoritesStorage.getFavorites();

        favoriteRecycler = view.findViewById(R.id.favoriteRecycler);
        favoriteRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        spiadapter = new ServiceProviderInfoAdapter(getContext(), new ArrayList<>(favorites));
        favoriteRecycler.setAdapter(spiadapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the list of favorites when coming back from ServiceProviderDetailsActivity
        List<ServiceProviderInfo> updatedFavorites = favoritesStorage.getFavorites();
        spiadapter.setFilteredList(new ArrayList<>(updatedFavorites));
    }
}
