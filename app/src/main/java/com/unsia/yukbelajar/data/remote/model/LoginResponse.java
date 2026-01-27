package com.unsia.yukbelajar.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    public String status;
    public String message;
    @SerializedName("user")
    public UserData data;

    public static class UserData {
        @SerializedName("iser_id")
        public String userId;
        @SerializedName("nama")
        public String nama;
        @SerializedName("jenis_kelamin")
        public String jenisKelamin;
        @SerializedName("foto_profile")
        public String fotoProfile;
    }
}

