package com.unsia.yukbelajar.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class QuizResponse {
    public String status;
    public String message;
    @SerializedName("data")
    public List<QuizData> data;

    public static class QuizData {
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
}
