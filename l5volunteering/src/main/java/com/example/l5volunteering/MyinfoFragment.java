package com.example.l5volunteering;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MyinfoFragment extends Fragment {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String volunteerId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myinfo, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            volunteerId = bundle.getString("vouId");
        }

        LinearLayout llContent = getClearLayout();
        dbHelper = new DatabaseHelper(getView().getContext());
        String email = "";
        TextView twFragment;
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM volunteers WHERE rowid = "+volunteerId+";", null);
        if(cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String surname = cursor.getString(cursor.getColumnIndex("surname"));
            email = cursor.getString(cursor.getColumnIndex("email"));

            addTextViewToLayout(llContent, name + " " + surname +
                    ", добро пожаловать в личный кабинет волонтера. Здесь будет отображаться информация о вас, вашей активности" +
                    "и мероприятиях, в которых вы можете принять участие.\r\n");
        }

        String strWantToDo = "Вы указали, что хотите участвовать в следующей деятельности:\r\n";
        cursor = db.rawQuery("SELECT * FROM volunteers_wantToDo INNER JOIN wantToDo on wantToDo.rowid = volunteers_wantToDo.activityId WHERE volunteerId = "+volunteerId+";", null);

        //cursor.moveToFirst();
        while (cursor.moveToNext()){
            strWantToDo += "* " + cursor.getString(cursor.getColumnIndex("activity")) + "\r\n";
        }
        cursor.close();
        addTextViewToLayout(llContent, strWantToDo);
        if(email!=null){
            addTextViewToLayout(llContent,"Вы подписались на информационную рассылку!");
        }
    }

    private LinearLayout getClearLayout(){
        LinearLayout llContent = (LinearLayout)getView().findViewById(R.id.clContent);
        llContent.removeAllViews();
        return llContent;
    }

    private void addTextViewToLayout(LinearLayout layout, String twText){
        TextView textView = new TextView(this.getActivity());
        textView.setText(twText);
        layout.addView(textView);
    }
}