package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WriteReview extends AppCompatActivity {

    TextView docname;
    EditText review;
    private DatabaseReference root;
    Intent intent;
    Bundle bundle;
    String doctorName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_write_review);
        root = FirebaseDatabase.getInstance().getReference();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent = getIntent();
        bundle = intent.getExtras();
        doctorName = bundle.getString("docname");
        docname = findViewById(R.id.docname);
        review = findViewById(R.id.toReview);

        if(doctorName !=null && !doctorName.isEmpty())
            docname.setText(doctorName);
    }

    public void writeReviewToDatabase(View v){
        if(review.getText()==null || review.getText().toString().isEmpty()){
            Toast.makeText(WriteReview.this,"Enter a review",Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference entry = root.child(doctorName);
            DatabaseReference reviews = entry.child("Review");
            DatabaseReference newReview = reviews.push();
            newReview.child("Review").setValue(review.getText());
            Toast.makeText(this,"Review Added to database",Toast.LENGTH_SHORT).show();
        }
    }
}