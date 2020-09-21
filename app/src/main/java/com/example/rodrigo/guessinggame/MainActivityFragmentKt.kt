package com.example.rodrigo.guessinggame

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class MainActivityFragmentKt : Fragment(), GameBoardContract.View {
    private lateinit var actionsListener: GameBoardPresenterKt
    private var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionsListener = GameBoardPresenterKt(this)
        actionsListener.newGame()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        val yes: Button = root.findViewById(R.id.buttonYes)
        yes.setOnClickListener { actionsListener.answer(true) }

        val no: Button = root.findViewById(R.id.buttonNo)
        no.setOnClickListener { actionsListener.answer(false) }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (text != null) {
            setQuestionText()
        }
    }

    override fun showWelcomeMessage(text: String) {
        val dialog = newAlertDialogFragment(
            text,
            false
        ) { _, _ -> actionsListener.startGame() }
        if (BuildConfig.DEBUG && fragmentManager == null) {
            error("Assertion failed")
        }
        dialog.show(fragmentManager ?: return, DIALOG_FRAGMENT_NAME)
    }

    override fun showQuestion(text: String) {
        this.text = text
        setQuestionText()
    }

    private fun setQuestionText() {
        val textView: TextView = (view ?: return).findViewById(R.id.question)
        textView.text = text
    }

    override fun finishGame(text: String) {
        val dialogFragment = newAlertDialogFragment(
            text,
            false
        ) { _, _ -> actionsListener.newGame() }
        fragmentManager?.let { dialogFragment.show(it, DIALOG_FRAGMENT_NAME) }
    }

    override fun showAddNewAnimal(text: String) {
        val dialogFragment =
            newAlertDialogFragment(text, true) { d, _ ->
                val dialog = d as Dialog
                val editText: EditText = dialog.findViewById(R.id.input_dialog)
                actionsListener.addAnimal(editText.text.toString())
            }
        fragmentManager?.let { dialogFragment.show(it, DIALOG_FRAGMENT_NAME) }
    }

    override fun showAddNewAnimalQuestion(text: String) {
        val dialogFragment =
            newAlertDialogFragment(text, true) { d, _ ->
                val dialog = d as Dialog
                val editText: EditText = dialog.findViewById(R.id.input_dialog)
                actionsListener.addQuestion(editText.text.toString())
            }
        fragmentManager?.let { dialogFragment.show(it, DIALOG_FRAGMENT_NAME) }
    }

    companion object {
        const val DIALOG_FRAGMENT_NAME: String = "dialog"
    }
}