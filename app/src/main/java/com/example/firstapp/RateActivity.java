package com.example.firstapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RateActivity extends AppCompatActivity {
    EditText rmb;
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rmb =(EditText) findViewById(R.id.rmb);
        show=(TextView) findViewById(R.id.showOut);
    }
    public  void OnClick(View btn){
        String str =rmb.getText().toString();
        float r=0;
        if(str.length()>0){
            r=Float.parseFloat(str);
        }else{
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }

        if(btn.getId()==R.id.btn_dollar){
            float val =r*(1/6.7f);
            show.setText(String.valueOf(val));
        }else if(btn.getId()==R.id.btn_euro){
            float val=r*(1/11.0f);
            show.setText(String.valueOf(val));
        }else{
            float val=r*500;
            show.setText(String.valueOf(val));
        }
    }
}
