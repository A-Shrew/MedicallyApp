package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;

public class activity_appointment_booking_page2 extends AppCompatActivity {
    Intent intent;
    Bundle bundle;
    String symptom_description, doctype;
    Spinner doctype2, city;
    EditText date;
    TextView docprompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appointment_booking_page2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent = getIntent();
        bundle = intent.getExtras();

        doctype = bundle.getString("doctype");
        symptom_description = bundle.getString("symptom_description");

        doctype2 = (Spinner) findViewById(R.id.doctype2);
        city = (Spinner) findViewById(R.id.city);
        date = findViewById(R.id.appointment_date);

        docprompt = findViewById(R.id.docprompt);
        String prompt_template = "Please select the type of ";


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.primary_care, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.specialist, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.womens, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.mental, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.other, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> cityarray = ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        cityarray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(cityarray);

        switch(doctype) {
            case "- Primary care providers":
                    doctype2.setAdapter(adapter1);
                    docprompt.setText(prompt_template+"primary care specialist:"); break;
            case "- Specialists"
                    : doctype2.setAdapter(adapter2);
                    docprompt.setText(prompt_template+" specialist specialist:"); break;
            case "- Women's and Reproductive Health":
                    doctype2.setAdapter(adapter3);
                    docprompt.setText(prompt_template+"women's/reproductive specialist:"); break;
            case "- Mental health":
                    doctype2.setAdapter(adapter4);
                    docprompt.setText(prompt_template+"mental health specialist:"); break;
            case "- Other Specialists":
                    doctype2.setAdapter(adapter5);
                    docprompt.setText(prompt_template+"other doctor"); break;
        }
    }

    public void datepick(View view){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String dat = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        date.setText(dat);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void book(View view){
        /* Need to save them to database
         */
        String doctor_Type = doctype2.getSelectedItem().toString();
        String appointment_City = city.getSelectedItem().toString();
        String appointment_Date = date.getText().toString();


        //save the info to next activity to display them
        Intent intent = new Intent(this, appointment_summary.class);
        Bundle bundle = new Bundle();

        bundle.putString("symptom_description",symptom_description);
        bundle.putString("doctor_Type",doctor_Type);
        bundle.putString("appointment_City",appointment_City);
        bundle.putString("appointment_Date",appointment_Date);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void back(View view){
        finish();
    }

}