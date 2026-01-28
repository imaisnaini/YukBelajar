package com.unsia.yukbelajar.data.repository;

import android.content.Context;

import com.unsia.yukbelajar.data.local.dao.ItemDao;
import com.unsia.yukbelajar.data.local.dao.KategoriDao;
import com.unsia.yukbelajar.data.local.dao.QuizDao;
import com.unsia.yukbelajar.data.local.database.AppDatabase;
import com.unsia.yukbelajar.data.local.entity.ItemEntity;
import com.unsia.yukbelajar.data.local.entity.KategoriEntity;
import com.unsia.yukbelajar.data.local.entity.QuizEntity;
import com.unsia.yukbelajar.data.local.entity.QuizQuestionEntity;
import com.unsia.yukbelajar.data.remote.api.ApiClient;
import com.unsia.yukbelajar.data.remote.api.ApiService;
import com.unsia.yukbelajar.data.remote.model.ItemResponse;
import com.unsia.yukbelajar.data.remote.model.KategoriResponse;
import com.unsia.yukbelajar.data.remote.model.QuizQuestionResponse;
import com.unsia.yukbelajar.data.remote.model.QuizResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class SyncRepository {

    private final ApiService api;
    private final KategoriDao kategoriDao;
    private final ItemDao itemDao;
    private final QuizDao quizDao;

    public SyncRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        api = ApiClient.getService();

        kategoriDao = db.kategoriDao();
        itemDao = db.itemDao();
        quizDao = db.quizDao();
    }

    public boolean syncAll() {
        try {
            syncKategori();
            syncItem();
            syncQuiz();
            syncQuizQuestion();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void syncKategori() throws IOException {
        Response<List<KategoriResponse>> response =
                api.getKategori().execute();

        if (response.isSuccessful() && response.body() != null) {
            List<KategoriEntity> entities = new ArrayList<>();
            for (KategoriResponse r : response.body()) {
                KategoriEntity e = new KategoriEntity();
                e.id = r.id;
                e.nama = r.nama;
                entities.add(e);
            }
            kategoriDao.upsertAll(entities);
        }
    }

    private void syncItem() throws IOException {
        Response<ItemResponse> response =
                api.getItems().execute();

        if (response.isSuccessful() && response.body() != null) {
            List<ItemEntity> entities = new ArrayList<>();
            ItemResponse r = response.body();
            for (ItemResponse.ItemData d : r.data) {
                ItemEntity e = new ItemEntity();
                e.id = d.id;
                e.nama = d.nama;
                e.kategoriId = d.kategori_id;
                e.gambar = d.gambar;
                entities.add(e);
            }
            itemDao.upsertAll(entities);
        }
    }

    private void syncQuiz() throws IOException {
        Response<QuizResponse> response =
                api.getQuiz().execute();

        if (response.isSuccessful() && response.body() != null) {
            List<QuizEntity> entities = new ArrayList<>();
            QuizResponse r = response.body();
            for (QuizResponse.QuizData d : r.data) {
                QuizEntity e = new QuizEntity();
                e.id = d.quizId;
                e.date = d.date;
                e.totalQuestion = d.totalQuestion;
                e.score = d.score;
                e.finished = d.finished;
                entities.add(e);
            }
            quizDao.upsertAll(entities);
        }
    }

    private void syncQuizQuestion() throws IOException {
        Response<QuizQuestionResponse> response =
                api.getQuizQuestions().execute();

        if (response.isSuccessful() && response.body() != null) {
            List<QuizQuestionEntity> entities = new ArrayList<>();
            QuizQuestionResponse r = response.body();
            for (QuizQuestionResponse.QuestionData d : r.data) {
                QuizQuestionEntity e = new QuizQuestionEntity();
                e.id = d.id;
                e.quizId = d.quizId;
                e.questionImage = d.questionImage;
                e.correctAnswer = d.correctAnswer;
                e.wrongAnswer1 = d.wrongAnswer1;
                e.wrongAnswer2 = d.wrongAnswer2;
                e.correct = d.correct;
                e.answered = d.answered;
                entities.add(e);
            }
            quizDao.upsertQuestion(entities);
        }
    }
}

