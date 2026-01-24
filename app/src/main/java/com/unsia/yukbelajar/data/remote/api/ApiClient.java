package com.unsia.yukbelajar.data.remote.api;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static ApiService service;

    public static ApiService getService() {
        if (service == null) {// 🔐 Basic Auth credentials (API credentials, NOT DB credentials)
            String username = "dbBelajar_yuk_cuproundif";
            String password = "9f969fb3e5b257f0468c18cb3b792a0df4e0f425";

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(chain -> {
                        String credential = Credentials.basic(username, password);
                        return chain.proceed(
                                chain.request().newBuilder()
                                        .header("Authorization", credential)
                                        .build()
                        );
                    })
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("http://192.168.1.38/backend/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(ApiService.class);
        }
        return service;
    }
}


