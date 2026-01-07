package com.unsia.yukbelajar.data.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unsia.yukbelajar.data.entity.UserEntity;

@Dao
public interface UserDao {

    @Insert
    void insert(UserEntity user);

    @Query("SELECT * FROM user WHERE nama = :nama")
    UserEntity getUserByNama(String nama);
}

