package com.example.rodrigo.guessinggame.model

class GameBoardKt(var questionText: String, var guessText: String) {
    var rootQuestion = QuestionKt("lives in water", false, null, QuestionKt("shark", true), QuestionKt("monkey", true))
    var currentQuestion: QuestionKt? = null
    var finished = true
    var victory = false

    fun newGame() {
        currentQuestion = rootQuestion
        finished = false
    }

    fun move() : String {
        return if (currentQuestion!!.guessing) String.format(guessText, currentQuestion?.questionText)
        else String.format(questionText, currentQuestion?.questionText)
    }

    fun play(answer: Boolean) {
        if (currentQuestion!!.guessing) {
            victory = answer
            finished = true
        } else {
            currentQuestion = if (answer) currentQuestion?.yes else currentQuestion?.no
        }
    }

    fun addNewAnimal(animalName: String, newQuestionText: String) {
        val parent = currentQuestion!!.parent
        val newQuestion = QuestionKt(newQuestionText, false, null, QuestionKt(animalName, true), currentQuestion)
        if (parent!!.yes == currentQuestion) {
            parent.yes = newQuestion
        } else {
            parent.no = newQuestion
        }
    }
}