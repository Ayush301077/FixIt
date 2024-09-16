package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    ArrayList<ServicesModel> serviceArray = new ArrayList<>();
    Button logout;
    FirebaseAuth auth;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        logout = view.findViewById(R.id.logout);
        serviceArray.add(new ServicesModel("Plumber"));
        serviceArray.add(new ServicesModel("Electrician"));
        serviceArray.add(new ServicesModel("Painter"));

        RecyclerView recyclerView = view.findViewById(R.id.servicesRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ProfileServicesAdapter adapter = new ProfileServicesAdapter(getContext(), serviceArray);
        recyclerView.setAdapter(adapter);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });
    }
}