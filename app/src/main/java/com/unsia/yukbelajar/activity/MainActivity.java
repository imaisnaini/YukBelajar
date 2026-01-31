package com.unsia.yukbelajar.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.unsia.yukbelajar.R;

public class MainActivity extends AppCompatActivity {

    CardView cardBuah, cardHewan, cardBentuk, btnProfile, cardKuis;
    ImageView imgProfileMain;
    TextView txtNameMain;

    SharedPreferences prefs;

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

        // 🔥 HARUS SAMA DENGAN ProfileActivity
        prefs = getSharedPreferences("profile_pref", MODE_PRIVATE);

        // View
        cardBuah = findViewById(R.id.cardBuah);
        cardHewan = findViewById(R.id.cardHewan);
        cardBentuk = findViewById(R.id.cardBentuk);
        btnProfile = findViewById(R.id.btnProfile);
        cardKuis = findViewById(R.id.cardKuis);

        imgProfileMain = findViewById(R.id.imgProfileMain);
        txtNameMain = findViewById(R.id.txtNameMain);

        // load awal
        loadProfile();

        // Klik Profil
        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ProfileActivity.class))
        );

        cardKuis.setOnClickListener(v ->
                Toast.makeText(this, "Fitur kuis belum tersedia", Toast.LENGTH_SHORT).show()
        );

        cardBuah.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("type", "buah");
            startActivity(intent);
        });

        cardHewan.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("type", "hewan");
            startActivity(intent);
        });

        cardBentuk.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("type", "bentuk");
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfile(); // 🔥 SYNC REALTIME
    }

    private void loadProfile() {
        txtNameMain.setText(prefs.getString("name", "Yuk Belajar"));

        String photoUri = prefs.getString("photo", null);
        int padding = (int) (10 * getResources().getDisplayMetrics().density);

        if (photoUri != null) {
            imgProfileMain.setImageURI(null); // clear cache
            imgProfileMain.setImageURI(Uri.parse(photoUri));
            imgProfileMain.setPadding(0, 0, 0, 0);
        } else {
            imgProfileMain.setImageResource(R.drawable.ic_profile_placeholder);
            imgProfileMain.setPadding(padding, padding, padding, padding);
        }
    }
}
