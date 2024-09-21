package com.example.fixit;

import android.os.Bundle;

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

    public CustomerFavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize storage helper
        favoritesStorage = new FavoritesStorage(getContext());

        // Get the list of favorites
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
        Log.d("CustomerFavoritesFragment", "Updated favorites: " + updatedFavorites.toString());

        spiadapter.setFilteredList(new ArrayList<>(updatedFavorites));// Update the adapter
    }
}





//package com.example.fixit;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//
//public class CustomerFavoritesFragment extends Fragment {
//
//    RecyclerView favoriteRecycler;
//    private ServiceProviderInfoAdapter spiadapter;
//    private FavoritesStorage favoritesStorage;  // Storage helper
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_customer_favorites, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        favoriteRecycler = view.findViewById(R.id.favoriteRecycler);
//        favoriteRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        favoritesStorage = new FavoritesStorage(getContext());  // Initialize the storage
//
//        // Retrieve the list of favorites from storage
//        ArrayList<ServiceProviderInfo> favorites = favoritesStorage.getFavorites();
//        if (favorites == null) {
//            favorites = new ArrayList<>();
//        }
//
//        // Initialize the adapter with the favorites list
//        spiadapter = new ServiceProviderInfoAdapter(getContext(), favorites);
//        favoriteRecycler.setAdapter(spiadapter);
//    }
//}
//
