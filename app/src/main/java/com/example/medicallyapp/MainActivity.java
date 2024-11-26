package com.example.medicallyapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText name,email,dob,pass;
    RadioGroup gender;
    RadioButton gselect;
    Button register;
    DatePickerDialog date;
    int mYear,mMonth,mDay, buttonId;
    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);
        dob = findViewById(R.id.dob);
        pass = findViewById(R.id.password);
        register = findViewById(R.id.register);

    }

    //OnClick method to check if user input is valid
    public void onRegister(View view){
        buttonId = gender.getCheckedRadioButtonId();
        if(name.getText().toString().isEmpty())
            Toast.makeText(this,"Please Enter a Name",Toast.LENGTH_SHORT).show();
        else if(email.getText().toString().isEmpty())
            Toast.makeText(this,"Please Enter an Email",Toast.LENGTH_SHORT).show();
        else if(!emailCheck(String.valueOf(email.getText())))
            Toast.makeText(this, "Enter a valid email.Ex: username@domain.com", Toast.LENGTH_SHORT).show();
        else if(buttonId==-1)
            Toast.makeText(this,"Please Select a Gender",Toast.LENGTH_SHORT).show();
        else if(dob.getText().toString().isEmpty())
            Toast.makeText(this,"Please Enter a Birthdate",Toast.LENGTH_SHORT).show();
        else if(pass.getText().toString().isEmpty())
            Toast.makeText(this,"Please Enter a Password",Toast.LENGTH_SHORT).show();
        else if(!passCheck(String.valueOf(pass.getText())))
            Toast.makeText(this, "Password should contain 1 numeric digit, 1 uppercase letter, and 1 special character", Toast.LENGTH_SHORT).show();
        else
            register();
    }

    public void dateSet(View v) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                String text = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                dob.setText(text);
            }
        },mYear,mMonth,mDay);
        date.show();
    }

    //Checks if email is valid
    public boolean emailCheck(String s){
        String r = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(s);
        return m.matches();
    }
    //Checks if password is valid
    public boolean passCheck(String s){
        String r = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    //Switches View to register account
    public void register(){
        gselect = findViewById(buttonId);
        Intent intent = new Intent(this, SecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", name.getText().toString());
        bundle.putString("email", email.getText().toString());
        bundle.putString("gender", gselect.getText().toString());
        bundle.putString("dob", dob.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}