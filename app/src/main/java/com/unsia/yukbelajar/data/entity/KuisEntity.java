package com.unsia.yukbelajar.data.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kuis")
public class KuisEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public long date;
    public int score;

    public KuisEntity(long date, int score) {
        this.date = date;
        this.score = score;
    }
}

