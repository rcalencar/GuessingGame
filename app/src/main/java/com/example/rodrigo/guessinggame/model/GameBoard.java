package com.example.rodrigo.guessinggame.model;

import androidx.annotation.NonNull;

/**
 * Created by rodrigo on 22/11/15.
 */
public class GameBoard {
    private final String guessText;
    private final String questionText;

    public final QuestionKt startQuestion = new QuestionKt("lives in water", false, null, new QuestionKt("shark", true), new QuestionKt("monkey", true));
    private QuestionKt currentQuestion = null;
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
        if(currentQuestion.getGuessing()) {
            text = String.format(guessText, currentQuestion.getQuestionText());
        } else {
            text = String.format(questionText, currentQuestion.getQuestionText());
        }

        return text;
    }

    public void play(boolean answer) {
        if(currentQuestion.getGuessing()) {
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
        QuestionKt parent = currentQuestion.getParent();
        if(parent.getYes() == currentQuestion) {
            QuestionKt newYes = newQuestion(animalName, newQuestionText, parent.getYes());
            parent.setYes(newYes);
        } else {
            QuestionKt newNo = newQuestion(animalName, newQuestionText, parent.getNo());
            parent.setYes(newNo);
        }
    }

    @NonNull
    private QuestionKt newQuestion(String animalName, String newQuestionText, QuestionKt currentQuestion) {
        return new QuestionKt(newQuestionText, false, null, new QuestionKt(animalName, true), currentQuestion);
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
