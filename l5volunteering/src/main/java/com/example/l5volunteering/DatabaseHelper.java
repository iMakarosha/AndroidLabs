package com.example.l5volunteering;

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
    static final String TABLE5 = "news";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE1+" (email TEXT, name TEXT, surname TEXT, nickname TEXT, password TEXT, city TEXT, country TEXT, birthday TEXT, about TEXT, activity INT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE2+" (email TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE3+" (activity TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE4+" (volunteerId INT, activityId INT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE5+" (volunteerId INT, name TEXT, annotation TEXT, text TEXT, date TEXT)");

        db.execSQL("INSERT INTO "+TABLE1+" (email, name, surname, nickname, password, city, country, birthday, about, activity) " +
                "VALUES ('admin@admin.ru', 'Admin', 'Adminov', 'CoolAdmin482', '123', 'Москва', 'Россия', '01.01.1990', 'Самый лучший админ', 1);");

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

        db.execSQL("INSERT INTO "+TABLE5+" (volunteerId, name, annotation, text, date) VALUES " +
                "(1, 'News_1. Диалоговые окна', 'По умолчанию в Android уже определены два диалоговых окна - DatePickerDialog и TimePickerDialog', " +
                "'По умолчанию в Android уже определены два диалоговых окна " +
                "- DatePickerDialog и TimePickerDialog, которые позволяют выбрать дату и время.', '01.01.2020');");
        db.execSQL("INSERT INTO "+TABLE5+" (volunteerId, name, annotation, text, date) VALUES " +
                "(1, 'News_2. EditText', 'Элемент EditText является подклассом класса TextView', " +
                "'Он также представляет текстовое поле, но теперь уже с возможностью ввода и редактирования текста.', '12.03.2020');");
        db.execSQL("INSERT INTO "+TABLE5+" (volunteerId, name, annotation, text, date) VALUES " +
                "(1, 'News_3. Работа с сетью', " +
                "'WebView представляет простейший элемент для рендеринга html-кода, базирующийся на движке WebKit.', " +
                "'WebView представляет простейший элемент для рендеринга html-кода, базирующийся на движке WebKit.', '27.03.2020');");
        db.execSQL("INSERT INTO "+TABLE5+" (volunteerId, name, annotation, text, date) VALUES " +
                "(1, 'News_4. Работа с базами данных SQLite', " +
                "'В Android имеется встроенная поддержка одной из распространенных систем управления базами данных - SQLite.', " +
                "'В Android имеется встроенная поддержка одной из распространенных систем управления базами данных - SQLite. Для этого в пакете android.database.sqlite " +
                "определен набор классов, которые позволяют работать с базами данных SQLite. И каждое приложение может создать свою базу данных.', '29.03.2020');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE4);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE5);
        onCreate(db);
    }
}
