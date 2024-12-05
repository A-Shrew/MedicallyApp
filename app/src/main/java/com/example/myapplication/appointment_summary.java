package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class appointment_summary extends AppCompatActivity {
    String doctor, date, location;
    TextView doctor_text, date_text, location_text;

    Intent intent;
    Bundle bundle;
    Button reviewbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appointment_summary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent = getIntent();
        bundle = intent.getExtras();

        doctor = bundle.getString("doctor_Type");
        date = bundle.getString("appointment_Date");
        location = bundle.getString("appointment_City");

        doctor_text = findViewById(R.id.doc);
        date_text = findViewById(R.id.date);
        location_text = findViewById(R.id.location);
        reviewbutton = findViewById(R.id.toReview);

        doctor_text.setText(doctor);
        date_text.setText(date);
        location_text.setText(location);
    }

    public void back(View view){
        Intent intent = new Intent(this, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    public void onReview(View v){
        Intent intent2 = new Intent(this, DoctorReviews.class);
        startActivity(intent2);
        finish();
    }
}