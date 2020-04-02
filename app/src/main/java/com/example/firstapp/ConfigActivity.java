package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {
    public final  String TAG="ConfigActivity";
    EditText dollarText;
    EditText euroText;
    EditText wonText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Intent intent =getIntent();
        float dollar2=intent.getFloatExtra("dollar_rate_key",0.0f);
        float euro2=intent.getFloatExtra("euro_rate_key",0.0f);
        float won2=intent.getFloatExtra("won_rate_key",0.0f);
    Log.i(TAG,"OnCreate: dollar2="+ dollar2);
        Log.i(TAG,"OnCreate: euro2="+ euro2);
        Log.i(TAG,"OnCreate: won2="+ won2);
        dollarText=(EditText)findViewById(R.id.dollar_rate);
        euroText=(EditText)findViewById(R.id.euro_rate);
        wonText=(EditText)findViewById(R.id.won_rate);
        //显示数据到控件
        dollarText.setText(String.valueOf(dollar2));
        euroText.setText(String.valueOf(euro2));
        wonText.setText(String.valueOf(won2));
    }
    public void save(View btn){
        Log.i("cfg","save: ");
    }
}
