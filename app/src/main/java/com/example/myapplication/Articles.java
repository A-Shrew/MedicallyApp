package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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
    SearchView sv;
    ListView lv;
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

        sv = findViewById(R.id.search);
        lv  = findViewById(R.id.list);
        adapter = ArrayAdapter.createFromResource(this, R.array.atitles, android.R.layout.simple_list_item_1);;
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);
        articleUrls = Arrays.asList(getResources().getStringArray(R.array.alinks));

        // Move articles similar to search query to the top of the list view
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

        // Displays toast when an item in the list is clicked
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
    public void Back(View view){
        finish();
    }
}
