package com.example.rodrigo.guessinggame.model;

/**
 * Created by rodrigo on 22/11/15.
 */
public class GameBoard {
    static private boolean setup = false;
    static private String guessText;
    static private String questionText;

    public final Question startQuestion = new Question("lives in water", new Question("shark"), new Question("monkey"));
    private Question currentQuestion = null;
    private boolean finished = true;
    private boolean victory = false;

    private GameBoard() { }

    public static void setup(String questionText, String guessText) {
        GameBoard.questionText = questionText;
        GameBoard.guessText = guessText;
        GameBoard.setup = true;
    }

    public static GameBoard newGame() {
        if(!setup) throw new IllegalStateException("Must call setup before getInstance.");
        return new GameBoard();
    }

    public void start() {
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

    public void addAnimal(String animalName, String newQuestionText) {
        Question parent = currentQuestion.getParent();
        Question parentYes = parent.getYes();

        Question _new = new Question(newQuestionText, new Question(animalName), parentYes);

        parent.setYes(_new);
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
