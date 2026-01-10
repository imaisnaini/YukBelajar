package com.unsia.yukbelajar.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quiz")
public class QuizEntity {

    @PrimaryKey(autoGenerate = true)
    public int quizId;

    @NonNull
    public String date; // yyyy-MM-dd HH:mm

    public int totalQuestion;
    public int score;
    public boolean finished;
}


