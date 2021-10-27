package com.example.rodrigo.guessinggame

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rodrigo.guessinggame.databinding.FragmentMainBinding

class MainActivityFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val model: GameViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigate()
    }

    private fun navigate() {
        when (model.state) {
            GameState.NOT_STARTED -> {
                model.newGame()
                showWelcomeMessage()
            }
            GameState.SHOW_WELCOME_MESSAGE -> {
                showWelcomeMessage()
            }
            GameState.SHOW_ADD_NEW_ANIMAL -> {
                showAddNewAnimal()
            }
            GameState.SHOW_ADD_NEW_ANIMAL_QUESTION -> {
                showAddNewAnimalQuestion()
            }
            GameState.PLAY -> {
                showQuestionText()
            }
            GameState.FINISHED -> {
                finishGame()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        model.questionText?.let { showQuestionText() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonYes.setOnClickListener { answer(true) }
        binding.buttonNo.setOnClickListener { answer(false) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun answer(answer: Boolean) {
        model.answer(answer)
        navigate()
    }

    private fun showWelcomeMessage() {
        val text = "Think about an animal..."
        val dialog = newAlertDialogFragment(
            text,
            false
        ) { _, _ ->
            model.startGame()
            navigate()
        }

        dialog.show(parentFragmentManager, "dialog")
    }

    private fun showQuestionText() {
        binding.question.text = model.questionText
    }

    private fun finishGame() {
        val victory = "I win again!"
        val tryAgain = "Let's try again!"

        val dialogFragment = newAlertDialogFragment(
            if (model.victory) victory else tryAgain,
            false
        ) { _, _ ->
            model.newGame()
            navigate()
        }
        parentFragmentManager.let { dialogFragment.show(it, "dialog") }
    }

    private fun showAddNewAnimal() {
        val text = "What was the animal that you thought about?"
        val dialogFragment =
            newAlertDialogFragment(text, true) { d, _ ->
                val dialog = d as Dialog
                val editText: EditText = dialog.findViewById(R.id.input_dialog)
                model.addAnimal(editText.text.toString())
                navigate()
            }
        parentFragmentManager.let { dialogFragment.show(it, "dialog") }
    }

    private fun showAddNewAnimalQuestion() {
        val text = "A " + model.newAnimalName + "_________ but a " + model.currentQuestion.questionText + " does not."

        val dialogFragment =
            newAlertDialogFragment(text, true) { d, _ ->
                val dialog = d as Dialog
                val editText: EditText = dialog.findViewById(R.id.input_dialog)
                model.addQuestion(editText.text.toString())
                navigate()
            }
        parentFragmentManager.let { dialogFragment.show(it, "dialog") }
    }
}