package com.example.l5volunteering;

import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        cursor = db.rawQuery("SELECT news.rowid as newsrowid, news.name as newsName, annotation, text, news.date, sum(rate) as commonRate, nickname " +
                "FROM news INNER JOIN volunteers on volunteers.rowid = news.volunteerId " +
                "LEFT JOIN newsRating on newsRating.newsId = news.rowid " +
                "GROUP BY newsId ORDER BY commonRate DESC", null);
        LinearLayout llContent = getClearLayout();
        while (cursor.moveToNext()) {
            addNewsToLayout(llContent,
                    cursor.getString(cursor.getColumnIndex("newsrowid")),
                    cursor.getString(cursor.getColumnIndex("newsName")),
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        clHeader.setLayoutParams(params);
        clHeader.setId(ViewCompat.generateViewId());
        ConstraintSet constraintSet = new ConstraintSet();

        TextView twNews = new TextView(this.getActivity());
        TextView twHidden = new TextView(this.getActivity());
        TextView twRate = new TextView(this.getActivity());
        Button btnRateNews = new Button(this.getActivity());

        twHidden.setText(rowid);
        twHidden.setTag("newsId");
        twHidden.setVisibility(View.GONE);
        twNews.setText(newsName);
        twNews.setTypeface(twNews.getTypeface(), Typeface.BOLD);
        twNews.setTextSize(16);

        twRate.setText((commonRate != null ? commonRate : 0).toString());
        twRate.setTextSize(24);
        twRate.setPadding(6, 10, 0, 0);
        twRate.setId(ViewCompat.generateViewId());

        btnRateNews.setId(ViewCompat.generateViewId());
        btnRateNews.setText("♥");
        btnRateNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDialogFragment dialog = new NewsDialogFragment();
                Bundle args = new Bundle();
                TextView hiddenTw = (TextView) ((ConstraintLayout) v.getParent()).findViewWithTag("newsId");
                args.putString("newsId", hiddenTw.getText().toString());
                dialog.setArguments(args);
                dialog.show(getActivity().getSupportFragmentManager(), "custom");
            }
        });


        constraintSet.clone(clHeader);
        constraintSet.connect(twNews.getId(), ConstraintSet.START, clHeader.getId(), ConstraintSet.START, 0);
        constraintSet.connect(twNews.getId(), ConstraintSet.TOP, clHeader.getId(), ConstraintSet.TOP, 0);
        constraintSet.connect(twNews.getId(), ConstraintSet.END, twRate.getId(), ConstraintSet.START, 0);
        constraintSet.applyTo(clHeader);


        constraintSet.clone(clHeader);
        constraintSet.connect(twRate.getId(), ConstraintSet.TOP, clHeader.getId(), ConstraintSet.TOP, 0);
        constraintSet.connect(twRate.getId(), ConstraintSet.END, btnRateNews.getId(), ConstraintSet.START, 0);
        constraintSet.applyTo(clHeader);

        //btnRateNews.getBackground().setColorFilter(ContextCompat.getColor(this.getActivity(), android.R.color.white), PorterDuff.Mode.MULTIPLY);

        constraintSet.clone(clHeader);
        constraintSet.connect(btnRateNews.getId(), ConstraintSet.TOP, clHeader.getId(), ConstraintSet.TOP, 0);
        constraintSet.connect(btnRateNews.getId(), ConstraintSet.RIGHT, clHeader.getId(), ConstraintSet.RIGHT, 0);
        constraintSet.applyTo(clHeader);

        clHeader.addView(twHidden);

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        twNews.setLayoutParams(params3);
        clHeader.addView(twNews);


        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(convertToPx(60), convertToPx(40));
        twRate.setLayoutParams(params2);
        clHeader.addView(twRate);


        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(convertToPx(50), convertToPx(40));
        btnRateNews.setLayoutParams(params1);
        clHeader.addView(btnRateNews);


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

    private int convertToPx(int valueInDp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, getResources().getDisplayMetrics());
    }

    private void addRate(int rate, String newsId) {
        dbHelper = new DatabaseHelper(getView().getContext());
        db = dbHelper.getReadableDatabase();

        db.execSQL("INSERT INTO newsRating (newsId, volunteerId, rate, date) VALUES (" + newsId + ", 1, " + rate + ", '" + (new SimpleDateFormat("dd.MM.yyyy")).format(new Date()) + "');");
        cursor.close();
    }
}
