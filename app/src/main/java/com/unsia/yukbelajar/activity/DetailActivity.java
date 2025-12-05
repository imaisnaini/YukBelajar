package com.unsia.yukbelajar.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unsia.yukbelajar.data.ItemAdapter;
import com.unsia.yukbelajar.data.ItemModel;
import com.unsia.yukbelajar.R;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ItemModel> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        // ---------- Setup Toolbar ----------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // enable back button

        recyclerView = findViewById(R.id.recyclerView);

        // Optional: Set title based on category
        String type = getIntent().getStringExtra("type");
        if(type != null) getSupportActionBar().setTitle(type.toUpperCase());

        loadData(type);  // ⬅ separate function to make clean reusable code

        ItemAdapter adapter = new ItemAdapter(this, items);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    // Back Button Action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // go back
        return true;
    }
    private void loadData(String type) {

        if (type.equals("buah")) {
            items.add(new ItemModel("Apple", R.drawable.apple, 0));
            items.add(new ItemModel("Banana", R.drawable.bananas, 0));
            items.add(new ItemModel("Orange", R.drawable.orange, 0));
            items.add(new ItemModel("Grape", R.drawable.grapes, 0));
            items.add(new ItemModel("Mango", R.drawable.mango, 0));
            items.add(new ItemModel("Watermelon", R.drawable.watermelon, 0));

        } else if (type.equals("hewan")) {
            items.add(new ItemModel("Kucing", R.drawable.cat, 0));
            items.add(new ItemModel("Anjing", R.drawable.dog, 0));
            items.add(new ItemModel("Sapi", R.drawable.cow, 0));
            items.add(new ItemModel("Ayam", R.drawable.chicken, 0));
            items.add(new ItemModel("Kuda", R.drawable.horse, 0));
            items.add(new ItemModel("Burung", R.drawable.bird, 0));

        } else if (type.equals("bentuk")) {
            items.add(new ItemModel("Lingkaran", R.drawable.circle, 0));
            items.add(new ItemModel("Persegi", R.drawable.square, 0));
            items.add(new ItemModel("Segitiga", R.drawable.triangle, 0));
            items.add(new ItemModel("Oval", R.drawable.oval, 0));
            items.add(new ItemModel("Persegi Panjang", R.drawable.rectangle, 0));
            items.add(new ItemModel("Bintang", R.drawable.star, 0));
        }
    }
}