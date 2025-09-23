package com.example.model;


import java.util.ArrayList;
import java.util.List;

public class TestState {
    private int currentQuestionIndex;
    private int score;
    private int wrongAnswers;
    private String groupNumber;
    private String fullName;
    private int attempts;
    private List<Integer> selectedQuestions; // Індекси обраних питань
    private boolean lastAnswerCorrect; // Для кольору сторінки

    public TestState() {
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.wrongAnswers = 0;
        this.attempts = 0;
        this.selectedQuestions = new ArrayList<>();
        this.lastAnswerCorrect = true;
    }

    public int getCurrentQuestionIndex() { return currentQuestionIndex; }
    public void setCurrentQuestionIndex(int currentQuestionIndex) { this.currentQuestionIndex = currentQuestionIndex; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getWrongAnswers() { return wrongAnswers; }
    public void setWrongAnswers(int wrongAnswers) { this.wrongAnswers = wrongAnswers; }
    public String getGroupNumber() { return groupNumber; }
    public void setGroupNumber(String groupNumber) { this.groupNumber = groupNumber; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public int getAttempts() { return attempts; }
    public void setAttempts(int attempts) { this.attempts = attempts; }
    public List<Integer> getSelectedQuestions() { return selectedQuestions; }
    public void setSelectedQuestions(List<Integer> selectedQuestions) { this.selectedQuestions = selectedQuestions; }
    public boolean isLastAnswerCorrect() { return lastAnswerCorrect; }
    public void setLastAnswerCorrect(boolean lastAnswerCorrect) { this.lastAnswerCorrect = lastAnswerCorrect; }
}