package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class ReadReview extends AppCompatActivity {

    TextView docname, review;
    private DatabaseReference root;
    Intent intent;
    Bundle bundle;
    String doctorName;
    Button prev,next,home;
    List<String> doctorsList;
    List<String> reviewsList;
    int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_read_review);
        root = FirebaseDatabase.getInstance().getReference();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent = getIntent();
        bundle = intent.getExtras();
        doctorName = bundle.getString("docname");
        docname = findViewById(R.id.docname2);
        review = findViewById(R.id.reviewDisplay);
        prev = findViewById(R.id.prevr);
        next = findViewById(R.id.nextr);
        home = findViewById(R.id.returnhome);
        counter = 0;


        doctorsList = new ArrayList<>();
        reviewsList = new ArrayList<>();

        if(doctorName !=null && !doctorName.isEmpty())
            docname.setText(doctorName);
    }

    public void nextReview(View v){
        counter+=1;
        if(counter>doctorsList.size())
            counter=1;
        if(doctorsList.get(counter-1).equals(doctorName)){
            review.setText(reviewsList.get(counter-1));
        }
    }

    public void prevReview(View v){
        counter-=1;
        if(counter<=0)
            counter=doctorsList.size();
        if(doctorsList.get(counter-1).equals(doctorName)){
            review.setText(reviewsList.get(counter-1));
        }
    }

    public void returnHome(View v){
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
        finish();
    }

    public void readReviewFromDatabase(){
        root.get().addOnCompleteListener(onValuesFetched);
    }
    private OnCompleteListener<DataSnapshot> onValuesFetched = new OnCompleteListener<DataSnapshot>()
    {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task)
        {
            if (!task.isSuccessful())
                Log.e("Firebase", "Error getting data", task.getException());
            else
            {
                DataSnapshot receivedValue = task.getResult();
                for(DataSnapshot node: receivedValue.getChildren())
                {
                    String docName = node.getKey();
                    DataSnapshot reviewsNode = node.child("Reviews");
                    for (DataSnapshot reviews : reviewsNode.getChildren()) {
                        doctorsList.add(reviews.getKey());
                        reviewsList.add(reviews.child("Review").getValue(String.class));
                    }
                }
            }
        }
    };

}