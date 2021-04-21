package com.example.l1calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView twResult;
    EditText etNumber;
    TextView twOperation;
    Double operand = null;
    String lastOperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twResult = (TextView) findViewById(R.id.twResult);
        twOperation = (TextView)findViewById(R.id.twOperation);
        etNumber = (EditText) findViewById(R.id.etNumber);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if(operand!=null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        twResult.setText(operand.toString());
        twOperation.setText(lastOperation);
    }

    public void onClearClick(View view){
        etNumber.setText("");
        twResult.setText("0");
        operand = null;
        lastOperation = "=";
    }

    public void onNumberClick(View view){
        Button btn = (Button)view;
        etNumber.append(btn.getText());
        if(lastOperation.equals("=") && operand!=null){
            operand = null;
        }
    }

    public void onOperationClick(View view){
        Button btn = (Button)view;
        String op = btn.getText().toString();
        String number = etNumber.getText().toString();

        if(number.length()>0){
            number = number.replace(",", ".");
            try{
                performOperation(Double.valueOf(number), op);
            }
            catch (NumberFormatException ex){
                etNumber.setText("");
            }
        }
        lastOperation = op;
        twOperation.setText(lastOperation);
    }

    private void performOperation(Double number, String operation){
        if(operand == null){
            operand = number;
        }
        else{
            if(lastOperation.equals("=")){
                lastOperation = operation;
            }
            switch (lastOperation){
                case "=":
                    operand = number;
                    break;
                case "/":
                    if(number == 0){
                        operand = 0.0;
                    }
                    else{
                        operand /= number;
                    }
                    break;
                case "*":
                    operand*= number;
                    break;
                case "+":
                    operand+=number;
                    break;
                case "-":
                    operand-=number;
                    break;
            }
        }
        twResult.setText(operand.toString().replace('.', ','));
        etNumber.setText("");
    }
}