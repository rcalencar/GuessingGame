package com.example.rodrigo.guessinggame;

import androidx.annotation.Nullable;

import com.example.rodrigo.guessinggame.model.GameBoard;
import com.example.rodrigo.guessinggame.util.PrintUtil;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameBoardUnitTest {

    public static final String LIVES_IN_WATER = "lives in water";
    public static final String NEMO = "Nemo";
    public static final String GETS_LOST = "gets lost";
    public static final String SHARK = "shark";
    public static final String MONKEY = "monkey";
    public static final String DORI = "Dori";
    public static final String IS_BLUE = "is blue";

    @Test
    public void shark() {
        System.out.println("**** You thought: " + SHARK);

        HashMap<String, Boolean> answers = new HashMap<>();
        answers.put(LIVES_IN_WATER, Boolean.TRUE);
        answers.put(SHARK, Boolean.TRUE);

        GameBoard board = new GameBoard("Does the animal that you thought about %s?", "Is the animal that you thought about a %s?");
        board.newGame();
        playA_Game(board, answers);

        assertTrue(board.getFinished());
        assertTrue(board.getVictory());
    }

    @Test
    public void bruce() {
        System.out.println("**** You thought: " + NEMO + " plus " + SHARK);

        HashMap<String, Boolean> answers = new HashMap<>();
        answers.put(LIVES_IN_WATER, Boolean.TRUE);
        answers.put(SHARK, Boolean.FALSE);

        GameBoard board = new GameBoard("Does the animal that you thought about %s?", "Is the animal that you thought about a %s?");
        board.newGame();
        playA_Game(board, answers);

        assertTrue(board.getFinished());
        assertFalse(board.getVictory());

        board.addNewAnimal(NEMO, GETS_LOST);

        answers.put(GETS_LOST, Boolean.FALSE);
        answers.put(SHARK, Boolean.TRUE);

        board.newGame();
        playA_Game(board, answers);

        assertTrue(board.getFinished());
        assertTrue(board.getVictory());
    }

    @Test
    public void sharkIncomplete() {
        System.out.println("**** You thought: " + SHARK + ", but incomplete answers.");

        HashMap<String, Boolean> answers = new HashMap<>();
        answers.put(LIVES_IN_WATER, Boolean.TRUE);

        GameBoard board = new GameBoard("Does the animal that you thought about %s?", "Is the animal that you thought about a %s?");
        board.newGame();
        playA_Game(board, answers);

        assertFalse(board.getFinished());
        assertFalse(board.getVictory());
    }

    @Test
    public void findingNemo() {
        System.out.println("**** Finding Nemo");

        HashMap<String, Boolean> answers = new HashMap<>();
        answers.put(LIVES_IN_WATER, Boolean.TRUE);
        answers.put(SHARK, Boolean.FALSE);

        GameBoard board = new GameBoard("Does the animal that you thought about %s?", "Is the animal that you thought about a %s?");

        // 1st round
        board.newGame();
        playA_Game(board, answers);

        assertTrue(board.getFinished());
        assertFalse(board.getVictory());

        System.out.println("Adding " + NEMO + " - " + GETS_LOST);
        board.addNewAnimal(NEMO, GETS_LOST);

        answers.put(GETS_LOST, Boolean.TRUE);
        answers.put(NEMO, Boolean.TRUE);

        // 2nd round
        board.newGame();
        playA_Game(board, answers);

        assertTrue(board.getFinished());
        assertTrue(board.getVictory());

        answers.put(NEMO, Boolean.FALSE);

        // 3rd round
        board.newGame();
        playA_Game(board, answers);

        assertTrue(board.getFinished());
        assertFalse(board.getVictory());

        System.out.println("Adding " + DORI + " - " + IS_BLUE);
        board.addNewAnimal(DORI, IS_BLUE);

        answers.put(DORI, Boolean.TRUE);
        answers.put(IS_BLUE, Boolean.TRUE);

        // 4th round
        board.newGame();
        playA_Game(board, answers);

        assertTrue(board.getFinished());
        assertTrue(board.getVictory());

        board.newGame();
        PrintUtil.print(board.getCurrentQuestion());
    }

    @Test
    public void monkey() {
        System.out.println("**** You thought: " + MONKEY);

        HashMap<String, Boolean> answers = new HashMap<>();
        answers.put(LIVES_IN_WATER, Boolean.FALSE);
        answers.put(MONKEY, Boolean.TRUE);

        GameBoard board = new GameBoard("Does the animal that you thought about %s?", "Is the animal that you thought about a %s?");
        board.newGame();
        playA_Game(board, answers);

        assertTrue(board.getFinished());
        assertTrue(board.getVictory());
    }

    private void playA_Game(GameBoard board, HashMap<String, Boolean> answers) {

        while (!board.getFinished()) {
            String completeQuestionText = board.move();

            Boolean answer = getAnswer(board, answers);
            if (answer == null) return;
            System.out.println(completeQuestionText + " " + answer);

            board.play(answer);
        }

        System.out.println("It has finished: " + board.getFinished());
        System.out.println("I won: " + board.getVictory());
    }

    @Nullable
    private Boolean getAnswer(GameBoard board, HashMap<String, Boolean> answers) {
        return answers.get(board.getCurrentQuestion().getQuestionText());
    }
}