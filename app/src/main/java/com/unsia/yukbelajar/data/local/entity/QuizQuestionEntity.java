package com.unsia.yukbelajar.data.local.entity;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "quiz_question",
        foreignKeys = @ForeignKey(
                entity = QuizEntity.class,
                parentColumns = "quizId",
                childColumns = "quizId",
                onDelete = ForeignKey.CASCADE
        )
)
public class QuizQuestionEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int quizId;
    public String questionImage;

    public String correctAnswer;
    public String wrongAnswer1;
    public String wrongAnswer2;

    public boolean answered;
    public boolean correct;
}

