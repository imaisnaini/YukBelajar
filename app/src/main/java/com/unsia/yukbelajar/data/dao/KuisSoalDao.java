package com.unsia.yukbelajar.data.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unsia.yukbelajar.data.entity.KuisSoalEntity;

import java.util.List;

@Dao
public interface KuisSoalDao {

    @Insert
    void insertSoal(KuisSoalEntity soal);

    @Query("SELECT * FROM kuis_soal WHERE kuisId = :kuisId")
    List<KuisSoalEntity> getSoalByKuis(int kuisId);
}
