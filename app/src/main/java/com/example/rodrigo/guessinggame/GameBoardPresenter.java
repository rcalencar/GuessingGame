package com.example.rodrigo.guessinggame;

import com.example.rodrigo.guessinggame.model.GameBoard;

/**
 * Created by rodrigo on 25/11/15.
 */
public class GameBoardPresenter implements GameBoardContract.UserActionsListener {

    private final GameBoardContract.View mView;
    private final GameBoard gameBoard;
    private String mNewAnimal;

    public GameBoardPresenter(GameBoardContract.View mView) {
        this.mView = mView;
        GameBoard.setup("Does the animal that you thought about %s?", "Is the animal that you thought about a %s?");
        gameBoard = GameBoard.newGame();
    }

    // GameBoardContract.UserActionsListener
    @Override
    public void newGame() {
        mNewAnimal = null;
        gameBoard.start();
        mView.showWelcomeMessage("Pense em algum animal...");
    }

    // GameBoardContract.UserActionsListener
    @Override
    public void startGame() {
        String question = gameBoard.move();
        mView.showQuestion(question);
    }

    // GameBoardContract.UserActionsListener
    @Override
    public void answer(boolean answer) {
        gameBoard.play(answer);

        if(gameBoard.hasFinished()) {
            if(gameBoard.hasVictory()) {
                mView.finishGame("I won again!");

            } else {
                mView.showAddNewAnimal("Animal?");
            }

        } else {
            String question = gameBoard.move();
            mView.showQuestion(question);
        }
    }

    // GameBoardContract.UserActionsListener
    @Override
    public void animal(String name) {
        mNewAnimal = name;
        mView.showAddNewAnimalQuestion("Question?");
    }

    // GameBoardContract.UserActionsListener
    @Override
    public void question(String text) {
        gameBoard.addAnimal(mNewAnimal, text);
        mView.finishGame("I lost!");
    }
}
