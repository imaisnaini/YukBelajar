package com.unsia.yukbelajar.data.repository;

import android.content.Context;

import com.unsia.yukbelajar.data.local.dao.ItemDao;
import com.unsia.yukbelajar.data.local.dao.KategoriDao;
import com.unsia.yukbelajar.data.local.dao.QuizDao;
import com.unsia.yukbelajar.data.local.database.AppDatabase;
import com.unsia.yukbelajar.data.local.entity.ItemEntity;
import com.unsia.yukbelajar.data.local.entity.KategoriEntity;
import com.unsia.yukbelajar.data.local.entity.QuizEntity;
import com.unsia.yukbelajar.data.remote.api.ApiClient;
import com.unsia.yukbelajar.data.remote.api.ApiService;
import com.unsia.yukbelajar.data.remote.model.ItemResponse;
import com.unsia.yukbelajar.data.remote.model.KategoriResponse;
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
            kategoriDao.insertAll(entities);
        }
    }

    private void syncItem() throws IOException {
        Response<List<ItemResponse>> response =
                api.getItems().execute();

        if (response.isSuccessful() && response.body() != null) {
            List<ItemEntity> entities = new ArrayList<>();
            for (ItemResponse r : response.body()) {
                ItemEntity e = new ItemEntity();
                e.id = r.id;
                e.nama = r.nama;
                e.kategoriId = r.kategori_id;
                e.gambar = r.gambar;
                entities.add(e);
            }
            itemDao.insertAll(entities);
        }
    }

    private void syncQuiz() throws IOException {
        Response<List<QuizResponse>> response =
                api.getQuiz().execute();

        if (response.isSuccessful() && response.body() != null) {
            List<QuizEntity> entities = new ArrayList<>();
            for (QuizResponse r : response.body()) {
                QuizEntity e = new QuizEntity();
                e.quizId = r.quizId;
                e.date = r.date;
                e.score = r.score;
                e.finished = r.finished;
                entities.add(e);
            }
            quizDao.insertAll(entities);
        }
    }
}

