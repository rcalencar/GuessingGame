package com.example.rodrigo.guessinggame.model

class GameBoardKt(var questionText: String, var guessText: String) {
    var rootQuestion = GameRoot("lives in water", GameGuess("shark"), GameGuess("monkey"))

    lateinit var currentQuestion: Question
    var finished = true
    var victory = false

    fun newGame() {
        currentQuestion = rootQuestion
        finished = false
    }

    fun move() : String {
        return if (currentQuestion is GameGuess) String.format(guessText, currentQuestion.questionText)
        else String.format(questionText, currentQuestion.questionText)
    }

    fun play(answer: Boolean) {
        if (currentQuestion is GameGuess) {
            victory = answer
            finished = true
        } else {
            currentQuestion = if (answer) (currentQuestion as Parent).yes else (currentQuestion as Parent).no
        }
    }

    fun addNewAnimal(animalName: String, newQuestionText: String) {
        val parent = (currentQuestion as Child).parent
        val newQuestion = GameQuestion(newQuestionText, parent, GameGuess(animalName), currentQuestion as Child)
        if (parent.yes == currentQuestion) {
            parent.yes = newQuestion
        } else {
            parent.no = newQuestion
        }
    }
}