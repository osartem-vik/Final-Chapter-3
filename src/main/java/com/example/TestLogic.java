package com.sewingtest.util;

import com.sewingtest.model.TestState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestLogic {
    private static final String[][] QUESTIONS = {
            {"Питання 1: (Зображення буде додано) Який інструмент зображено?", "Варіант 1", "Варіант 2", "Варіант 3"},
            {"Питання 2: (Зображення буде додано) Яка тканина зображена?", "Варіант 1", "Варіант 2"},
            {"Питання 3: (Зображення буде додано) Який тип шва зображено?", "Варіант 1", "Варіант 2", "Варіант 3", "Варіант 4"},
            {"Питання 4: (Зображення буде додано) Яка деталь одягу зображена?", "Варіант 1", "Варіант 2", "Варіант 3"},
            {"Питання 5: (Зображення буде додано) Який дефект шиття зображено?", "Варіант 1", "Варіант 2", "Варіант 3", "Варіант 4"},
            {"Питання 6: (Зображення буде додано) Який інструмент зображено?", "Варіант 1", "Варіант 2", "Варіант 3"},
            {"Питання 7: (Зображення буде додано) Яка тканина зображена?", "Варіант 1", "Варіант 2"},
            {"Питання 8: (Зображення буде додано) Який тип шва зображено?", "Варіант 1", "Варіант 2", "Варіант 3", "Варіант 4"},
            {"Питання 9: (Зображення буде додано) Яка деталь одягу зображена?", "Варіант 1", "Варіант 2", "Варіант 3"},
            {"Питання 10: (Зображення буде додано) Який дефект шиття зображено?", "Варіант 1", "Варіант 2", "Варіант 3", "Варіант 4"}
    };

    private static final String[] IMAGES = {
            "images/question1.png", "images/question2.png", "images/question3.png", "images/question4.png",
            "images/question5.png", "images/question6.png", "images/question7.png", "images/question8.png",
            "images/question9.png", "images/question10.png"
    };

    private static final int[] CORRECT_ANSWERS = {0, 1, 2, 0, 3, 0, 1, 2, 0, 3}; // Індекси правильних відповідей

    public static void selectRandomQuestions(TestState state) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < QUESTIONS.length; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);
        state.setSelectedQuestions(indices.subList(0, 5)); // Вибираємо 5 питань
    }

    public static String getQuestion(TestState state) {
        int questionIndex = state.getSelectedQuestions().get(state.getCurrentQuestionIndex());
        return QUESTIONS[questionIndex][0];
    }

    public static String[] getOptions(TestState state) {
        int questionIndex = state.getSelectedQuestions().get(state.getCurrentQuestionIndex());
        String[] options = new String[QUESTIONS[questionIndex].length - 1];
        System.arraycopy(QUESTIONS[questionIndex], 1, options, 0, options.length);
        return options;
    }

    public static String getImage(TestState state) {
        int questionIndex = state.getSelectedQuestions().get(state.getCurrentQuestionIndex());
        return IMAGES[questionIndex];
    }

    public static void processAnswer(TestState state, String answer) {
        int questionIndex = state.getSelectedQuestions().get(state.getCurrentQuestionIndex());
        String[] options = getOptions(state);
        boolean isCorrect = false;
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(answer) && i == CORRECT_ANSWERS[questionIndex]) {
                state.setScore(state.getScore() + 1);
                isCorrect = true;
                break;
            }
        }
        if (!isCorrect) {
            state.setWrongAnswers(state.getWrongAnswers() + 1);
        }
        state.setLastAnswerCorrect(isCorrect);
        state.setCurrentQuestionIndex(state.getCurrentQuestionIndex() + 1);
    }

    public static String getResult(TestState state) {
        if (state.getWrongAnswers() >= 2) {
            return "Тест провален! Ви допустили 2 неправильні відповіді.";
        }
        int score = state.getScore();
        if (score >= 4) {
            return "Відмінно! Ви набрали " + score + " з 5 балів!";
        } else if (score >= 3) {
            return "Добре! Ви набрали " + score + " з 5 балів.";
        } else {
            return "Потрібно підучити! Ви набрали " + score + " з 5 балів.";
        }
    }
}