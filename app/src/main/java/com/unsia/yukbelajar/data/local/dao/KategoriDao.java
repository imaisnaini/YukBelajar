package com.unsia.yukbelajar.data.local.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unsia.yukbelajar.data.local.entity.ItemEntity;
import com.unsia.yukbelajar.data.local.entity.KategoriEntity;

import java.util.List;

@Dao
public interface KategoriDao {

    @Insert
    void insert(KategoriEntity kategori);@Insert
    void insertAll(List<KategoriEntity> kategories);

    @Query("SELECT * FROM kategori")
    List<KategoriEntity> getAllKategori();
}

