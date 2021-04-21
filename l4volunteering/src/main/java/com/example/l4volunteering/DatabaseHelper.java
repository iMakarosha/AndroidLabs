package com.example.l4volunteering;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "volunteering.db";
    private static final int SCHEMA = 1;//версия БД
    static final String TABLE1 = "volunteers";
    static final String TABLE2 = "emailSubscription";
    static final String TABLE3 = "wantToDo";
    static final String TABLE4 = "volunteers_wantToDo";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE1+" (email TEXT, name TEXT, surname TEXT, nickname TEXT, password TEXT, city TEXT, country TEXT, birthday TEXT, about TEXT, activity INT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE2+" (email TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE3+" (activity TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE4+" (volunteerId INT, activityId INT)");

        db.execSQL("INSERT INTO "+TABLE3+" (activity) VALUES ('Оказание помощи пожилым людям и ветеранам');");
        db.execSQL("INSERT INTO "+TABLE3+" (activity) VALUES ('Помощь животным, работа в приютах для животных');");
        db.execSQL("INSERT INTO "+TABLE3+" (activity) VALUES ('Безвозмездное донорство');");
        db.execSQL("INSERT INTO "+TABLE3+" (activity) VALUES ('Благотворительность');");
        db.execSQL("INSERT INTO "+TABLE3+" (activity) VALUES ('Поиск людей');");
        db.execSQL("INSERT INTO "+TABLE3+" (activity) VALUES ('Поиск домашних животных');");
        db.execSQL("INSERT INTO "+TABLE3+" (activity) VALUES ('Сбор вещей (гуманитарной помощи)');");
        db.execSQL("INSERT INTO "+TABLE3+" (activity) VALUES ('Экологическое волонтерство');");
        db.execSQL("INSERT INTO "+TABLE3+" (activity) VALUES ('Работа в центрах и пунктах добровольчества');");
        db.execSQL("INSERT INTO "+TABLE3+" (activity) VALUES ('Организация досуга детей');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE4);
        onCreate(db);
    }
}
