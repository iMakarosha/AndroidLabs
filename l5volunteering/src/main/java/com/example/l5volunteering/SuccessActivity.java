package com.example.l5volunteering;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        dbHelper = new DatabaseHelper(getApplicationContext());

        Bundle arguments = getIntent().getExtras();
        String volunteerId = arguments.getString("volunteerId");
        String email = "";
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM Volunteers WHERE rowid = " + volunteerId + ";", null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String surname = cursor.getString(cursor.getColumnIndex("surname"));
            email = cursor.getString(cursor.getColumnIndex("email"));

            ((TextView) findViewById(R.id.twHello)).setText(String.format("%s %s, добро пожаловать в личный кабинет волонтера. Здесь будет отображаться информация о вас, вашей активностии мероприятиях, в которых вы можете принять участие.", name, surname));
        }

        String strWantToDo = "Вы указали, что хотите участвовать в следующей деятельности:\r\n";
        cursor = db.rawQuery("SELECT * FROM volunteers_wantToDo INNER JOIN wantToDo on wantToDo.rowid = volunteers_wantToDo.activityId WHERE volunteerId = " + volunteerId + ";", null);

        while (cursor.moveToNext()) {
            strWantToDo += "* " + cursor.getString(cursor.getColumnIndex("activity")) + "\r\n";
        }
        ((TextView) findViewById(R.id.twWantToDo)).setText(strWantToDo);
        if (email != null) {
            ((TextView) findViewById(R.id.twMail)).setText("Вы подписались на информационную рассылку!");
        }
    }
}