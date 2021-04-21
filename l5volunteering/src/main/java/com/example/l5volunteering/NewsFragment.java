package com.example.l5volunteering;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        dbHelper = new DatabaseHelper(getView().getContext());
        db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT news.name, annotation, text, date, nickname FROM news " +
                "INNER JOIN volunteers on volunteers.rowid = news.volunteerId", null);
        LinearLayout llContent = getClearLayout();
        while (cursor.moveToNext()) {
            addNewsToLayout(llContent,
                    cursor.getString(cursor.getColumnIndex("news.name")),
                    cursor.getString(cursor.getColumnIndex("annotation")),
                    cursor.getString(cursor.getColumnIndex("text")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("nickname")));
        }
        cursor.close();

        Button btnAddNews = new Button(this.getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btnAddNews.setLayoutParams(params);
        btnAddNews.setText("Добавить новость");
        btnAddNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDialogFragment dialog = new NewsDialogFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "custom");
            }
        });
        llContent.addView(btnAddNews);
    }

    private LinearLayout getClearLayout() {
        LinearLayout llContent = (LinearLayout) getView().findViewById(R.id.llNews);
        llContent.removeAllViews();
        return llContent;
    }

    private void addNewsToLayout(LinearLayout layout, String newsName, String annotation, String text, String date, String nickname) {
        LinearLayout llNews = new LinearLayout(this.getActivity());
        llNews.setOrientation(LinearLayout.VERTICAL);
        llNews.setPadding(0, 0, 0, 20);
        TextView twNews = new TextView(this.getActivity());
        twNews.setText(newsName);
        twNews.setTypeface(twNews.getTypeface(), Typeface.BOLD);
        twNews.setTextSize(16);
        llNews.addView(twNews);

        twNews = new TextView(this.getActivity());
        twNews.setText(date);
        twNews.setTypeface(twNews.getTypeface(), Typeface.ITALIC);
        twNews.setTextSize(12);
        twNews.setTextColor(Color.GRAY);
        llNews.addView(twNews);

        twNews = new TextView(this.getActivity());
        twNews.setText(annotation);
        twNews.setTextSize(14);
        llNews.addView(twNews);

        twNews = new TextView(this.getActivity());
        twNews.setText("Автор: " + nickname);
        twNews.setTypeface(twNews.getTypeface(), Typeface.ITALIC);
        twNews.setTextColor(Color.GRAY);
        twNews.setTextSize(12);
        llNews.addView(twNews);

        layout.addView(llNews);
    }
}
