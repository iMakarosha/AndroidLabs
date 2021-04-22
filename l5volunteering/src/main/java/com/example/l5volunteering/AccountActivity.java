package com.example.l5volunteering;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountActivity extends AppCompatActivity implements TabsFragment.OnFragmentSendDataListener {

    String volunteerId;
    MyinfoFragment frg1;
    AboutFragment frg2;
    NewsFragment frg3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Bundle arguments = getIntent().getExtras();
        volunteerId = (String) arguments.getString("volunteerId");

        frg1 = new MyinfoFragment();
        frg2 = new AboutFragment();
        frg3 = new NewsFragment();

        ((Button) findViewById(R.id.btnCloseDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseDialogFragment dialog = new CloseDialogFragment();
                dialog.show(getSupportFragmentManager(), "custom");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        onSendData("ЛК");
    }

    @Override
    public void onSendData(String selectedItem) {
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        switch (selectedItem) {
            case "О нас":
                fTrans.replace(R.id.frgMyinfo, frg2);
                break;
            case "Новости":

//что-то надо сюда думаю добавить, чтобы обновлялось сразу
                fTrans.replace(R.id.frgMyinfo, frg3);
                fTrans.detach(frg3);
                fTrans.attach(frg3);
                break;
            default:
                Bundle bundle = new Bundle();
                bundle.putString("vouId", volunteerId);
                frg1.setArguments(bundle);
                fTrans.replace(R.id.frgMyinfo, frg1);
                break;
        }
        fTrans.commit();
    }


    public void addRate(int rate, String newsId) {

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        db.execSQL("INSERT INTO newsRating (newsId, volunteerId, rate, date) VALUES (" + newsId + ", "+volunteerId+", " + rate + ", '" + (new SimpleDateFormat("dd.MM.yyyy")).format(new Date()) + "');");
        onSendData("Новости");
    }

}