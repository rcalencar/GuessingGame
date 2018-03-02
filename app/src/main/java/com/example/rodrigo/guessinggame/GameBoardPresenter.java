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
        gameBoard = GameBoard.createGame("Does the animal that you thought about %s?", "Is the animal that you thought about a %s?");
    }

    // GameBoardContract.UserActionsListener
    @Override
    public void newGame() {
        mNewAnimal = null;
        gameBoard.newGame();
        mView.showWelcomeMessage("Think about an animal...");
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
                mView.finishGame("I win again!");

            } else {
                mView.showAddNewAnimal("What was the animal that you thought about?");
            }

        } else {
            String question = gameBoard.move();
            mView.showQuestion(question);
        }
    }

    // GameBoardContract.UserActionsListener
    @Override
    public void addAnimal(String name) {
        mNewAnimal = name;
        mView.showAddNewAnimalQuestion("A " + name + "_________ but a " + gameBoard.getQuestionText() + " does not.") ;
    }

    // GameBoardContract.UserActionsListener
    @Override
    public void addQuestion(String text) {
        gameBoard.addAnimal(mNewAnimal, text);
        mView.finishGame("Let's try again!");
    }
}
