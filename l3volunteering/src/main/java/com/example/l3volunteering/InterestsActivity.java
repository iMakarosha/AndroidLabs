package com.example.l3volunteering;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InterestsActivity extends AppCompatActivity {

    Volunteer volunteer;
    Drawable originalDrawable;
    boolean emailSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        originalDrawable = findViewById(R.id.etNickname).getBackground();

        Bundle arguments = getIntent().getExtras();
        volunteer = (Volunteer) arguments.get(Volunteer.class.getSimpleName());
        ((TextView) findViewById(R.id.textView8)).setText(String.format("%s, расскажите о своих интересах и целях. Изменить данные можно будет в личном кабинете.", volunteer.getName()));

        ((EditText) findViewById(R.id.etNickname)).setText(volunteer.getName());

    }

    public void sendForm(View view) {
        //валидация
        boolean isValid = true;
        if (((EditText) findViewById(R.id.etNickname)).getText().toString().isEmpty()) {
            setNotValidBack(R.id.etNickname);
            isValid = false;
        }
        if (((EditText) findViewById(R.id.etAbout)).getText().toString().isEmpty()) {
            setNotValidBack(R.id.etAbout);
            isValid = false;
        }

        if (isValid) {
            volunteer.setNickname(((EditText) findViewById(R.id.etNickname)).getText().toString());

            RadioGroup rg = findViewById(R.id.rbGroupActivity);
            RadioButton rb = findViewById(rg.getCheckedRadioButtonId());
            String rbName = getResources().getResourceEntryName(rb.getId());
            int activityId = Integer.parseInt(rbName.substring(rbName.length() - 1));
            volunteer.setActivity(activityId);

            LinearLayout llWantToDo = findViewById(R.id.llWantToDo);
            ArrayList<String> wantToDo = new ArrayList<>();
            int wantToDoCount = llWantToDo.getChildCount();
            for (int i = 1; i < wantToDoCount; i++) {
                if (((CheckBox) llWantToDo.getChildAt(i)).isChecked()) {
                    wantToDo.add(((CheckBox) llWantToDo.getChildAt(i)).getText().toString());
                }
            }

            //обработка активностей
            Intent intent = new Intent(this, SuccessActivity.class);
            intent.putExtra(Volunteer.class.getSimpleName(), volunteer);
            intent.putExtra("emailSubscribe", emailSubscribe);
            intent.putStringArrayListExtra("wantToDo", wantToDo);

            startActivity(intent);
        }
    }

    //дубль
    public void setNotValidBack(int elId) {
        findViewById(elId).setBackgroundResource(R.drawable.border);
    }

    public void setValidBack(int elId) {
        findViewById(elId).setBackground(originalDrawable);
    }
}