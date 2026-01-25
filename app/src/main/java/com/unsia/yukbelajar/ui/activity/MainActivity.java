package com.unsia.yukbelajar.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.lifecycle.ViewModelProvider;

import com.unsia.yukbelajar.R;
import com.unsia.yukbelajar.util.NotificationUtil;
import com.unsia.yukbelajar.viewmodel.MainViewModel;
import com.unsia.yukbelajar.viewmodel.QuizViewModel;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    TextView tvQuizScore;
    ImageView ivQuizIcon;
    CardView cardBuah, cardHewan, cardBentuk, cardQuiz;

    @SuppressLint("MissingInflatedId")
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
        viewModel = new ViewModelProvider(this)
                .get(MainViewModel.class);

        checkPermission();
        initViews();
        viewListener();
        observeQuizState();

    }

    private void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        100
                );
            }
        }
    }

    private void initViews(){
        tvQuizScore = findViewById(R.id.tvQuizScore);
        ivQuizIcon = findViewById(R.id.imgQuizIcon);

        cardQuiz = findViewById(R.id.cardQuiz);
        cardBuah = findViewById(R.id.cardBuah);
        cardHewan = findViewById(R.id.cardHewan);
        cardBentuk = findViewById(R.id.cardBentuk);
    }

    private void viewListener(){
        cardQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });

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

    private void observeQuizState() {
        viewModel.getLatestQuiz().observe(this, quiz -> {

            if (quiz == null) {
                // 🆕 Never played
                cardQuiz.setBackgroundResource(
                        R.drawable.bg_card_kuis_default
                );
                tvQuizScore.setVisibility(View.GONE);
                ivQuizIcon.setImageResource(R.drawable.question_mark);
            } else {
                // ✅ Finished
                cardQuiz.setBackgroundResource(
                        R.drawable.bg_card_kuis_finished
                );
                tvQuizScore.setVisibility(View.VISIBLE);
                tvQuizScore.setText("Skor: " + quiz.score);
                ivQuizIcon.setImageResource(R.drawable.check_mark);
            }
        });
    }

}