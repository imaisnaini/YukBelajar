package com.unsia.yukbelajar.data.dummy;

import com.unsia.yukbelajar.data.local.entity.QuizQuestionEntity;

import java.util.ArrayList;
import java.util.List;

public class DummyQuizData {

    public static List<QuizQuestionEntity> createQuestions(int quizId) {

        List<QuizQuestionEntity> questions = new ArrayList<>();

        questions.add(create(
                quizId,
                "img_apple",
                "Apple",
                "Banana",
                "Orange"
        ));

        questions.add(create(
                quizId,
                "img_cat",
                "Cat",
                "Dog",
                "Horse"
        ));

        return questions;
    }

    private static QuizQuestionEntity create(
            int quizId,
            String image,
            String correct,
            String wrong1,
            String wrong2
    ) {
        QuizQuestionEntity q = new QuizQuestionEntity();
        q.quizId = quizId;
        q.questionImage = image;
        q.correctAnswer = correct;
        q.wrongAnswer1 = wrong1;
        q.wrongAnswer2 = wrong2;
        q.answered = false;   // VERY IMPORTANT
        q.correct = false;   // VERY IMPORTANT
        return q;
    }
}
