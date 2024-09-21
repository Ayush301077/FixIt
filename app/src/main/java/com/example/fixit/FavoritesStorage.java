package com.example.fixit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritesStorage {

    private static final String PREFERENCES_KEY = "favorites";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public FavoritesStorage(Context context) {
        sharedPreferences = context.getSharedPreferences("FixItPrefs", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void addFavorite(ServiceProviderInfo serviceProvider) {
        List<ServiceProviderInfo> favorites = getFavorites();
        favorites.add(serviceProvider);
        saveFavorites(favorites);
    }

    public void removeFavorite(ServiceProviderInfo serviceProvider) {
        List<ServiceProviderInfo> favorites = getFavorites();

        // Log the favorites before removal to check the current state
        Log.d("FavoritesStorage", "Before removal: " + favorites.toString());

        favorites.remove(serviceProvider);

        // Log the favorites after removal to see if the item was removed
        Log.d("FavoritesStorage", "After removal: " + favorites.toString());

        saveFavorites(favorites);
    }


    public boolean isFavorite(ServiceProviderInfo serviceProvider) {
        List<ServiceProviderInfo> favorites = getFavorites();
        for (ServiceProviderInfo favorite : favorites) {
            if (favorite.getEmail().equals(serviceProvider.getEmail())) {
                return true;  // Found the provider in favorites
            }
        }
        return false;  // Not found
    }

    public List<ServiceProviderInfo> getFavorites() {
        String json = sharedPreferences.getString(PREFERENCES_KEY, null);
        Type type = new TypeToken<ArrayList<ServiceProviderInfo>>() {}.getType();
        List<ServiceProviderInfo> favorites = gson.fromJson(json, type);
        if (favorites != null) {
            return favorites;
        } else {
            return new ArrayList<>();
        }
    }

    private void saveFavorites(List<ServiceProviderInfo> favorites) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(favorites);
        Log.d("FavoritesStorage", "Saving favorites: " + json);
        editor.putString(PREFERENCES_KEY, json);
        editor.apply();
    }
}