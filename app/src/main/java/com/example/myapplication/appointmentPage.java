package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class appointmentPage extends AppCompatActivity {

    String phone;
    TextView textViewPhone, textViewLocation, textViewDate, textViewDoctor, textViewSymptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_page);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null || !bundle.containsKey("phone")) {
            finish();
            return;
        }
        phone = bundle.getString("phone");

        textViewPhone = findViewById(R.id.textView30);
        textViewLocation = findViewById(R.id.textView31);
        textViewDate = findViewById(R.id.textView32);
        textViewDoctor = findViewById(R.id.textView33);
        textViewSymptoms = findViewById(R.id.textView34);

        fetchAppointmentData(phone);
    }

    private void fetchAppointmentData(String phone) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments");

        databaseReference.orderByChild("userPhone").equalTo(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                    String location = appointmentSnapshot.child("appointment_City").getValue(String.class);
                    String date = appointmentSnapshot.child("appointment_Date").getValue(String.class);
                    String doctor = appointmentSnapshot.child("doctor_Type").getValue(String.class);
                    String symptoms = appointmentSnapshot.child("symptom_description").getValue(String.class);

                    textViewPhone.setText(phone);
                    textViewLocation.setText(location);
                    textViewDate.setText(date);
                    textViewDoctor.setText(doctor);
                    textViewSymptoms.setText(symptoms);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", "Failed to fetch data: " + databaseError.getMessage());
            }
        });
    }
}