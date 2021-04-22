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
                .setMessage("Как вы хотите оценить новость?" + newsId)
                .setPositiveButton("+1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), AccountActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//?
                        intent.putExtra("", true);
                        intent.putExtra("newsId", newsId);
                        intent.putExtra("rating", 1);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("-1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), AccountActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//?
                        intent.putExtra("addNews", true);
                        intent.putExtra("newsId", newsId);
                        intent.putExtra("rating", -1);
                        startActivity(intent);
                    }
                })
                .create();
    }


}
