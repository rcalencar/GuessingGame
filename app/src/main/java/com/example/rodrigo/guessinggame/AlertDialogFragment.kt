package com.example.rodrigo.guessinggame

import android.app.AlertDialog
import android.app.Dialog
import androidx.fragment.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle

class AlertDialogFragment : DialogFragment() {
    var listener: DialogInterface.OnClickListener? = null
    var showEditText: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = requireArguments().getString("title")
        val builder = AlertDialog.Builder(requireActivity()).setTitle(title)
            .setPositiveButton(R.string.alert_dialog_ok, listener)

        if (showEditText) {
            builder.setView(R.layout.input_dialog)
        }

        return builder.create()
    }

    override fun onPause() {
        super.onPause()
        dismissAllowingStateLoss()
    }
}

fun newAlertDialogFragment(
    title: String,
    showEditText: Boolean,
    onClickListener: DialogInterface.OnClickListener
): AlertDialogFragment {
    val dialog = AlertDialogFragment()
    dialog.isCancelable = false
    dialog.listener = onClickListener
    dialog.showEditText = showEditText
    val args = Bundle()
    args.putString("title", title)
    dialog.arguments = args

    return dialog
}