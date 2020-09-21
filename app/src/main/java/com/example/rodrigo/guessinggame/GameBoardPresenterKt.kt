package com.example.rodrigo.guessinggame

import com.example.rodrigo.guessinggame.model.GameBoardKt

class GameBoardPresenterKt(private val view: GameBoardContract.View) :
    GameBoardContract.UserActionsListener {
    private val gameBoard = GameBoardKt(
        "Does the animal that you thought about %s?",
        "Is the animal that you thought about a %s?"
    )
    private var newAnimalName: String = ""

    override fun newGame() {
        newAnimalName = ""
        gameBoard.newGame()
        view.showWelcomeMessage("Think about an animal...")
    }

    override fun startGame() {
        view.showQuestion(gameBoard.move())
    }

    override fun answer(answer: Boolean) {
        gameBoard.play(answer)

        if (gameBoard.finished) {
            if (gameBoard.victory) {
                view.finishGame("I win again!")
            } else {
                view.showAddNewAnimal("What was the animal that you thought about?")
            }
        } else {
            view.showQuestion(gameBoard.move())
        }
    }

    override fun addAnimal(name: String) {
        newAnimalName = name
        view.showAddNewAnimalQuestion("A " + name + "_________ but a " + gameBoard.currentQuestion.questionText + " does not.")
    }

    override fun addQuestion(text: String) {
        gameBoard.addNewAnimal(newAnimalName, text)
        view.finishGame("Let's try again!")
    }
}