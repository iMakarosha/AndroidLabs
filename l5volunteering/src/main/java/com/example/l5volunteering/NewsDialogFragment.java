package com.example.l5volunteering;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class NewsDialogFragment extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String newsId = getArguments().getString("newsId");
        return builder.setTitle("Оценить новость")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Как вы хотите оценить новость?")
                .setPositiveButton("+1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((AccountActivity)getActivity()).addRate(1, newsId);
                    }
                })
                .setNegativeButton("-1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((AccountActivity)getActivity()).addRate(-1, newsId);
                    }
                })
                .create();
    }


}
