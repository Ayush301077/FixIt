package com.example.fixit;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoriesList extends AppCompatActivity {
    RecyclerView recyclerView;
    CategoriesListAdapter adapter;
    ArrayList<CategorieslistItem> categorieslistItemArray = new ArrayList<>();
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);

        recyclerView = findViewById(R.id.servicesRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        categorieslistItemArray.add(new CategorieslistItem("Medicinal Services", R.drawable.services_medicinal));
        categorieslistItemArray.add(new CategorieslistItem("Medicinal Services", R.drawable.services_medicinal));
        categorieslistItemArray.add(new CategorieslistItem("Medicinal Services", R.drawable.services_medicinal));
        categorieslistItemArray.add(new CategorieslistItem("Medicinal Services", R.drawable.services_medicinal));
        categorieslistItemArray.add(new CategorieslistItem("Medicinal Services", R.drawable.services_medicinal));
        categorieslistItemArray.add(new CategorieslistItem("Car Services", R.drawable.services_medicinal));

        adapter = new CategoriesListAdapter(getApplicationContext(), categorieslistItemArray);
        recyclerView.setAdapter(adapter);

        searchView = findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<CategorieslistItem> filteredList = new ArrayList<>();
                for(CategorieslistItem item : categorieslistItemArray)
                {
                    if(item.getServiceType().toLowerCase().contains(newText.toLowerCase()))
                    {
                        filteredList.add(item);
                    }
                }

                if(filteredList.isEmpty())
                    Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
                else
                    adapter.setFilteredList(filteredList);
                return true;
            }
        });

    }
}