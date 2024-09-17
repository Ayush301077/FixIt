package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ServiceproviderEditProfile extends AppCompatActivity {

    TextView name, contact,email, street,city,services;
    Button save;
//    ImageView profile_image;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceprovider_edit_profile);

        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        email = findViewById(R.id.email);
        street = findViewById(R.id.street);
        city = findViewById(R.id.city);
        services = findViewById(R.id.services);
//        profile_image = findViewById(R.id.profile_image);
        save = findViewById(R.id.save);

        db = FirebaseFirestore.getInstance();

        String userId = getIntent().getStringExtra("userId");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> serviceProvider = new HashMap<>();

                serviceProvider.put("name", name.getText().toString());
                serviceProvider.put("contact", contact.getText().toString());
                serviceProvider.put("email", email.getText().toString());
                serviceProvider.put("street", street.getText().toString());
                serviceProvider.put("city", city.getText().toString());
                serviceProvider.put("services", services.getText().toString());
//              serviceProvider.put("profile_image", profile_image);


                db.collection("Users").document(userId).collection("profile").add(serviceProvider)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                            }
                        });
                startActivity(new Intent(ServiceproviderEditProfile.this, ServiceProviderDashboard.class));
                finish();
            }
        });



    }
}