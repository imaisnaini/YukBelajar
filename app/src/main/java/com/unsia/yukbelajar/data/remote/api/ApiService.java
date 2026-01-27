package com.unsia.yukbelajar.data.remote.api;

import com.unsia.yukbelajar.data.remote.model.ItemResponse;
import com.unsia.yukbelajar.data.remote.model.KategoriResponse;
import com.unsia.yukbelajar.data.remote.model.LoginResponse;
import com.unsia.yukbelajar.data.remote.model.QuizQuestionResponse;
import com.unsia.yukbelajar.data.remote.model.QuizResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("kategori/read.php")
    Call<List<KategoriResponse>> getKategori();
    @GET("item/read.php")
    Call<List<ItemResponse>> getItems();

    @GET("kuis/read.php")
    Call<List<QuizResponse>> getQuiz();

    @GET("kuis_soal/read.php")
    Call<List<QuizQuestionResponse>> getQuizQuestions();

    @FormUrlEncoded
    @POST("user/login.php")
    Call<LoginResponse> login(
            @Field("nama") String username,
            @Field("kata_sandi") String password
    );
}

