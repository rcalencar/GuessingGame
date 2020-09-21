package com.example.rodrigo.guessinggame

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AlertDialogFragment : DialogFragment() {
    var listener: DialogInterface.OnClickListener? = null
    var showEditText: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments!!.getString("title")
        val builder = AlertDialog.Builder(activity!!).setTitle(title)
            .setPositiveButton(R.string.alert_dialog_ok, listener)

        if (showEditText) {
            builder.setView(R.layout.input_dialog)
        }

        return builder.create()
    }

    override fun onDestroyView() {
        if (dialog != null && retainInstance) {
            (dialog ?: return).setDismissMessage(null)
        }
        super.onDestroyView()
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