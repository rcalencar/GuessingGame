package com.example.rodrigo.guessinggame;

import android.app.Dialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements GameBoardContract.View {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    public static final String DIALOG_FRAGMENT_NAME = "dialog";
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

        Button yes = root.findViewById(R.id.buttonYes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickYes();
            }
        });

        Button no = root.findViewById(R.id.buttonNo);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNo();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
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
        assert getFragmentManager() != null;
        newFragment.show(getFragmentManager(), DIALOG_FRAGMENT_NAME);
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
        assert getFragmentManager() != null;
        newFragment.show(getFragmentManager(), DIALOG_FRAGMENT_NAME);
    }

    // GameBoardContract.View
    @Override
    public void showAddNewAnimal(String text) {

        final AlertDialogFragment newFragment = AlertDialogFragment.newInstance(text, true, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int whichButton) {
                Dialog dialog  = (Dialog) dialogInterface;
                EditText editText = dialog.findViewById(R.id.input_dialog);
                mActionsListener.addAnimal(editText.getText().toString());
            }
        });
        assert getFragmentManager() != null;
        newFragment.show(getFragmentManager(), DIALOG_FRAGMENT_NAME);
    }

    // GameBoardContract.View
    @Override
    public void showAddNewAnimalQuestion(String text) {
        final DialogFragment newFragment = AlertDialogFragment.newInstance(text, true, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int whichButton) {
                Dialog dialog  = (Dialog) dialogInterface;
                EditText editText = dialog.findViewById(R.id.input_dialog);
                mActionsListener.addQuestion(editText.getText().toString());
            }
        });
        assert getFragmentManager() != null;
        newFragment.show(getFragmentManager(), DIALOG_FRAGMENT_NAME);
    }

    private void clickYes() {
        mActionsListener.answer(true);
    }

    private void clickNo() {
        mActionsListener.answer(false);
    }

    public static class AlertDialogFragment extends DialogFragment {

        private DialogInterface.OnClickListener mListener;
        private boolean mShowEditText;

        public static AlertDialogFragment newInstance(String title, boolean showEditText, DialogInterface.OnClickListener onClickListener) {
            AlertDialogFragment frag = new AlertDialogFragment();
            frag.setCancelable(false);
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

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            assert getArguments() != null;
            String title = getArguments().getString("title");
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                    .setTitle(title)
                    .setPositiveButton(R.string.alert_dialog_ok, mListener);

            if(mShowEditText) {
                builder = builder.setView(R.layout.input_dialog);
            }
            return builder.create();
        }

        @Override
        public void onDestroyView() {
            if (getDialog() != null && getRetainInstance()){
                getDialog().setDismissMessage(null);
            }

            super.onDestroyView();
        }
    }

    private void setQuestionText() {
        TextView textView = Objects.requireNonNull(getView()).findViewById(R.id.question);
        textView.setText(mText);
    }
}
