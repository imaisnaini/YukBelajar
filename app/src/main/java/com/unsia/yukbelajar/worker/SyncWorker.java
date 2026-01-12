package com.unsia.yukbelajar.worker;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.unsia.yukbelajar.data.repository.SyncRepository;

public class SyncWorker extends Worker {

    public SyncWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params
    ) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        SyncRepository repository =
                new SyncRepository(getApplicationContext());

        boolean success = repository.syncAll();

        return success ? Result.success() : Result.retry();
    }
}

