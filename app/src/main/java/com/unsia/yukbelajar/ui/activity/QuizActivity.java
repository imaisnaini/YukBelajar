package com.unsia.yukbelajar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.unsia.yukbelajar.R;
import com.unsia.yukbelajar.data.local.entity.QuizQuestionEntity;
import com.unsia.yukbelajar.util.NotificationUtil;
import com.unsia.yukbelajar.viewmodel.QuizViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private QuizViewModel viewModel;

    private ImageView imgQuestion;
    private Button btn1, btn2, btn3;

    private List<QuizQuestionEntity> questions;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setupToolbar();
        bindViews();

        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        viewModel.loadTodayQuiz();

        observeQuestions();

        viewModel.getQuizFinishedEvent().observe(this, score -> {
            if (score != null) {
                NotificationUtil.showQuizFinished(
                        this,
                        score
                );
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // enable back button
    }

    private void bindViews() {
        imgQuestion = findViewById(R.id.imgQuestion);
        btn1 = findViewById(R.id.btnAnswer1);
        btn2 = findViewById(R.id.btnAnswer2);
        btn3 = findViewById(R.id.btnAnswer3);
    }

    private void observeQuestions() {
        viewModel.getQuestions().observe(this, list -> {
            if (list == null || list.isEmpty()) return;
            questions = list;
            showQuestion();
        });
    }

    private void showQuestion() {
        if (index >= questions.size()) {
            viewModel.finishQuiz();
            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        QuizQuestionEntity q = questions.get(index);

        // Load image (simple version)
        int imgRes = getResources()
                .getIdentifier(q.questionImage,
                        "drawable",
                        getPackageName());
        imgQuestion.setImageResource(imgRes);

        // Shuffle answers
        List<String> answers = new ArrayList<>();
        answers.add(q.correctAnswer);
        answers.add(q.wrongAnswer1);
        answers.add(q.wrongAnswer2);
        Collections.shuffle(answers);

        btn1.setText(answers.get(0));
        btn2.setText(answers.get(1));
        btn3.setText(answers.get(2));

        btn1.setOnClickListener(v -> submitAnswer(q, answers.get(0)));
        btn2.setOnClickListener(v -> submitAnswer(q, answers.get(1)));
        btn3.setOnClickListener(v -> submitAnswer(q, answers.get(2)));
    }

    private void submitAnswer(QuizQuestionEntity q, String selected) {
        viewModel.answerQuestion(q, selected);
        index++;
        showQuestion();
    }
}
