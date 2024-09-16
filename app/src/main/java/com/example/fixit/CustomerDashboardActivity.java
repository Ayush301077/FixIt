package com.example.fixit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerDashboardActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    BottomNavigationView bnView;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(CustomerDashboardActivity.this, RoleSelectorActivity.class));
            finish();
            return;
        }

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#F44336"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Home");
        bnView = findViewById(R.id.bnView);
        frameLayout = findViewById(R.id.frameLayout);

        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.home)
                {
                    loadFragment(new CustomerHomeFragment(), 1);
                    actionBar.setTitle("Home");
                }
                else if(id == R.id.favorites)
                {
                    loadFragment(new CustomerFavoritesFragment(), 0);
                    actionBar.setTitle("Favorites");
                }
                else if(id == R.id.chats)
                {
                    loadFragment(new CustomerChatFragment(), 0);
                    actionBar.setTitle("Chats");
                }
                else if(id == R.id.bookings)
                {
                    loadFragment(new CustomerBookingsFragment(), 0);
                    actionBar.setTitle("Bookings");
                }
                else
                {
                    loadFragment(new CustomersProfileFragment(), 0);
                    actionBar.setTitle("Profile");
                }

                return true;
            }
        });
        bnView.setSelectedItemId(R.id.home);
    }
    public void loadFragment(Fragment fragment, int flag)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(flag ==1)
            ft.add(R.id.frameLayout, fragment);
        else
            ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }
}