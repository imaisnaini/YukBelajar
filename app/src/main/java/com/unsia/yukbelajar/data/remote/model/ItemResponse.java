package com.unsia.yukbelajar.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class ItemResponse {
    @SerializedName("id")
    public int id;
    @SerializedName("nama")
    public String nama;
    @SerializedName("gambar")
    public String gambar;
    @SerializedName("kategori")
    public int kategori_id;
}

