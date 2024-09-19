package com.example.fixit;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ServiceProvidersListActivity extends AppCompatActivity {
    RecyclerView serviceproviderinfo;
    ArrayList<ServiceProviderInfo> serviceProviderInfoArrayList = new ArrayList<>();
    ArrayList<String> filter = new ArrayList<>();
    FirebaseFirestore db;
    SearchView searchView;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers_list);

        serviceproviderinfo = findViewById(R.id.servicesRecycler);
        serviceproviderinfo.setLayoutManager(new LinearLayoutManager(this));
        ServiceProviderInfoAdapter adapter = new ServiceProviderInfoAdapter(getApplicationContext(),serviceProviderInfoArrayList);
        serviceproviderinfo.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        db.collection("Service providers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d : list)
                        {
                            ServiceProviderInfo obj = d.toObject(ServiceProviderInfo.class);
                            serviceProviderInfoArrayList.add(obj);
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to get Service provider list", Toast.LENGTH_SHORT).show();
                    }
                });


        searchView = findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<ServiceProviderInfo> filteredList = new ArrayList<>();
                for(ServiceProviderInfo item : serviceProviderInfoArrayList)
                {
                    if(item.getName().toLowerCase().contains(s.toLowerCase()))
                    {
                        filteredList.add(item);
                    }
                }

                if(filteredList.isEmpty())
                    Toast.makeText(getApplicationContext(), "No such data found", Toast.LENGTH_SHORT).show();
                else
                    adapter.setFilteredList(filteredList);

                return true;
            }
        });


        spinner = findViewById(R.id.spinner);
        filter.add("Name");
        filter.add("Service");
        filter.add("City");
        filter.add("Default");
        ArrayAdapter<String> spinner1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, filter);
        spinner.setAdapter(spinner1);




    }
}