package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RoleSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roleselector);

        Button servicetaker = findViewById(R.id.button1);
        Button serviceprovider = findViewById(R.id.button2);

        servicetaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoleSelectorActivity.this, CustomerSelection.class));
                finish();
            }
        });
        serviceprovider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoleSelectorActivity.this, ServiceProviderActivity.class));
                finish();
            }
        });

    }
}