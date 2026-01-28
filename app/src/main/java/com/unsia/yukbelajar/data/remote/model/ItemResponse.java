package com.unsia.yukbelajar.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemResponse {
    public String status;
    public String message;
    @SerializedName("data")
    public List<ItemData> data;

    public static class ItemData {
        @SerializedName("id")
        public int id;
        @SerializedName("nama")
        public String nama;
        @SerializedName("gambar")
        public String gambar;
        @SerializedName("kategori_id")
        public int kategori_id;
    }
}

