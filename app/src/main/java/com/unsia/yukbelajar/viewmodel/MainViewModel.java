package com.unsia.yukbelajar.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.unsia.yukbelajar.data.entity.QuizEntity;
import com.unsia.yukbelajar.repository.QuizRepository;

public class MainViewModel extends AndroidViewModel {

    private final QuizRepository repository;
    private final LiveData<QuizEntity> latestQuiz;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new QuizRepository(application);
        latestQuiz = repository.getLatestFinishedQuiz();
    }

    public LiveData<QuizEntity> getLatestQuiz() {
        return latestQuiz;
    }
}
