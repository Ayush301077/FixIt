package com.example.fixit;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomersProfileFragment extends Fragment {

    TextView name,contact,email,area, city;
    Button editProfilebutton,logout;
    FirebaseFirestore db;
    FirebaseAuth auth;

    public CustomersProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customers_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        String userIdFromIntent = getActivity().getIntent().getStringExtra("userId");
        final String userId;
        if (userIdFromIntent == null) {
            SharedPreferences prefs = getActivity().getSharedPreferences("FixItApp", MODE_PRIVATE);
            userId = prefs.getString("userId", null); // Retrieve saved userId
        } else {
            userId = userIdFromIntent;
        }

        auth = FirebaseAuth.getInstance();
        logout = view.findViewById(R.id.logout);
        editProfilebutton = view.findViewById(R.id.editProfilebutton);

        name = view.findViewById(R.id.name);
        contact = view.findViewById(R.id.contact);
        email = view.findViewById(R.id.email);
        area = view.findViewById(R.id.area);
        city = view.findViewById(R.id.city);


        DocumentReference docRef = db.collection("Customers").document(userId);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    name.setText(documentSnapshot.getString("name"));
                    contact.setText(documentSnapshot.getString("contact"));
                    email.setText(documentSnapshot.getString("email"));
                    area.setText(documentSnapshot.getString("area"));
                    city.setText(documentSnapshot.getString("city"));
                } else {
                    Toast.makeText(getContext(), "No profile found", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Unable to retrieve profile", Toast.LENGTH_SHORT).show();
            }
        });

        editProfilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CustomersEditProfileActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

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