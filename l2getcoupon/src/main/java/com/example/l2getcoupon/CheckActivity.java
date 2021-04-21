package com.example.l2getcoupon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Bundle arguments = getIntent().getExtras();
        String userMessage = "На вопрос о наличии велосипеда вы ответили: ";
        userMessage += arguments.getString("hasBike");
        if(arguments.getBoolean("hasOrWantBike")){
            userMessage += ".\r\nВы рассказали, что предпочитаете велосипед типа "
                    + arguments.getString("myType");
            if(arguments.getString("expect")!=null){
                userMessage += ", и он должен обладать свойствами: "
                        + arguments.getString("expect");
            }
            userMessage += "\r\n\r\nВ нашем интернет-магазине вы сможете найти широкий ассортимент " +
                    "велосипедов и комплектующих.";
        }
        else {
            userMessage += "\r\nВы рассказали, что предпочитаете " + arguments.getString("transport")
                    +", и это замечательно!\r\n\r\n";
            userMessage += "В нашем интернет-магазине вы сможете найти широкий ассортимент " +
                    "средств передвижения и комплектующих.";
        }

        if(arguments.getString("mailSender")!=null){
            userMessage += "\r\nВы также подписались на информационную рассылку!";
        }

        TextView twText = (TextView) findViewById(R.id.textView4);
        twText.setText(userMessage);
    }

    public void sendForm(View view){
        Intent intent = new Intent(this, SuccessActivity.class);
        startActivity(intent);
    }

    public void backPage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}