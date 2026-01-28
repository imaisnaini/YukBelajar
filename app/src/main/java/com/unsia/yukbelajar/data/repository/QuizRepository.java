package com.unsia.yukbelajar.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unsia.yukbelajar.data.local.dao.QuizDao;
import com.unsia.yukbelajar.data.local.database.AppDatabase;
import com.unsia.yukbelajar.data.local.entity.QuizEntity;
import com.unsia.yukbelajar.data.local.entity.QuizQuestionEntity;
import com.unsia.yukbelajar.data.local.entity.QuizResultEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuizRepository {

    private final QuizDao quizDao;
    private final ExecutorService executor;

    public QuizRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        quizDao = db.quizDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<QuizEntity> getTodayQuiz() {
        MutableLiveData<QuizEntity> data = new MutableLiveData<>();

        executor.execute(() -> {
            String today = new SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
            ).format(new Date());

            QuizEntity quiz = quizDao.getTodayQuiz(today);
            data.postValue(quiz);
        });

        return data;
    }

    public LiveData<QuizEntity> getFinishedTodayQuiz() {
        MutableLiveData<QuizEntity> data = new MutableLiveData<>();

        executor.execute(() -> {
            String today = new SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
            ).format(new Date());

            data.postValue(
                    quizDao.getFinishedTodayQuiz(today)
            );
        });

        return data;
    }

    // -----------------------------
    // INSERT QUESTIONS
    // -----------------------------
    public void insertQuestions(List<QuizQuestionEntity> questions) {
        executor.execute(() -> quizDao.insertQuestions(questions));
    }

    // -----------------------------
    // LOAD QUESTIONS
    // -----------------------------
    public LiveData<List<QuizQuestionEntity>> getQuestions(int quizId) {
        MutableLiveData<List<QuizQuestionEntity>> data = new MutableLiveData<>();

        executor.execute(() -> {
            List<QuizQuestionEntity> questions =
                    quizDao.getQuestionsByQuiz(quizId);
            data.postValue(questions);
        });

        return data;
    }

    // -----------------------------
    // ANSWER QUESTION
    // -----------------------------
    public void answerQuestion(int questionId, boolean isCorrect) {
        executor.execute(() ->
                quizDao.answerQuestion(questionId, isCorrect)
        );
    }

    // -----------------------------
    // FINISH QUIZ
    // -----------------------------
    public void finishQuiz(int quizId, int finalScore) {
        executor.execute(() -> {
            quizDao.finishQuiz(quizId, finalScore);

            QuizResultEntity result = new QuizResultEntity();
            result.quizId = quizId;
            result.finalScore = finalScore;
            result.completedAt = getCurrentDate();

            quizDao.insertResult(result);
        });
    }

    // -----------------------------
    // HELPERS
    // -----------------------------
    private String getCurrentDate() {
        return new SimpleDateFormat(
                "yyyy-MM-dd HH:mm",
                Locale.getDefault()
        ).format(new Date());
    }

    public LiveData<QuizEntity> getLatestFinishedQuiz() {
        return quizDao.getLatestFinishedQuiz();
    }


}
