package com.example.l3volunteering;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

public class TabsFragment extends Fragment implements View.OnClickListener{

    interface OnFragmentSendDataListener {
        void onSendData(String data);
    }

    private OnFragmentSendDataListener fragmentSendDataListener;
    String[] menuItems = { "ЛК", "О нас", "Блог"};


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);
        // добавляем для списка слушатель
        ((Button) view.findViewById(R.id.btnMyInfo)).setOnClickListener(this::onClick);
        ((Button) view.findViewById(R.id.btnAbout)).setOnClickListener(this::onClick);
        ((Button) view.findViewById(R.id.btnBlog)).setOnClickListener(this::onClick);
        return view;
    }

    @Override
    public void onClick(View v) {
        // получаем выбранный элемент
        String selectedItem = ((Button)v).getText().toString();
        // Посылаем данные Activity
        fragmentSendDataListener.onSendData(selectedItem);

    }
}
