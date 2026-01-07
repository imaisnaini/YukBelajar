package com.unsia.yukbelajar.data.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kategori")
public class KategoriEntity {

    @PrimaryKey(autoGenerate = true)
    public int kategoriId;

    public String namaKategori;

    public KategoriEntity(String namaKategori) {
        this.namaKategori = namaKategori;
    }
}

