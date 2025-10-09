package com.example.util;

import com.example.sewingtest.model.TestState;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TestLogic {
    private static final List<Question> QUESTIONS = loadQuestions();

    private static class Question {
        String questionText;
        List<String> options;
        String correctAnswer;
        String imagePath;

        Question(String questionText, List<String> options, String correctAnswer, String imagePath) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswer = correctAnswer;
            this.imagePath = imagePath;
        }
    }

    private static List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(TestLogic.class.getClassLoader().getResourceAsStream("questions.json"))))) {
            System.out.println("TestLogic: Loading questions.json at " + new java.util.Date());
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            System.out.println("TestLogic: JSON content length: " + jsonContent.length());
            if (jsonContent.length() == 0) {
                System.out.println("TestLogic: JSON content is empty! Using fallback questions.");
                return createFallbackQuestions();
            }
            JSONArray jsonArray = new JSONArray(jsonContent.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String questionText = jsonObject.getString("question");
                JSONArray optionsArray = jsonObject.getJSONArray("options");
                List<String> options = new ArrayList<>();
                for (int j = 0; j < optionsArray.length(); j++) {
                    options.add(optionsArray.getString(j));
                }
                String correctAnswer = jsonObject.getString("correctAnswer");
                String imagePath = jsonObject.getString("image");
                System.out.println("TestLogic: Loaded question " + (i + 1) + ": " + questionText + ", image: " + imagePath);
                questions.add(new Question(questionText, options, correctAnswer, imagePath));
            }
            System.out.println("TestLogic: Loaded " + questions.size() + " questions.");
        } catch (Exception e) {
            System.err.println("TestLogic: Error reading questions.json: " + e.getMessage());
            e.printStackTrace();
            return createFallbackQuestions();
        }
        return questions;
    }

    private static List<Question> createFallbackQuestions() {
        List<Question> fallback = new ArrayList<>();
        fallback.add(new Question("Тестове питання 1", List.of("Опція 1", "Опція 2", "Опція 3", "Опція 4"), "Опція 1", "images/question1.png"));
        fallback.add(new Question("Тестове питання 2", List.of("Опція 1", "Опція 2", "Опція 3", "Опція 4"), "Опція 2", "images/question2.png"));
        System.out.println("TestLogic: Using fallback questions.");
        return fallback;
    }

    public static void selectRandomQuestions(TestState state) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < QUESTIONS.size(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);
        state.setSelectedQuestions(new ArrayList<>(indices.subList(0, Math.min(5, indices.size()))));
        System.out.println("TestLogic: Selected questions: " + state.getSelectedQuestions());
    }

    public static String getQuestion(TestState state) {
        if (QUESTIONS.isEmpty() || state.getSelectedQuestions().isEmpty()) {
            return "Питання не завантажено (перевірте questions.json)";
        }
        int questionIndex = state.getSelectedQuestions().get(state.getCurrentQuestionIndex());
        if (questionIndex < 0 || questionIndex >= QUESTIONS.size()) {
            return "Помилка індексу питання";
        }
        String question = QUESTIONS.get(questionIndex).questionText;
        System.out.println("TestLogic: getQuestion returned: " + question);
        return question;
    }

    public static String[] getOptions(TestState state) {
        if (QUESTIONS.isEmpty() || state.getSelectedQuestions().isEmpty()) {
            return new String[]{"Опція 1", "Опція 2", "Опція 3"};
        }
        int questionIndex = state.getSelectedQuestions().get(state.getCurrentQuestionIndex());
        if (questionIndex < 0 || questionIndex >= QUESTIONS.size()) {
            return new String[]{"Опція 1", "Опція 2", "Опція 3"};
        }
        String[] options = QUESTIONS.get(questionIndex).options.toArray(new String[0]);
        System.out.println("TestLogic: getOptions returned: " + java.util.Arrays.toString(options));
        return options;
    }

    public static String getImage(TestState state) {
        if (QUESTIONS.isEmpty() || state.getSelectedQuestions().isEmpty()) {
            return "images/placeholder.png";
        }
        int questionIndex = state.getSelectedQuestions().get(state.getCurrentQuestionIndex());
        if (questionIndex < 0 || questionIndex >= QUESTIONS.size()) {
            return "images/placeholder.png";
        }
        String imagePath = QUESTIONS.get(questionIndex).imagePath;
        System.out.println("TestLogic: getImage returned: " + imagePath);
        return imagePath != null && !imagePath.isEmpty() ? imagePath : "images/placeholder.png";
    }

    public static void processAnswer(TestState state, String answer) {
        if (QUESTIONS.isEmpty() || state.getSelectedQuestions().isEmpty()) {
            state.setLastAnswerCorrect(false);
            state.setWrongAnswers(state.getWrongAnswers() + 1);
            state.setCurrentQuestionIndex(state.getCurrentQuestionIndex() + 1);
            return;
        }
        int questionIndex = state.getSelectedQuestions().get(state.getCurrentQuestionIndex());
        if (questionIndex < 0 || questionIndex >= QUESTIONS.size()) {
            state.setLastAnswerCorrect(false);
            state.setWrongAnswers(state.getWrongAnswers() + 1);
            state.setCurrentQuestionIndex(state.getCurrentQuestionIndex() + 1);
            return;
        }
        String correctAnswer = QUESTIONS.get(questionIndex).correctAnswer;
        boolean isCorrect = answer.equals(correctAnswer);
        state.setLastAnswerCorrect(isCorrect);
        state.addUserAnswer(answer, isCorrect);
        if (isCorrect) {
            state.setScore(state.getScore() + 1);
        } else {
            state.setWrongAnswers(state.getWrongAnswers() + 1);
        }
        state.setCurrentQuestionIndex(state.getCurrentQuestionIndex() + 1);
    }

    public static String getResult(TestState state) {
        if (state.getWrongAnswers() >= 2) {
            return "Тест провалено!";
        } else if (state.getCurrentQuestionIndex() >= 5) {
            return "Тест пройдено! Очки: " + state.getScore();
        }
        return "";
    }

    public static String getQuestionByIndex(int index) {
        if (index < 0 || index >= QUESTIONS.size()) {
            return "Питання не існує";
        }
        return QUESTIONS.get(index).questionText;
    }
}