package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerSignInActivity extends AppCompatActivity {
    FirebaseAuth auth;
    TextInputEditText emailInput, passwordinput;
    Button signInBtn;
    TextView signUpTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signin);
        auth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.emailInput);
        passwordinput = findViewById(R.id.passwordinput);
        signInBtn = findViewById(R.id.signInBtn);
        signUpTxtView = findViewById(R.id.signUpTxtView);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String pass = passwordinput.getText().toString();

                if(email.isEmpty() || pass.isEmpty())
                {
                    Toast.makeText(CustomerSignInActivity.this, "Enter valid credentials", Toast.LENGTH_SHORT).show();
                }

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        startActivity(new Intent(CustomerSignInActivity.this, CustomerDashboardActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(CustomerSignInActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        signUpTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerSignInActivity.this, CustomerSignUpActivity.class));
            }
        });


    }
}