package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    EditText firstName, lastName, email, phoneNumber, password;
    NumberPicker agePicker;
    RadioGroup genderGroup;
    Button signUpButton;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        firstName = findViewById(R.id.userInput);
        lastName = findViewById(R.id.userInput2);
        email = findViewById(R.id.userInput3);
        phoneNumber = findViewById(R.id.userInput4);
        password = findViewById(R.id.userInput5);
        agePicker = findViewById(R.id.agePicker);
        genderGroup = findViewById(R.id.radioGroup);
        signUpButton = findViewById(R.id.button4);

        agePicker.setMinValue(1);
        agePicker.setMaxValue(100);
        agePicker.setValue(25);
        agePicker.setWrapSelectorWheel(false);

        signUpButton.setOnClickListener(v -> saveUserData());
    }

    private void saveUserData() {
        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String userEmail = email.getText().toString();
        String phone = phoneNumber.getText().toString();
        String userPassword = password.getText().toString();
        int age = agePicker.getValue();

        int selectedGenderId = genderGroup.getCheckedRadioButtonId();
        RadioButton selectedGender = findViewById(selectedGenderId);
        String gender = (selectedGender != null) ? selectedGender.getText().toString() : "Not specified";

        if (fName.isEmpty() || lName.isEmpty() || userEmail.isEmpty() || phone.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(fName, lName, userEmail, phone, userPassword, age, gender);

        databaseReference.child(phone).setValue(user)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(SignIn.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(SignIn.this, "Failed to Register: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public static class User {
        public String firstName, lastName, email, phone, password, gender;
        public int age;

        public User() {
        }

        public User(String firstName, String lastName, String email, String phone, String password, int age, String gender) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phone = phone;
            this.password = password;
            this.age = age;
            this.gender = gender;
        }
    }
}