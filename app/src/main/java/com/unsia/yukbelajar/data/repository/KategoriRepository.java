package com.unsia.yukbelajar.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.unsia.yukbelajar.data.local.dao.KategoriDao;
import com.unsia.yukbelajar.data.local.database.AppDatabase;
import com.unsia.yukbelajar.data.local.entity.KategoriEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class KategoriRepository {

    private final KategoriDao kategoriDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public KategoriRepository(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        kategoriDao = db.kategoriDao();
    }

    public List<KategoriEntity> getAllKategori() {
        return kategoriDao.getAllKategori();
    }

    public void insertAll(List<KategoriEntity> list) {
        executor.execute(() -> kategoriDao.insertAll(list));
    }
}

