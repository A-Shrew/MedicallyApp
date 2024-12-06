package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DoctorReviews extends AppCompatActivity {

    Button write, read, home;
    Spinner doctor;
    String docname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_reviews);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        write = findViewById(R.id.wreview);
        read = findViewById(R.id.rreview);
        doctor = findViewById(R.id.doctor);
        home = findViewById(R.id.home3);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.doctors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctor.setAdapter(adapter);
    }

    public void readReview(View v) {
        docname = doctor.getSelectedItem().toString();
        Intent intent = new Intent(this, ReadReview.class);
        Bundle bundle = new Bundle();
        bundle.putString("docname",docname);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }

    public void writeReview(View view) {
        docname = doctor.getSelectedItem().toString();
        Intent intent = new Intent(this, WriteReview.class);
        Bundle bundle = new Bundle();
        bundle.putString("docname",docname);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void back(View view){
        Intent intent = new Intent(this, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
