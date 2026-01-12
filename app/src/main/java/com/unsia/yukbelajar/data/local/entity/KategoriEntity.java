package com.unsia.yukbelajar.data.local.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kategori")
public class KategoriEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nama;

    public KategoriEntity(){}
    public KategoriEntity(String nama) {
        this.nama = nama;
    }
}

