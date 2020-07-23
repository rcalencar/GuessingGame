package com.example.rodrigo.guessinggame.model;

import androidx.annotation.NonNull;

/**
 * Created by rodrigo on 22/11/15.
 */
public class GameBoard {
    private final String guessText;
    private final String questionText;

    public final Question startQuestion = new Question("lives in water", new Question("shark"), new Question("monkey"));
    private Question currentQuestion = null;
    private boolean finished = true;
    private boolean victory = false;

    private GameBoard(String questionText, String guessText) {
        this.questionText = questionText;
        this.guessText = guessText;
    }

    public static GameBoard createGame(String questionText, String guessText) {
        return new GameBoard(questionText, guessText);
    }

    public void newGame() {
        currentQuestion = startQuestion;
        finished = false;
    }

    public String move() {
        String text;
        if(currentQuestion.isGuessing()) {
            text = String.format(guessText, currentQuestion.getQuestionText());
        } else {
            text = String.format(questionText, currentQuestion.getQuestionText());
        }

        return text;
    }

    public void play(boolean answer) {
        if(currentQuestion.isGuessing()) {
            victory  = answer;
            finished = true;

        } else {
            if(answer) {
                currentQuestion = currentQuestion.getYes();
            } else {
                currentQuestion = currentQuestion.getNo();
            }
        }
    }

    public void addNewAnimal(String animalName, String newQuestionText) {
        if(currentQuestion.getParent().getYes() == currentQuestion) {
            Question newYes = newQuestion(animalName, newQuestionText, currentQuestion.getParent().getYes());
            currentQuestion.getParent().setYes(newYes);
        } else {
            Question newNo = newQuestion(animalName, newQuestionText, currentQuestion.getParent().getNo());
            currentQuestion.getParent().setNo(newNo);
        }
    }

    @NonNull
    private Question newQuestion(String animalName, String newQuestionText, Question currentQuestion) {
        return new Question(newQuestionText, new Question(animalName), currentQuestion);
    }

    public boolean hasFinished() {
        return finished;
    }

    public boolean hasVictory() {
        return victory;
    }

    public String getQuestionText() {
        return currentQuestion.getQuestionText();
    }
}
