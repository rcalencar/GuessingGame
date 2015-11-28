package com.example.rodrigo.guessinggame;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements GameBoardContract.View {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private GameBoardPresenter mActionsListener;
    private String mText;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG,"onCreate");
        super.onCreate(savedInstanceState);
        mActionsListener = new GameBoardPresenter(this);
        mActionsListener.newGame();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(LOG_TAG,"onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView");

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Button yes = (Button) root.findViewById(R.id.buttonYes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickYes(v);
            }
        });

        Button no = (Button) root.findViewById(R.id.buttonNo);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNo(v);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mText != null) {
            setQuestionText();
        }
    }

    // GameBoardContract.View
    @Override
    public void showWelcomeMessage(String text) {
        DialogFragment newFragment = AlertDialogFragment.newInstance(text, false, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mActionsListener.startGame();
            }
        });
        newFragment.show(getFragmentManager(), "dialog");
    }

    // GameBoardContract.View
    @Override
    public void showQuestion(String text) {
        mText = text;
        setQuestionText();
    }

    // GameBoardContract.View
    @Override
    public void finishGame(String text) {
        DialogFragment newFragment = AlertDialogFragment.newInstance(text, false, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mActionsListener.newGame();
            }
        });
        newFragment.show(getFragmentManager(), "dialog");
    }

    // GameBoardContract.View
    @Override
    public void showAddNewAnimal(String text) {

        final AlertDialogFragment newFragment = AlertDialogFragment.newInstance(text, true, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int whichButton) {
                Dialog dialog  = (Dialog) dialogInterface;
                EditText editText = (EditText) dialog.findViewById(R.id.input_dialog);
                mActionsListener.addAnimal(editText.getText().toString());
            }
        });
        newFragment.show(getFragmentManager(), "dialog");
    }

    // GameBoardContract.View
    @Override
    public void showAddNewAnimalQuestion(String text) {
        final DialogFragment newFragment = AlertDialogFragment.newInstance(text, true, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int whichButton) {
                Dialog dialog  = (Dialog) dialogInterface;
                EditText editText = (EditText) dialog.findViewById(R.id.input_dialog);
                mActionsListener.addQuestion(editText.getText().toString());
            }
        });
        newFragment.show(getFragmentManager(), "dialog");
    }

    private void clickYes(View view) {
        mActionsListener.answer(true);
    }

    private void clickNo(View view) {
        mActionsListener.answer(false);
    }

    public static class AlertDialogFragment extends DialogFragment {

        private DialogInterface.OnClickListener mListener;
        private boolean mShowEditText;

        public static AlertDialogFragment newInstance(String title, boolean showEditText, DialogInterface.OnClickListener onClickListener) {
            AlertDialogFragment frag = new AlertDialogFragment();
            frag.mListener = onClickListener;
            frag.mShowEditText = showEditText;
            Bundle args = new Bundle();
            args.putString("title", title);
            frag.setArguments(args);

            return frag;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setRetainInstance(true);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String title = getArguments().getString("title");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setPositiveButton(R.string.alert_dialog_ok, mListener);

            if(mShowEditText) {
                builder = builder.setView(R.layout.input_dialog);
            }
            return builder.create();
        }

        @Override
        public void onDestroyView() {
            if (getDialog() != null && getRetainInstance())
                getDialog().setDismissMessage(null);
            super.onDestroyView();
        }
    }

    private void setQuestionText() {
        TextView textView = (TextView) getView().findViewById(R.id.question);
        textView.setText(mText);
    }
}
