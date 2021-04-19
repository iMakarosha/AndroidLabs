package com.example.l3volunteering;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class InterestsActivity extends AppCompatActivity {

    Volunteer volunteer;
    Drawable originalDrawable;
    boolean emailSubscribe;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        dbHelper = new DatabaseHelper(getApplicationContext());

        originalDrawable = ((EditText) findViewById(R.id.etNickname)).getBackground();

        Bundle arguments = getIntent().getExtras();
        volunteer = (Volunteer) arguments.get(Volunteer.class.getSimpleName());
        ((TextView)findViewById(R.id.textView8)).setText(volunteer.getName() + ", расскажите о своих интересах и целях. " +
                "Изменить данные можно будет в личном кабинете.");

        ((EditText)findViewById(R.id.etNickname)).setText(volunteer.getName());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendForm(View view){
        //валидация
        boolean isValid = true;
        if(((EditText)findViewById(R.id.etNickname)).getText().toString().isEmpty()){
            setNotValidBack(R.id.etNickname);
            isValid = false;
        }
        if(((EditText)findViewById(R.id.etAbout)).getText().toString().isEmpty()){
            setNotValidBack(R.id.etAbout);
            isValid = false;
        }

        if(isValid){
            volunteer.setNickname(((EditText)findViewById(R.id.etNickname)).getText().toString());
            volunteer.setAbout(((EditText)findViewById(R.id.etAbout)).getText().toString());

            RadioGroup rg = (RadioGroup)findViewById(R.id.rbGroupActivity);
            RadioButton rb = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
            String rbName = getResources().getResourceEntryName(rb.getId());
            int activityId = Integer.parseInt(rbName.substring(rbName.length()-1));
            volunteer.setActivity(activityId);

            LinearLayout llWantToDo = (LinearLayout) findViewById(R.id.llWantToDo);
            //ArrayList<String> wantToDo = new ArrayList<>();
            ArrayList<Integer> wantToDoInt = new ArrayList<>();
            int wantToDoCount = llWantToDo.getChildCount();
            for(int i = 1; i<wantToDoCount; i++){
                if(((CheckBox)llWantToDo.getChildAt(i)).isChecked()) {
                    //wantToDo.add(((CheckBox) llWantToDo.getChildAt(i)).getText().toString());
                    wantToDoInt.add(i-1);
                }
            }

            //БД
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT rowid FROM Volunteers WHERE email like '" + volunteer.getEmail() + "';", null);

            if(cursor.getCount() == 0) {
                String userValues = String.join("', '", "'" + volunteer.getEmail(), volunteer.getName(), volunteer.getSurname(), volunteer.getNickname(),
                        volunteer.getPassword(), volunteer.getCity(), volunteer.getCountry(), volunteer.getBirthday() , volunteer.getAbout()+ "'");
                Log.d("TAG", "userValues: " + userValues);
                db.execSQL("INSERT INTO Volunteers(email, name, surname, nickname, password, city, country, birthday, about, activity) " +
                        "VALUES (" + userValues + ", " + Integer.toString(volunteer.getActivity()) + ");");
                cursor = db.rawQuery("SELECT rowid FROM Volunteers ORDER BY rowid DESC LIMIT 1;", null);

                if (emailSubscribe) {
                    db.execSQL("INSERT INTO EmailSendering (email) VALUES (" + volunteer.getEmail() + ");");
                }
                if(cursor.moveToFirst()) {

                    String volunteerId = cursor.getString(cursor.getColumnIndex("rowid"));
                    for (Integer item: wantToDoInt) {
                        db.execSQL("INSERT INTO volunteers_wantToDo (volunteerId, activityId) VALUES("+volunteerId+", "+item+")");
                    }

                    //обработка активностей
                    Intent intent = new Intent(this, SuccessActivity.class);
                    intent.putExtra("volunteerId", cursor.getString(cursor.getColumnIndex("rowid")));
                    //intent.putStringArrayListExtra("wantToDo", wantToDo);

                    startActivity(intent);
                }
            }
            else{
                Toast toast = Toast.makeText(this, "Данный email уже используется!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 30, 160);
                toast.show();
            }
        }
    }

    //дубль
    public void setNotValidBack(int elId){
        ((EditText)findViewById(elId)).setBackgroundResource(R.drawable.border);
    }

    public void setValidBack(int elId){
        ((EditText)findViewById(elId)).setBackground(originalDrawable);
    }
}