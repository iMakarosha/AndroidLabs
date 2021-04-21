package com.example.l5volunteering;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class AccountActivity extends AppCompatActivity implements TabsFragment.OnFragmentSendDataListener {

    String volunteerId;
    FragmentTransaction fTrans;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        onSendData("ЛК");
    }

    @Override
    public void onSendData(String selectedItem) {
        fTrans = getSupportFragmentManager().beginTransaction();
        switch (selectedItem){
            case "О нас":
                fTrans.replace(R.id.frgMyinfo, frg2);
                break;
            case "Новости":
                fTrans.replace(R.id.frgMyinfo, frg3);
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
}