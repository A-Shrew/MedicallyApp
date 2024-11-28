package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {

    private EditText phoneNumber, password;
    private Button logInButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        phoneNumber = findViewById(R.id.userInput6);
        password = findViewById(R.id.userInput7);
        logInButton = findViewById(R.id.button3);

        logInButton.setOnClickListener(v -> verifyLogin());
    }

    private void verifyLogin() {
        String enteredPhone = phoneNumber.getText().toString().trim();
        String enteredPassword = password.getText().toString().trim();

        if (enteredPhone.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child(enteredPhone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String storedPassword = dataSnapshot.child("password").getValue(String.class);

                    if (storedPassword != null && storedPassword.equals(enteredPassword)) {
                        Toast.makeText(LogIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        navigateToHomePage();
                    } else {
                        Toast.makeText(LogIn.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        navigateToMainActivity();
                    }
                } else {
                    Toast.makeText(LogIn.this, "User not found", Toast.LENGTH_SHORT).show();
                    navigateToMainActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LogIn.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                navigateToMainActivity();
            }
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LogIn.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToHomePage() {
        Intent intent = new Intent(LogIn.this, HomePage.class);
        startActivity(intent);
        finish();
    }
}