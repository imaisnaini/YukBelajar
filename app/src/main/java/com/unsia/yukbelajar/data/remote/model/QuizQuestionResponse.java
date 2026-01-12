package com.unsia.yukbelajar.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class QuizQuestionResponse {
    @SerializedName("id")
    public int id;
    @SerializedName("quizId")
    public int quizId;
    @SerializedName("questionImage")
    public String questionImage;
    @SerializedName("correctAnswer")
    public String correctAnswer;
    @SerializedName("wrongAnswer1")
    public String wrongAnswer1;
    @SerializedName("wrongAnswer2")
    public String wrongAnswer2;
    @SerializedName("answered")
    public boolean answered;
    @SerializedName("correct")
    public boolean correct;
}
