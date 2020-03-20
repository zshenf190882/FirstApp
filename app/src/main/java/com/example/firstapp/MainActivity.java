package com.example.firstapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView out;
    EditText inp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        out = (TextView) findViewById(R.id.showText);

       inp = (EditText) findViewById(R.id.inpText);
        String str = inp.getText().toString();

        Button btn= (Button)findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("email","onClick called...");
                String str = inp.getText().toString();
                out.setText("Hello "+str);

            }
        });


    }

    @Override
    public void onClick(View v) {
        Log.i("clink","onClink......");

        String str = inp.getText().toString();
        out.setText("Hello "+str);
    }
    public void btnClick(View btn){
        Log.i("click","btnClick called....");
    }
}

