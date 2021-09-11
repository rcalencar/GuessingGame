package com.example.rodrigo.guessinggame

import androidx.lifecycle.ViewModel
import com.example.rodrigo.guessinggame.model.GameBoard
import com.example.rodrigo.guessinggame.model.Question

enum class GameState {
    NOT_STARTED, SHOW_WELCOME_MESSAGE, SHOW_ADD_NEW_ANIMAL, SHOW_ADD_NEW_ANIMAL_QUESTION, PLAY, FINISHED
}

class GameViewModel : ViewModel() {
    var questionText: String? = null
    var state = GameState.NOT_STARTED
        private set

    private val gameBoard = GameBoard(
        "Does the animal that you thought about %s?",
        "Is the animal that you thought about a %s?"
    )
    var newAnimalName: String = ""

    fun newGame() {
        state = GameState.SHOW_WELCOME_MESSAGE
        newAnimalName = ""
        gameBoard.newGame()
    }

    val victory: Boolean
        get() = gameBoard.victory

    val currentQuestion: Question
        get() = gameBoard.currentQuestion

    fun startGame() {
        state = GameState.PLAY
        questionText = gameBoard.move()
    }

    fun answer(answer: Boolean) {
        gameBoard.play(answer)

        if (gameBoard.finished) {
            state = if (gameBoard.victory) {
                GameState.FINISHED
            } else {
                GameState.SHOW_ADD_NEW_ANIMAL
            }
        } else {
            questionText = gameBoard.move()
        }
    }

    fun addAnimal(name: String) {
        state = GameState.SHOW_ADD_NEW_ANIMAL_QUESTION
        newAnimalName = name
    }

    fun addQuestion(text: String) {
        state = GameState.FINISHED
        gameBoard.addNewAnimal(newAnimalName, text)
    }
}