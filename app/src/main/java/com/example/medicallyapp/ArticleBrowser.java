package com.example.medicallyapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ArticleBrowser extends AppCompatActivity {

    ArrayAdapter<CharSequence> adapter;
    SearchView sv;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_article_browser);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sv = findViewById(R.id.search);
        lv  = findViewById(R.id.list);

        //ArrayAdapter for the ListView using data from strings.xml
        adapter = ArrayAdapter.createFromResource(this, R.array.articles, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);

        //Filters items in the ArrayAdapter based on contents in the SearchView
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        //Displays toast when an item in the list is clicked
        lv.setOnItemClickListener((p, v, position, id) -> Toast.makeText(ArticleBrowser.this, "You clicked on " + adapter.getItem(position), Toast.LENGTH_SHORT).show());
    }
}