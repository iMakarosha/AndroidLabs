package com.example.l3volunteering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    List<String> rCities = new ArrayList<>();
    List<String> uCities = new ArrayList<>();
    List<String> bCities= new ArrayList<>();
    ArrayAdapter<String> etCityAdapter;
    Drawable originalDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        originalDrawable = ((EditText) findViewById(R.id.etCountry)).getBackground();

        rCities = addListItems(rCities, mRCities);
        uCities = addListItems(uCities, mUCities);
        bCities = addListItems(bCities, mBCities);

        AutoCompleteTextView actwCountry = (AutoCompleteTextView) findViewById(R.id.etCountry);
        ArrayAdapter<String> etCountryAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arrays.asList(getResources().getStringArray(R.array.countries)));
        actwCountry.setAdapter(etCountryAdapter);
        actwCountry.setThreshold(1);


        AutoCompleteTextView actwCity = (AutoCompleteTextView) findViewById(R.id.etCity);
        etCityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rCities);
        actwCity.setAdapter(etCityAdapter);
        actwCity.setThreshold(1);

        ((EditText) findViewById(R.id.etCountry)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    etCityAdapter.clear();

                    switch ((((EditText) findViewById(R.id.etCountry)).getText().toString().toLowerCase())){
                        case "россия":
                            etCityAdapter.addAll(rCities);
                            break;
                        case "украина":
                            etCityAdapter.addAll(uCities);
                            break;
                        case "белоруссия":
                        case "беларусь":
                            etCityAdapter.addAll(bCities);
                            break;
                    }
                }
                else{
                    ((AutoCompleteTextView) findViewById(R.id.etCountry)).showDropDown();
                }
            }
        });

        ((TextView) findViewById(R.id.etEmail)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!checkEmail(((EditText) findViewById(R.id.etEmail)).getText().toString())){
                        setNotValidBack(R.id.etEmail);
                    }
                    else{
                        setValidBack(R.id.etEmail);
                    }
                }
            }
        });
    }

    public void setNotValidBack(int elId){
        ((EditText)findViewById(elId)).setBackgroundResource(R.drawable.border);
    }

    public void setValidBack(int elId){
        ((EditText)findViewById(elId)).setBackground(originalDrawable);
    }

    public void checkBaseForm(View view){
        //валидация
        boolean isValid = true;
        if(((EditText)findViewById(R.id.etEmail)).getText().toString().isEmpty()){
            setNotValidBack(R.id.etEmail);
            isValid = false;
        }
        else{
            setValidBack(R.id.etEmail);
        }
        if(((EditText)findViewById(R.id.etPassword)).getText().toString().isEmpty()){
            setNotValidBack(R.id.etPassword);
            isValid = false;
        }
        else{
            setValidBack(R.id.etPassword);
        }

        if(isValid){
            ((EditText)findViewById(R.id.etEmail)).setEnabled(false);
            ((EditText)findViewById(R.id.etPassword)).setEnabled(false);
            ((Button)findViewById(R.id.btnBaseSend)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.btnLogin)).setVisibility(View.GONE);

            ((LinearLayout)findViewById(R.id.llName)).setVisibility(View.VISIBLE);
            ((LinearLayout)findViewById(R.id.llSurname)).setVisibility(View.VISIBLE);
            ((LinearLayout)findViewById(R.id.llBirthday)).setVisibility(View.VISIBLE);
            ((LinearLayout)findViewById(R.id.llCountry)).setVisibility(View.VISIBLE);
            ((LinearLayout)findViewById(R.id.llCities)).setVisibility(View.VISIBLE);
            ((CheckBox)findViewById(R.id.checkBox)).setVisibility(View.VISIBLE);
            ((Button)findViewById(R.id.btnSend)).setVisibility(View.VISIBLE);
        }
    }

    public void checkForm(View view){
        //валидация
        //дописать сброс красной рамки на потере фокуса
        Date birthday = new Date();
        boolean isValid = true;
        if(((EditText)findViewById(R.id.etName)).getText().toString().isEmpty()){
            setNotValidBack(R.id.etName);
            isValid = false;
        }
        if(((EditText)findViewById(R.id.etSurname)).getText().toString().isEmpty()){
            setNotValidBack(R.id.etSurname);
            isValid = false;
        }
        if(((EditText)findViewById(R.id.etBirthday)).getText().toString().isEmpty()){
            setNotValidBack(R.id.etBirthday);
            isValid = false;
        }
        else{
            try{
                String birthdayStr = ((EditText)findViewById(R.id.etBirthday)).getText().toString();
                birthday = (new SimpleDateFormat("dd.MM.yyyy")).parse(birthdayStr);
            }
            catch (ParseException e){
                setNotValidBack(R.id.etBirthday);
                isValid = false;
            }
        }
        if(((EditText)findViewById(R.id.etCountry)).getText().toString().isEmpty()){
            setNotValidBack(R.id.etCountry);
            isValid = false;
        }
        if(((EditText)findViewById(R.id.etCity)).getText().toString().isEmpty()){
            setNotValidBack(R.id.etCity);
            isValid = false;
        }

        if(isValid) {
            String email = ((EditText)findViewById(R.id.etEmail)).getText().toString();
            String password = ((EditText)findViewById(R.id.etPassword)).getText().toString();
            String name = ((EditText)findViewById(R.id.etName)).getText().toString();
            String surname = ((EditText)findViewById(R.id.etSurname)).getText().toString();
            String country = ((EditText)findViewById(R.id.etCountry)).getText().toString();
            String city = ((EditText)findViewById(R.id.etCity)).getText().toString();
            Volunteer volunteer = new Volunteer(email, password, name, surname, birthday, country, city);
            Intent intent = new Intent(this, InterestsActivity.class);
            intent.putExtra(Volunteer.class.getSimpleName(), volunteer);

            intent.putExtra("mailSender", ((CheckBox) findViewById(R.id.checkBox)).isChecked());

            startActivity(intent);
        }
    }

    public List<String> addListItems(List<String> list, String[] items){
        for (String item: items) {
            list.add(item);
        }
        return  list;
    }

    public String[] mRCities = {
        "Москва", "Санкт-Петербург", "Астрахань", "Барнаул", "Владивосток", "Волгоград", "Воронеж",
        "Екатеринбург", "Ижевск", "Иркутстк", "Казань", "Кемерово", "Краснодар", "Красноярск", "Махачкала",
        "Нижний Новгород", "Новокузнецк", "Новосибирс", "Омск", "Оренбург", "Пермь", "Ростов-на-Дону",
        "Самара", "Саратов", "Тольятти", "Томск", "Тюмень", "Ульяновск", "Уфа", "Хабаровск", "Челябинск",
        "Ярославль"
    };

    public String[] mUCities = {
        "Киев", "Винница", "Днепропетровск", "Донецк", "Житомир", "Запорожье", "Кривой Рог", "Луганск",
        "Львов", "Мариуполь", "Николаев", "Одесса", "Полтва", "Суммы", "Харьков", "Херсон", "Хмельницкий",
        "Черкассы", "Чернигов"
    };

    public String[] mBCities = {
        "Минск", "Барановичи", "Бобруйск", "Борисов", "Брест", "Витебск", "Гомель", "Горки", "Гродно", "Жлобин",
        "Лида", "Могилев", "Мозырь", "Молодечно", "Новополоцк", "Орша", "Пинск", "Полоцк", "Солигорск"
    };

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}