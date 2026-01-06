package com.unsia.yukbelajar.data.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unsia.yukbelajar.data.entity.KuisEntity;

import java.util.List;

@Dao
public interface KuisDao {

    @Insert
    long insertKuis(KuisEntity kuis);

    @Query("SELECT * FROM kuis ORDER BY date DESC")
    List<KuisEntity> getAllKuis();
}

