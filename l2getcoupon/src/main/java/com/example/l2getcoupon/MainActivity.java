package com.example.l2getcoupon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup rg1 = (RadioGroup)findViewById(R.id.rbGroup1);
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioGroup rgH;
                LinearLayout layoutH;
                LinearLayout layoutV;

                switch (checkedId){
                    case R.id.radioButton13:
                        rgH = (RadioGroup)findViewById(R.id.rbGroup2);
                        rgH.setVisibility(View.GONE);
                        layoutH = (LinearLayout)findViewById(R.id.hideNoBike);
                        layoutH.setVisibility(View.GONE);
                        layoutV = (LinearLayout)findViewById(R.id.hideBike);
                        layoutV.setVisibility(View.VISIBLE);
                break;
                    default:
                        rgH = (RadioGroup)findViewById(R.id.rbGroup2);
                        rgH.setVisibility(View.VISIBLE);
                        layoutH = (LinearLayout)findViewById(R.id.hideBike);
                        layoutH.setVisibility(View.GONE);
                        layoutV = (LinearLayout)findViewById(R.id.hideNoBike);
                        layoutV.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    public void checkForm(View view){
        Intent intent = new Intent(this, CheckActivity.class);
        RadioGroup rg1 = (RadioGroup) findViewById(R.id.rbGroup1);
        RadioButton rb1 = (RadioButton) findViewById(rg1.getCheckedRadioButtonId());
        intent.putExtra("hasBike", rb1.getText());
        if(rb1.getText().toString().indexOf("Нет, приобретать не планирую") == -1){
            intent.putExtra("hasOrWantBike", true);
            RadioGroup rg2 = (RadioGroup) findViewById(R.id.rbGroup2);
            RadioButton rb2 = (RadioButton) findViewById(rg2.getCheckedRadioButtonId());
            intent.putExtra("myType", rb2.getText());

            MultiAutoCompleteTextView mtw = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView31);
            intent.putExtra("expect", mtw.getText());
        }
        else{
            intent.putExtra("hasOrWantBike", false);
            MultiAutoCompleteTextView mtw = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView31);
            intent.putExtra("transport", mtw.getText());
        }
        CheckBox chb1 = (CheckBox) findViewById(R.id.checkBox);
        if(chb1.isChecked()){
            intent.putExtra("mailSender", true);
        }

        startActivity(intent);
    }


}