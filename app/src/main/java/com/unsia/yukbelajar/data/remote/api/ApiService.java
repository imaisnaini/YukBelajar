package com.unsia.yukbelajar.data.remote.api;

import com.unsia.yukbelajar.data.remote.model.ItemResponse;
import com.unsia.yukbelajar.data.remote.model.KategoriResponse;
import com.unsia.yukbelajar.data.remote.model.QuizQuestionResponse;
import com.unsia.yukbelajar.data.remote.model.QuizResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("kategori")
    Call<List<KategoriResponse>> getKategori();
    @GET("items")
    Call<List<ItemResponse>> getItems();

    @GET("quiz")
    Call<List<QuizResponse>> getQuiz();

    @GET("quiz-question")
    Call<List<QuizQuestionResponse>> getQuizQuestions();
}

