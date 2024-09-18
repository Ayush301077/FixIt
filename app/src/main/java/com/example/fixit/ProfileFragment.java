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
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    TextView name, ratings, contact, email, street, city, services;
    ShapeableImageView profile_image;
    Button logout, uploadData, editProfile;
    FirebaseFirestore db;
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
        editProfile = view.findViewById(R.id.editProfilebutton);
        uploadData = view.findViewById(R.id.uploadData);

        name = view.findViewById(R.id.name);
        ratings = view.findViewById(R.id.ratings);
        contact = view.findViewById(R.id.contact);
        email = view.findViewById(R.id.email);
        street = view.findViewById(R.id.street);
        city = view.findViewById(R.id.city);
        services = view.findViewById(R.id.services);
        profile_image = view.findViewById(R.id.profile_image);

        // Reference to the profile document
        DocumentReference docRef = db.collection("Users").document(userId).collection("profile").document("profileData");

        // Retrieve profile data including image
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    name.setText(documentSnapshot.getString("name"));
                    contact.setText(documentSnapshot.getString("contact"));
                    email.setText(documentSnapshot.getString("email"));
                    street.setText(documentSnapshot.getString("street"));
                    city.setText(documentSnapshot.getString("city"));
                    services.setText(documentSnapshot.getString("services"));

                    // Get the profile image URL from Firestore
                    String profileImageUrl = documentSnapshot.getString("profileImage");

                    // Check if the image URL exists and is not empty
                    if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                        // Load the profile image using Picasso
                        Picasso.get()
                                .load(profileImageUrl)
                                .placeholder(R.drawable.profile_icon) // Placeholder image if none is set
                                .error(R.drawable.profile_icon)  // Error placeholder in case of any issue
                                .into(profile_image);
                    } else {
                        Toast.makeText(getContext(), "No profile image URL found", Toast.LENGTH_SHORT).show();
                    }
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

        // Edit Profile Button
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ServiceproviderEditProfile.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        // Logout Button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        // Upload Data Button
        uploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code for uploading additional data if needed
            }
        });
    }
}
