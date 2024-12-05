package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class appointment_booking_page1 extends AppCompatActivity {
    EditText desc;
    RadioGroup doctortype;
    Intent intent;
    Bundle bundle;
    String phone_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appointment_booking_page1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent = getIntent();
        bundle = intent.getExtras();

        phone_num = bundle.getString("phone");

        desc = findViewById(R.id.Symptom_desc);
        doctortype = findViewById(R.id.doctype);
    }

    public void gotopage2(View view){

        int duration = Toast.LENGTH_SHORT;

        int docnum = doctortype.getCheckedRadioButtonId();
        RadioButton dochosen = (RadioButton) findViewById(docnum);
        String doctype = dochosen.getText().toString();
        String symptom_description = desc.getText().toString();


        if (symptom_description.isEmpty()){
            Toast.makeText(this, "Please enter your symptoms", duration).show();
            return;
        }

        if (docnum == -1) {
            Toast.makeText(this, "Please select a type of doctor", duration).show();
            return;
        }

        Intent intent = new Intent(this, activity_appointment_booking_page2.class);
        Bundle bundle = new Bundle();
        bundle.putString("phone", phone_num);
        bundle.putString("doctype",doctype);
        bundle.putString("symptom_description",symptom_description);
        bundle.putString("phone",phone_num);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void back(View view){
        finish();
    }
}