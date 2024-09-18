package com.example.fixit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class ServiceproviderEditProfile extends AppCompatActivity {

    TextView name, contact, email, street, city, services;
    ShapeableImageView profile_image;
    Button save;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageReference;
    String TAG = "ProfileUpdate";
    Uri selectedImageUri;  // Uri to hold the selected image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceprovider_edit_profile);

        // Initialize the views
        profile_image = findViewById(R.id.profile_image);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        email = findViewById(R.id.email);
        street = findViewById(R.id.street);
        city = findViewById(R.id.city);
        services = findViewById(R.id.services);
        save = findViewById(R.id.save);

        // Firebase instances
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // Get userId from Intent or SharedPreferences
        String userIdFromIntent = getIntent().getStringExtra("userId");
        final String userId;
        if (userIdFromIntent == null) {
            SharedPreferences prefs = getSharedPreferences("FixItApp", MODE_PRIVATE);
            userId = prefs.getString("userId", null); // Retrieve saved userId
        } else {
            userId = userIdFromIntent;
        }

        // ActivityResultLauncher to handle the image picker result
        ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        profile_image.setImageURI(selectedImageUri);  // Display selected image
                    }
                }
        );

        // OnClickListener for profile image selection
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open gallery to select an image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imagePickerLauncher.launch(intent);
            }
        });

        // Save button functionality to update profile data and image
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Collect the profile data from the form
                String nameStr = name.getText().toString();
                String contactStr = contact.getText().toString();
                String emailStr = email.getText().toString();
                String streetStr = street.getText().toString();
                String cityStr = city.getText().toString();
                String servicesStr = services.getText().toString();

                // Logging for debugging
                Log.d(TAG, "Profile Data: " + nameStr + ", " + contactStr + ", " + emailStr + ", " + streetStr + ", " + cityStr + ", " + servicesStr);

                // If an image has been selected, upload it to Firebase Storage
                if (selectedImageUri != null) {
                    // Create a reference in Firebase Storage for the profile image
                    StorageReference profileImageRef = storageReference.child("profile_images/" + userId + ".jpg");

                    // Upload the image
                    profileImageRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get the image URL after upload
                            profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();

                                    // Now that we have the image URL, we can update the profile data
                                    updateProfile(userId, nameStr, contactStr, emailStr, streetStr, cityStr, servicesStr, imageUrl);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e(TAG, "Error getting download URL: " + e.getMessage());
                                    Toast.makeText(getApplicationContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Image upload failed: " + e.getMessage());
                            Toast.makeText(getApplicationContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // If no image was selected, just update the profile without image
                    updateProfile(userId, nameStr, contactStr, emailStr, streetStr, cityStr, servicesStr, null);
                }
            }
        });
    }

    // Method to update profile data in Firestore
    private void updateProfile(String userId, String name, String contact, String email, String street, String city, String services, String imageUrl) {
        // Create a HashMap to store the profile data
        HashMap<String, Object> serviceProvider = new HashMap<>();
        serviceProvider.put("name", name);
        serviceProvider.put("contact", contact);
        serviceProvider.put("email", email);
        serviceProvider.put("street", street);
        serviceProvider.put("city", city);
        serviceProvider.put("services", services);

        // If imageUrl is not null, add it to the profile data
        if (imageUrl != null) {
            serviceProvider.put("profileImage", imageUrl);
        }

        // Reference to the specific user's profile document in Firestore
        DocumentReference docRef = db.collection("Users").document(userId).collection("profile").document("profileData");

        // Update the profile data in Firestore
        docRef.set(serviceProvider)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error updating profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error updating profile: " + e.getMessage());
                        Toast.makeText(getApplicationContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });

        // Navigate back to the ServiceProviderDashboard after updating the profile
        startActivity(new Intent(ServiceproviderEditProfile.this, ServiceProviderDashboard.class));
        finish(); // Finish the activity to prevent going back to this screen
    }
}
