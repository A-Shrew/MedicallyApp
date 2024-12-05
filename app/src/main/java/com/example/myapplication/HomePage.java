package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomePage extends AppCompatActivity {
    Intent intent;
    Bundle bundle;
    String phone_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent = getIntent();
        bundle = intent.getExtras();

        phone_num = bundle.getString("phone");
    }

    public void start_booking(View view){
        Intent intent = new Intent(this, appointment_booking_page1.class);
        Bundle phone_num_bundle = new Bundle();
        phone_num_bundle.putString("phone", phone_num);
        intent.putExtras(phone_num_bundle);
        startActivity(intent);
    }

    public void read_article(View view){
        Intent intent = new Intent(this, Articles.class);
        startActivity(intent);
    }
}