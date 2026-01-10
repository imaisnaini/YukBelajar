package com.unsia.yukbelajar.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unsia.yukbelajar.data.dao.QuizDao;
import com.unsia.yukbelajar.data.database.AppDatabase;
import com.unsia.yukbelajar.data.dummy.DummyQuizData;
import com.unsia.yukbelajar.data.entity.QuizEntity;
import com.unsia.yukbelajar.data.entity.QuizQuestionEntity;
import com.unsia.yukbelajar.data.entity.QuizResultEntity;

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

    // -----------------------------
    // CREATE QUIZ
    // -----------------------------
    public MutableLiveData<Integer> createQuiz(int totalQuestion) {
        MutableLiveData<Integer> quizIdLiveData = new MutableLiveData<>();

        executor.execute(() -> {
            QuizEntity quiz = new QuizEntity();
            quiz.date = getCurrentDate();
            quiz.totalQuestion = totalQuestion;
            quiz.score = 0;
            quiz.finished = false;

            int quizId = (int) quizDao.insertQuiz(quiz);
            quizIdLiveData.postValue(quizId);
        });

        return quizIdLiveData;
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
        MutableLiveData<QuizEntity> data = new MutableLiveData<>();

        executor.execute(() -> {
            QuizEntity quiz = quizDao.getLatestFinishedQuiz();
            data.postValue(quiz);
        });

        return data;
    }

}
