package com.example.fixit;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ServiceProviderDashboard extends AppCompatActivity {

    private static final String PREF_NAME = "first_time_pref";
    private static final String IS_FIRST_TIME = "is_first_time";

    BottomNavigationView bnView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_dashboard);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#F44336"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Home");

        bnView = findViewById(R.id.bnView);
        frameLayout = findViewById(R.id.frameLayout);

        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean(IS_FIRST_TIME, true);

        if (isFirstTime) {
            frameLayout.post(new Runnable() {
                @Override
                public void run() {
                    showPopup();
                }
            });

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(IS_FIRST_TIME, false);
            editor.apply();
        }

        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home) {
                    loadFragment(new HomeFragment(), 1);
                    actionBar.setTitle("Home");
                } else if (id == R.id.aboutus) {
                    loadFragment(new AboutUsFragment(), 0);
                    actionBar.setTitle("About Us");
                } else if (id == R.id.chats) {
                    loadFragment(new ChatsFragment(), 0);
                    actionBar.setTitle("Chats");
                } else if (id == R.id.earnings) {
                    loadFragment(new EarningsFragment(), 0);
                    actionBar.setTitle("Earnings");
                } else {
                    loadFragment(new ProfileFragment(), 0);
                    actionBar.setTitle("Profile");
                }

                return true;
            }
        });

        bnView.setSelectedItemId(R.id.home);
    }

    public void loadFragment(Fragment fragment, int flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag == 1)
            ft.add(R.id.frameLayout, fragment);
        else
            ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

    private void showPopup() {
        View popupView = LayoutInflater.from(this).inflate(R.layout.serviceprovider_popup, null);

        int width = (int) getResources().getDisplayMetrics().density * 500;
        int height = (int) getResources().getDisplayMetrics().density * 300;

        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.setElevation(10);
        popupWindow.showAtLocation(frameLayout, Gravity.CENTER, 0, 0);
        Button closeButton = popupView.findViewById(R.id.ok);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

}
