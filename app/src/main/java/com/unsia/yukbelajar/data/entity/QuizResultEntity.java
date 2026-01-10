package com.unsia.yukbelajar.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "quiz_result",
        foreignKeys = {
                @ForeignKey(
                        entity = QuizEntity.class,
                        parentColumns = "quizId",
                        childColumns = "quizId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("quizId")}
)
public class QuizResultEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int quizId;
    public int finalScore;
    public String completedAt;
}

