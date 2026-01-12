package com.unsia.yukbelajar.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.unsia.yukbelajar.data.local.dao.ItemDao;
import com.unsia.yukbelajar.data.local.database.AppDatabase;
import com.unsia.yukbelajar.data.local.entity.ItemEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ItemRepository {

    private final ItemDao itemDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public ItemRepository(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        itemDao = db.itemDao();
    }

    public List<ItemEntity> getAllItems() {
        return itemDao.getAllItems();
    }

    public void insert(ItemEntity item) {
        executor.execute(() -> itemDao.insert(item));
    }

    public void insertAll(List<ItemEntity> items) {
        executor.execute(() -> itemDao.insertAll(items));
    }
}

