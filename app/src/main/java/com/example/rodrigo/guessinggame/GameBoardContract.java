package com.example.rodrigo.guessinggame;

/**
 * Created by rodrigo on 25/11/15.
 */
interface GameBoardContract {

    interface View {
        void showWelcomeMessage(String text);

        void showQuestion(String text);

        void finishGame(String text);

        void showAddNewAnimal(String text);

        void showAddNewAnimalQuestion(String text);
    }

    interface UserActionsListener {
        void newGame();

        void startGame();

        void answer(boolean answer);

        void addAnimal(String name);

        void addQuestion(String text);
    }
}
