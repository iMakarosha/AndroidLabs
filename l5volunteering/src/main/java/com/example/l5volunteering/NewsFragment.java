package com.example.l5volunteering;

import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import static android.view.View.generateViewId;

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

        cursor = db.rawQuery("SELECT news.rowid, news.name, annotation, text, news.date, sum(rate) as commonRate, nickname " +
                "FROM news INNER JOIN volunteers on volunteers.rowid = news.volunteerId " +
                "LEFT JOIN newsRating on newsRating.newsId = news.rowid " +
                "GROUP BY newsId ORDER BY commonRate DESC", null);
        LinearLayout llContent = getClearLayout();
        while (cursor.moveToNext()) {
            addNewsToLayout(llContent,
                    cursor.getString(cursor.getColumnIndex("news.rowid")),
                    cursor.getString(cursor.getColumnIndex("news.name")),
                    cursor.getString(cursor.getColumnIndex("annotation")),
                    cursor.getString(cursor.getColumnIndex("text")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("commonRate")),
                    cursor.getString(cursor.getColumnIndex("nickname")));
        }
        cursor.close();

//        Button btnAddNews = new Button(this.getActivity());
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        btnAddNews.setLayoutParams(params);
//        btnAddNews.setText("Добавить новость");
//        btnAddNews.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NewsDialogFragment dialog = new NewsDialogFragment();
//                dialog.show(getActivity().getSupportFragmentManager(), "custom");
//            }
//        });
//        llContent.addView(btnAddNews);
    }

    private LinearLayout getClearLayout() {
        LinearLayout llContent = (LinearLayout) getView().findViewById(R.id.llNews);
        llContent.removeAllViews();
        return llContent;
    }

    private void addNewsToLayout(LinearLayout layout, String rowid, String newsName, String annotation, String text, String date, String commonRate, String nickname) {
        LinearLayout llNews = new LinearLayout(this.getActivity());
        llNews.setOrientation(LinearLayout.VERTICAL);
        llNews.setPadding(0, 0, 0, 20);

            ConstraintLayout clHeader = new ConstraintLayout(this.getActivity());
            clHeader.setBackgroundColor(Color.GREEN);
            ConstraintSet constraintSet = new ConstraintSet();
            LinearLayout.LayoutParams params;

            Button btnRateNews = new Button(this.getActivity());
            btnRateNews.setId(ViewCompat.generateViewId());
            int asdf = btnRateNews.getId();
            btnRateNews.setText("♥");
            btnRateNews.getBackground().setColorFilter(ContextCompat.getColor(this.getActivity(), android.R.color.white), PorterDuff.Mode.MULTIPLY);

            constraintSet.clone(clHeader);
            constraintSet.connect(btnRateNews.getId(),ConstraintSet.TOP, clHeader.getId(),ConstraintSet.TOP,0);
            constraintSet.connect(btnRateNews.getId(),ConstraintSet.END, clHeader.getId(),ConstraintSet.END,0);
            constraintSet.applyTo(clHeader);

            params = new LinearLayout.LayoutParams(50, 50);
            btnRateNews.setLayoutParams(params);
            clHeader.addView(btnRateNews);


            TextView twRate = new TextView(this.getActivity());
        twRate.setId(ViewCompat.generateViewId());
        int assss = twRate.getId();
        twRate.setText((commonRate != null ? commonRate : 0).toString());
        twRate.setTextSize(24);
        twRate.setPadding(6, 10, 0, 0);

            constraintSet.connect(twRate.getId(),ConstraintSet.TOP, clHeader.getId(),ConstraintSet.TOP,0);
            constraintSet.connect(twRate.getId(),ConstraintSet.END, btnRateNews.getId(),ConstraintSet.START,0);
            constraintSet.applyTo(clHeader);

            params = new LinearLayout.LayoutParams(60, 40);
        twRate.setLayoutParams(params);
            clHeader.addView(twRate);



        TextView twNews = new TextView(this.getActivity());
            twNews.setText(newsName);
            twNews.setTypeface(twNews.getTypeface(), Typeface.BOLD);
            twNews.setTextSize(16);
            twNews.setBackgroundColor(Color.BLUE);

            constraintSet.connect(twNews.getId(),ConstraintSet.START, clHeader.getId(),ConstraintSet.START,0);
            constraintSet.connect(twNews.getId(),ConstraintSet.TOP, clHeader.getId(),ConstraintSet.TOP,0);
            constraintSet.connect(twNews.getId(),ConstraintSet.END, twRate.getId(),ConstraintSet.START,0);
            constraintSet.applyTo(clHeader);

            params = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            twNews.setLayoutParams(params);
            clHeader.addView(twNews);


        llNews.addView(clHeader);

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
