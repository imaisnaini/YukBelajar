package com.unsia.yukbelajar.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class QuizResponse {
    @SerializedName("quizId")
    public int quizId;
    @SerializedName("date")
    public String date;
    @SerializedName("totalQuestion")
    public int totalQuestion;
    @SerializedName("score")
    public int score;
    @SerializedName("finished")
    public boolean finished;
}
