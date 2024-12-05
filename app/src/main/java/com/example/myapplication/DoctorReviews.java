package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DoctorReviews extends AppCompatActivity {

    Button write, read;
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

        write.findViewById(R.id.wreview);
        read.findViewById(R.id.rreview);
        doctor.findViewById(R.id.doctor);
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

}
