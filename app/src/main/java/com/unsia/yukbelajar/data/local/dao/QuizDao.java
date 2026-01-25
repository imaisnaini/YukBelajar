package com.unsia.yukbelajar.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unsia.yukbelajar.data.local.entity.ItemEntity;
import com.unsia.yukbelajar.data.local.entity.QuizEntity;
import com.unsia.yukbelajar.data.local.entity.QuizQuestionEntity;
import com.unsia.yukbelajar.data.local.entity.QuizResultEntity;

import java.util.List;

@Dao
public interface QuizDao {

    // --- QUIZ SESSION ---

    @Insert
    long insertQuiz(QuizEntity quiz);@Insert
    void insertAll(List<QuizEntity> quizEntities);

    @Query("SELECT * FROM quiz WHERE quizId = :quizId")
    QuizEntity getQuizById(int quizId);

    @Query("UPDATE quiz SET score = :score, finished = 1 WHERE quizId = :quizId")
    void finishQuiz(int quizId, int score);


    // --- QUESTIONS ---

    @Insert
    void insertQuestions(List<QuizQuestionEntity> questions);

    @Query("SELECT * FROM quiz_question WHERE quizId = :quizId")
    List<QuizQuestionEntity> getQuestionsByQuiz(int quizId);

    @Query("UPDATE quiz_question SET answered = 1, correct = :isCorrect WHERE id = :questionId")
    void answerQuestion(int questionId, boolean isCorrect);


    // --- RESULT ---

    @Insert
    void insertResult(QuizResultEntity result);

    @Query("SELECT * FROM quiz_result WHERE quizId = :quizId")
    QuizResultEntity getResultByQuiz(int quizId);

    @Query("SELECT * FROM quiz WHERE finished = 1 ORDER BY date DESC LIMIT 1")
    LiveData<QuizEntity> getLatestFinishedQuiz();

}

