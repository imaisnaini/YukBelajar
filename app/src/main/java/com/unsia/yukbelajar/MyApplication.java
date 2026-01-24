package com.unsia.yukbelajar;

import android.app.Application;
import android.util.Log;

import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.unsia.yukbelajar.data.local.database.AppDatabase;
import com.unsia.yukbelajar.worker.SyncWorker;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            db.kategoriDao().getAllKategori();
            Log.d("ROOM_DB", "Database initialized");
        });
        schedulePeriodicSync();
    }

    private void schedulePeriodicSync() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest syncWork =
                new PeriodicWorkRequest.Builder(
                        SyncWorker.class,
                        5,
                        TimeUnit.MINUTES
                )
                        .setConstraints(constraints)
                        .addTag("sync_data")
                        .build();

        WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork(
                        "sync_data_work",
                        ExistingPeriodicWorkPolicy.KEEP,
                        syncWork
                );
    }
}

