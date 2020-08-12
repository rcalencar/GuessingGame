package com.example.rodrigo.guessinggame.model

class QuestionKt(var questionText: String, var guessing: Boolean, var parent: QuestionKt? = null, yes: QuestionKt? = null, no: QuestionKt? = null) {
    constructor(questionText: String, guessing: Boolean) : this(questionText, guessing, null, null, null)

    var yes: QuestionKt? = yes
        set(value) {
            value!!.parent = this
            field = value
        }
    var no: QuestionKt? = no
        set(value) {
            value!!.parent = this
            field = value
        }

    init {
        this.yes?.parent = this
        this.no?.parent = this
    }
}