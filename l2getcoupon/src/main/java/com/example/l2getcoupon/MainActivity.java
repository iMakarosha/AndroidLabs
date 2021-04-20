package com.example.l2getcoupon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup rg1 = findViewById(R.id.rbGroup1);
        rg1.setOnCheckedChangeListener((group, checkedId) -> {
            RadioGroup rgH;
            LinearLayout layoutH;
            LinearLayout layoutV;

            switch (checkedId) {
                case R.id.radioButton13:
                    rgH = findViewById(R.id.rbGroup2);
                    rgH.setVisibility(View.GONE);
                    layoutH = findViewById(R.id.hideNoBike);
                    layoutH.setVisibility(View.GONE);
                    layoutV = findViewById(R.id.hideBike);
                    layoutV.setVisibility(View.VISIBLE);
                    break;
                default:
                    rgH = findViewById(R.id.rbGroup2);
                    rgH.setVisibility(View.VISIBLE);
                    layoutH = findViewById(R.id.hideBike);
                    layoutH.setVisibility(View.GONE);
                    layoutV = findViewById(R.id.hideNoBike);
                    layoutV.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    public void checkForm(View view) {
        Intent intent = new Intent(this, CheckActivity.class);
        RadioGroup rg1 = findViewById(R.id.rbGroup1);
        RadioButton rb1 = findViewById(rg1.getCheckedRadioButtonId());
        intent.putExtra("hasBike", rb1.getText());
        if (!rb1.getText().toString().contains("Нет, приобретать не планирую")) {
            intent.putExtra("hasOrWantBike", true);
            RadioGroup rg2 = findViewById(R.id.rbGroup2);
            RadioButton rb2 = findViewById(rg2.getCheckedRadioButtonId());
            intent.putExtra("myType", rb2.getText());

            MultiAutoCompleteTextView mtw = findViewById(R.id.multiAutoCompleteTextView31);
            intent.putExtra("expect", mtw.getText());
        } else {
            intent.putExtra("hasOrWantBike", false);
            MultiAutoCompleteTextView mtw = findViewById(R.id.multiAutoCompleteTextView31);
            intent.putExtra("transport", mtw.getText());
        }
        CheckBox chb1 = findViewById(R.id.checkBox);
        if (chb1.isChecked()) {
            intent.putExtra("mailSender", true);
        }

        startActivity(intent);
    }
}