package com.example.rodrigo.guessinggame.model

interface Question {
    val questionText: String
}

interface Child : Question {
    var parent: Parent
}

interface Parent : Question {
    var yes: Child
    var no: Child
}

open class GameRoot(override val questionText: String, override var yes: Child, override var no: Child) : Parent {
    init {
        yes.parent = this
        no.parent = this
    }
}

class GameQuestion(override val questionText: String, override var parent: Parent, override var yes: Child, override var no: Child) : Parent, Child {
    init {
        yes.parent = this
        no.parent = this
    }
}

class GameGuess(override val questionText: String) : Child {
    override lateinit var parent: Parent
}