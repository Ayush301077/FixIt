package com.example.fixit;

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

public class ServiceProviderDashboard extends AppCompatActivity {

    BottomNavigationView bnView;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_dashboard);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#F44336"));
        actionBar.setBackgroundDrawable(colorDrawable);

        bnView = findViewById(R.id.bnView);
        frameLayout = findViewById(R.id.frameLayout);

        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.home)
                {
                    loadFragment(new HomeFragment(), 1);

                }
                else if(id == R.id.aboutus)
                {
                    loadFragment(new AboutUsFragment(), 0);

                }
                else if(id == R.id.chats)
                {
                    loadFragment(new ChatsFragment(), 0);

                }
                else if(id == R.id.earnings)
                {
                    loadFragment(new EarningsFragment(), 0);

                }
                else
                {
                    loadFragment(new ProfileFragment(), 0);
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