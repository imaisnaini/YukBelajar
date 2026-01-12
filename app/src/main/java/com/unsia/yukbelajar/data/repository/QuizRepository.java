package com.unsia.yukbelajar.data.repository;

import android.app.Application;

import com.unsia.yukbelajar.data.local.dao.QuizDao;
import com.unsia.yukbelajar.data.local.database.AppDatabase;
import com.unsia.yukbelajar.data.local.entity.QuizEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class QuizRepository {

    private final QuizDao quizDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public QuizRepository(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        quizDao = db.quizDao();
    }

    public QuizEntity getFinishedQuiz() {
        return quizDao.getLatestFinishedQuiz();
    }

    public void insertQuiz(QuizEntity quiz) {
        executor.execute(() -> quizDao.insertQuiz(quiz));
    }
}

