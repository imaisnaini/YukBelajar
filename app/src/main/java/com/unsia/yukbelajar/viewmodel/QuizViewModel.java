package com.unsia.yukbelajar.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unsia.yukbelajar.data.dummy.DummyQuizData;
import com.unsia.yukbelajar.data.entity.QuizQuestionEntity;
import com.unsia.yukbelajar.repository.QuizRepository;

import java.util.ArrayList;
import java.util.List;

public class QuizViewModel extends AndroidViewModel {

    private QuizRepository repository;

    private MutableLiveData<Integer> quizId = new MutableLiveData<>();
    // 🔥 NEVER NULL
    private MutableLiveData<List<QuizQuestionEntity>> questions =
            new MutableLiveData<>(new ArrayList<>());

    private int currentIndex = 0;
    private int score = 0;

    public QuizViewModel(@NonNull Application app) {
        super(app);
        repository = new QuizRepository(app);
    }

    // -----------------------------
    // START QUIZ
    // -----------------------------
    public void startQuiz(int totalQuestion) {
        repository.createQuiz(totalQuestion)
                .observeForever(id -> {
                    quizId.setValue(id);
                    injectDummyQuestions(id);
                    repository.getQuestions(id)
                            .observeForever(list -> {
                                questions.postValue(list);
                            });
                });
    }

    // -----------------------------
    // INJECT QUESTIONS (DUMMY)
    // -----------------------------
    private void injectDummyQuestions(int quizId) {
        List<QuizQuestionEntity> dummy =
                DummyQuizData.createQuestions(quizId);

        repository.insertQuestions(dummy);
    }

    // -----------------------------
    // LOAD QUESTIONS
    // -----------------------------
//    private void loadQuestions(int quizId) {
//        questions = repository.getQuestions(quizId)
//                .observeForever(list -> {
//                    questions.postValue(list);
//                });
//    }

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

        if (isCorrect) score += 10;
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
    }
}

