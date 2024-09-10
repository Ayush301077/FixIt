package com.example.fixit;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerSignUpActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextInputEditText emailInput, passwordinput;
    Button signUpBtn;
    TextView signInTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);

        auth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.emailInput);
        passwordinput = findViewById(R.id.passwordinput);
        signUpBtn = findViewById(R.id.signUpBtn);
        signInTxtView = findViewById(R.id.signInTxtView);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String pass = passwordinput.getText().toString();

                if(email.isEmpty() || pass.isEmpty())
                {
                    Toast.makeText(CustomerSignUpActivity.this, "Enter valid credentials", Toast.LENGTH_SHORT).show();
                }

                auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                Log.d(TAG, "createUserWithEmail:success");
                                startActivity(new Intent(CustomerSignUpActivity.this, CustomerSignInActivity.class));
                                finish();
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(CustomerSignUpActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        //jihihi

        signInTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerSignUpActivity.this, CustomerSignInActivity.class));
            }
        });

    }
}