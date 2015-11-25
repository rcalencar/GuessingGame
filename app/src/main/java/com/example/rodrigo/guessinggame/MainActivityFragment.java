package com.example.rodrigo.guessinggame;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements GameBoardContract.View {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private GameBoardPresenter mActionsListener;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActionsListener = new GameBoardPresenter(this);
        mActionsListener.newGame();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        return root;
    }

    // GameBoardContract.View
    @Override
    public void showWelcomeMessage(String text) {
        DialogFragment newFragment = AlertDialogFragment.newInstance(text);
        newFragment.show(getFragmentManager(), "dialog");
    }

    // GameBoardContract.View
    @Override
    public void showQuestion(String text) {
        TextView textView =
                (TextView) getView().findViewById(R.id.question);
        textView.setText(text);
    }

    // GameBoardContract.View
    @Override
    public void finishGame(String text) {

    }

    // GameBoardContract.View
    @Override
    public void showAddNewAnimal(String text) {

    }

    // GameBoardContract.View
    @Override
    public void showAddNewAnimalQuestion(String text) {

    }

    public void clickYes(View view) {
        mActionsListener.answer(true);
    }

    public void clickNo(View view) {
        mActionsListener.answer(false);
    }

    public static class AlertDialogFragment extends DialogFragment {

        public static AlertDialogFragment newInstance(String title) {
            AlertDialogFragment frag = new AlertDialogFragment();
            Bundle args = new Bundle();
            args.putString("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String title = getArguments().getString("title");

            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setPositiveButton(R.string.alert_dialog_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((MainActivityFragment) getActivity().getSupportFragmentManager()
                                            .findFragmentByTag("fragment_main")).doOKClick();
                                }
                            }
                    ).create();
        }
    }

    private void doOKClick() {
        mActionsListener.startGame();
    }
}
