package com.unsia.yukbelajar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.unsia.yukbelajar.R;
import com.unsia.yukbelajar.data.remote.api.ApiClient;
import com.unsia.yukbelajar.data.remote.api.ApiService;
import com.unsia.yukbelajar.data.remote.model.ItemResponse;
import com.unsia.yukbelajar.data.remote.model.KategoriResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    CardView cardBuah, cardHewan, cardBentuk;

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
        WorkManager.getInstance(this)
                .getWorkInfosForUniqueWorkLiveData("sync_data_work")
                .observe(this, workInfos -> {
                    for (WorkInfo info : workInfos) {
                        Log.d("WM_STATUS", "State: " + info.getState());
                    }
                });

        cardBuah = findViewById(R.id.cardBuah);
        cardHewan = findViewById(R.id.cardHewan);
        cardBentuk = findViewById(R.id.cardBentuk);

        cardBuah.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("type", "buah");
            startActivity(intent);
        });

        cardHewan.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("type", "hewan");
            startActivity(intent);
        });

        cardBentuk.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("type", "bentuk");
            startActivity(intent);
        });
    }
}