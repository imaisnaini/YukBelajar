package com.unsia.yukbelajar.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unsia.yukbelajar.data.dummy.DummyQuizData;
import com.unsia.yukbelajar.data.local.entity.QuizEntity;
import com.unsia.yukbelajar.data.local.entity.QuizQuestionEntity;
import com.unsia.yukbelajar.data.repository.QuizRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuizViewModel extends AndroidViewModel {

    private QuizRepository repository;

    private MutableLiveData<Integer> quizId = new MutableLiveData<>();
    // 🔥 NEVER NULL
    private MutableLiveData<List<QuizQuestionEntity>> questions =
            new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Integer> quizFinishedEvent = new MutableLiveData<>();

    private int currentIndex = 0;
    private int score = 0;

    public QuizViewModel(@NonNull Application app) {
        super(app);
        repository = new QuizRepository(app);
    }
    public void loadTodayQuiz() {
        repository.getTodayQuiz()
                .observeForever(quiz -> {
                    if (quiz == null) {
                        // no quiz today
                        quizFinishedEvent.postValue(-1);
                        return;
                    }

                    quizId.setValue(quiz.id);

                    repository.getQuestions(quiz.id)
                            .observeForever(list -> {
                                questions.postValue(list);
                            });
                });
    }



    public LiveData<List<QuizQuestionEntity>> getQuestions() {
        return questions;
    }

    // -----------------------------
    // ANSWER
    // -----------------------------
    public void answerQuestion(QuizQuestionEntity question, String selected) {
        boolean isCorrect =
                selected.equals(question.correctAnswer);

        repository.answerQuestion(question.id, isCorrect);

        if (isCorrect) score += 20;
        currentIndex++;
    }

    // -----------------------------
    // FINISH QUIZ
    // -----------------------------
    public void finishQuiz() {
        repository.finishQuiz(
                quizId.getValue(),
                score
        );
        quizFinishedEvent.setValue(score);
    }

    public LiveData<Integer>getQuizFinishedEvent() {
        return quizFinishedEvent;
    }
}

