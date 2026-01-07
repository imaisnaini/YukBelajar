package com.unsia.yukbelajar.data.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unsia.yukbelajar.data.entity.KategoriEntity;

import java.util.List;

@Dao
public interface KategoriDao {

    @Insert
    void insert(KategoriEntity kategori);

    @Query("SELECT * FROM kategori")
    List<KategoriEntity> getAllKategori();
}

