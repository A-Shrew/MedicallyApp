package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Articles extends AppCompatActivity {

    ArrayAdapter<CharSequence> adapter;
    ListView lv;
    List<String> articleTitles = new ArrayList<>();
    List<String> articleUrls = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_articles);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lv  = findViewById(R.id.list);
        adapter = ArrayAdapter.createFromResource(this, R.array.atitles, android.R.layout.simple_list_item_1);;
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);



        articleUrls = Arrays.asList(getResources().getStringArray(R.array.alinks));

        //Displays toast when an item in the list is clicked
        lv.setOnItemClickListener((p, v, position, id) -> {
            String url = articleUrls.get(position);
            if (url != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } else {
                Toast.makeText(Articles.this, "URL not available", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
