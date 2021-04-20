package com.example.l3volunteering;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
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

    // обновление текстового поля
    public void setContentMyInfo(String volunteerId) {

        LinearLayout llContent = getClearLayout();
        dbHelper = new DatabaseHelper(getView().getContext());
        String email = "";
        TextView twFragment;
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM Volunteers WHERE rowid = "+volunteerId+";", null);
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

        while (cursor.moveToNext()){
            strWantToDo += "* " + cursor.getString(cursor.getColumnIndex("activity")) + "\r\n";
        }
        addTextViewToLayout(llContent, strWantToDo);
        if(email!=null){
            addTextViewToLayout(llContent,"Вы подписались на информационную рассылку!");
        }
    }

    public void setContentAbout() {
        LinearLayout llContent = getClearLayout();
        addTextViewToLayout(llContent,"ОТКРЫТ БЛОК 'О НАС'");
    }

    public void setContentNews() {
        LinearLayout llContent = getClearLayout();
        addTextViewToLayout(llContent,"ОТКРЫТ БЛОК 'НОВОСТИ");
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

//import android.content.Context;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//public class MyinfoFragment extends Fragment {
////
////    interface OnFragmentSendDataListener {
////        void onSendData(String data);
////    }
////
////    private OnFragmentSendDataListener fragmentSendDataListener;
////    String[] countries = { "Бразилия", "Аргентина", "Колумбия", "Чили", "Уругвай"};
////
////
////    @Override
////    public void onAttach(Context context) {
////        super.onAttach(context);
////        try {
////            fragmentSendDataListener = (OnFragmentSendDataListener) context;
////        } catch (ClassCastException e) {
////            throw new ClassCastException(context.toString()
////                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
////        }
////    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        //return super.onCreateView(inflater, container, savedInstanceState)
//        Context context = getActivity().getApplicationContext();
//        LinearLayout layout = new LinearLayout(context);
//        layout.setBackgroundColor(Color.BLUE);
//        TextView text = new TextView(context);
//        text.setText("ОБЛАСЬ ФРАГМЕНТА");
//        layout.addView(text);
//        return layout;
//        //View rootView = inflater.inflate(R.layout.fragment_myinfo, container, false);
//        //return rootView;
//    }
//}
