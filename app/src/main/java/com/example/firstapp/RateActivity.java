package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RateActivity extends AppCompatActivity {
    EditText rmb;
    TextView show;
    private final String TAG="Rate";
    private float dollarRate=0.1f;
    private float euroRate=0.2f;
    private float wonRate=0.3f;

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
            Toast.makeText(this,"请输入内容",Toast.LENGTH_SHORT);
        }
        Log.i(TAG,"Onclick: r=" + r);
//bnvb
//            if(btn.getId()==R.id.btn_dollar){
//                float val =r*(1/6.7f);
//                show.setText(String.valueOf(val));
//            }else if(btn.getId()==R.id.btn_euro){
//                float val=r*(1/11.0f);
//                show.setText(String.valueOf(val));
//            }else{
//                float val=r*500;
//                show.setText(String.valueOf(val));
//        }
        if(btn.getId()==R.id.btn_dollar){
            show.setText(String.format("%.2f",r*dollarRate));
        }else if(btn.getId()==R.id.btn_euro){
            show.setText(String.format("%.2f",r*euroRate));
        }else
            show.setText(String.format("%.2f",r*wonRate));}



    public  void openOne(View btn){
        openConfig();

    }

    private void openConfig() {
        Intent config = new Intent(this, ConfigActivity.class);
        config.putExtra("dollar_rate_key", dollarRate);
        config.putExtra("euro_rate_key", euroRate);
        config.putExtra("won_rate_key", wonRate);

        Log.i(TAG, "openOne: dollarRate=" + dollarRate);
        Log.i(TAG, "openOne: euroRate=" + euroRate);
        Log.i(TAG, "openOne: wonRate=" + wonRate);

        startActivityForResult(config, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_set);
        openConfig();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==2){
//
            Bundle bundle =data.getExtras();
            dollarRate =bundle.getFloat("key_dollar",0.1f);
            euroRate=bundle.getFloat("key_euro",0.1f);
            wonRate=bundle.getFloat("key_won",0.1f);
            Log.i(TAG,"onActivityResult: dollarRate="+ dollarRate);
            Log.i(TAG,"onActivityResult: euroRate="+ euroRate);
            Log.i(TAG,"onActivityResult: wonRate="+ wonRate);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
