package com.example.l3volunteering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity implements TabsFragment.OnFragmentSendDataListener {

    String volunteerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Bundle arguments = getIntent().getExtras();
        volunteerId = (String) arguments.getString("volunteerId");

    }

    @Override
    protected void onResume() {
        super.onResume();
        onSendData("ЛК");
    }

    @Override
    public void onSendData(String selectedItem) {
        MyinfoFragment fragment = (MyinfoFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frgMyinfo);
        if (fragment != null){
            switch (selectedItem){
                case "О нас":
                    fragment.setContentAbout();
                    break;
                case "Новости":
                    fragment.setContentNews();
                    break;
                default:
                    fragment.setContentMyInfo(volunteerId);
                    break;
            }
        }
    }
}