package com.example.rodrigo.guessinggame

interface GameBoardContract {
    interface View {
        fun showWelcomeMessage(text: String?)
        fun showQuestion(text: String?)
        fun finishGame(text: String?)
        fun showAddNewAnimal(text: String?)
        fun showAddNewAnimalQuestion(text: String?)
    }

    interface UserActionsListener {
        fun newGame()
        fun startGame()
        fun answer(answer: Boolean)
        fun addAnimal(name: String?)
        fun addQuestion(text: String?)
    }
}