package com.example.l4volunteering;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Bundle arguments = getIntent().getExtras();
        Volunteer volunteer = (Volunteer) arguments.get(Volunteer.class.getSimpleName());
        ((TextView) findViewById(R.id.twHello)).setText(volunteer.getName() +
                ", добро пожаловать в личный кабинет волонтера. Здесь будет отображаться информация о вас, вашей активности" +
                "и мероприятиях, в которых вы можете принять участие.");
        String strWantToDo = "Вы указали, что хотите участвовать в следующей деятельности:\r\n";
        for (String item : arguments.getStringArrayList("wantToDo")) {
            strWantToDo += item + "\r\n";
        }
        ((TextView) findViewById(R.id.twWantToDo)).setText(strWantToDo);

        String userMessage = "";
        if (arguments.getString("emailSubscribe") != null) {
            ((TextView) findViewById(R.id.twMail)).setText("Вы подписались на информационную рассылку!");
        }
    }
}