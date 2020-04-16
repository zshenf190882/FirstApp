package com.example.firstapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class RateActivity extends AppCompatActivity implements Runnable {
    EditText rmb;
    TextView show;
    Handler handler;

    private final String TAG = "Rate";
    private float dollarRate = 0.1f;
    private float euroRate = 0.2f;
    private float wonRate = 0.3f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rmb = (EditText) findViewById(R.id.rmb);
        show = (TextView) findViewById(R.id.showOut);
        //获取sp里保存的数据..
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        dollarRate = sharedPreferences.getFloat("dollar_rate", 0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate", 0.0f);
        wonRate = sharedPreferences.getFloat("won_rate", 0.0f);

        Log.i(TAG, "OnCreate: sp dollarRate=" + dollarRate);
        Log.i(TAG, "OnCreate: sp euroRate=" + euroRate);
        Log.i(TAG, "OnCreate: sp wonRate=" + wonRate);

        //开启子线程
        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 5) {
                    Bundle bdl = (Bundle) msg.obj;
                   dollarRate=bdl.getFloat("dollar-rate");
                    euroRate=bdl.getFloat("euro-rate");
                    wonRate=bdl.getFloat("won-rate");
                    Log.i(TAG,"handleMessage: dollarRate:"+dollarRate);
                    Log.i(TAG,"handleMessage: euroRate:"+euroRate);
                    Log.i(TAG,"handleMessage: wonRate:"+wonRate);

                    Toast.makeText(RateActivity.this,"汇率已更新",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };

    }


    public void OnClick(View btn) {
        String str = rmb.getText().toString();
        float r = 0;
        if (str.length() > 0) {
            r = Float.parseFloat(str);
        } else {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT);
        }
        Log.i(TAG, "Onclick: r=" + r);
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
        if (btn.getId() == R.id.btn_dollar) {
            show.setText(String.format("%.2f", r * dollarRate));
        } else if (btn.getId() == R.id.btn_euro) {
            show.setText(String.format("%.2f", r * euroRate));
        } else
            show.setText(String.format("%.2f", r * wonRate));
    }


    public void openOne(View btn) {
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
//mm
        startActivityForResult(config, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_set) ;
        openConfig();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == 2) {
//
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar", 0.1f);
            euroRate = bundle.getFloat("key_euro", 0.1f);
            wonRate = bundle.getFloat("key_won", 0.1f);
            Log.i(TAG, "onActivityResult: dollarRate=" + dollarRate);
            Log.i(TAG, "onActivityResult: euroRate=" + euroRate);
            Log.i(TAG, "onActivityResult: wonRate=" + wonRate);

            //将新设置的汇率写到SP中
            SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("dollar_rate", dollarRate);
            editor.putFloat("euro_rate", euroRate);
            editor.putFloat("won_rate", wonRate);
            editor.commit();
            Log.i(TAG, "onActivityResult:数据已保存到SharedPreferences.");


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
        Log.i(TAG, "run: run()......");
//        for (int i = 1; i < 3; i++) {
//            Log.i(TAG, "run: i=" + i);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
              //用于保存获取的汇率
            Bundle bundle=new Bundle();


        //获取网络数据
//        URL url = null;
//        try {
//            url = new URL("http://www.usd-cny.com/bankofchina.htm");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();
//
//            String html=inputStream2String(in);
//            Log.i(TAG,"run: html="+ html);
//            Document doc =Jsoup.parse(html);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Document doc =null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            //doc =Jsoup.parse(html);
            Log.i(TAG, "run:" + doc.title());
//            Elements newsHeadlines = doc.select("Nmp.itn b a");
//            for (Element headline : newsHeadlines) {
//                Log.i(TAG, "%\n\t%s" + headline.attr("title") + headline.absUrl("href"));
//            }
            Elements tds =doc.getElementsByTag("td");

//            for (Element table:tables){
//                Log.i(TAG, "run: table["+i+"] =" + table);
//                i++;
//            }
           // Element table6=tds.get(5);
            //Log.i(TAG, "run: table6=" + table6);
            //获取TD中的元素
            //Elements tds =table6.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=6){
                Element td1=tds.get(i);
                Element td2=tds.get(i+5);
                Log.i(TAG,"run: text="+td1.text() +"==>"+td2.text());
                String str1=td1.text();
                String val=td2.text();
                if("美元".equals(str1)){
                    bundle.putFloat("dollar-rate",100f/Float.parseFloat(val));
                }else if("欧元".equals(str1)){
                    bundle.putFloat("euro-rate",100f/Float.parseFloat(val));
                }else if("韩元".equals(str1)){
                    bundle.putFloat("won-rate",100f/Float.parseFloat(val));
                }
            }
//            for(Element td :tds){
//                Log.i(TAG,"run: td="+td);
//                Log.i(TAG,"run: text="+td.text());
//                Log.i(TAG,"run: html="+td.html());
//            }
        }

        catch(IOException e){
            e.printStackTrace();
            }

        //bundle中保存所获取的汇率

        //获取MAG对象，用于返回主线程
        Message msg = handler.obtainMessage(5);
        //msg.what = 5;
       // msg.obj = "Hello from run()";
        msg.obj=bundle;
        handler.sendMessage(msg);
    }
    private String inputStream2String(InputStream inputStream) throws  IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        for( ; ;){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);

        }return out.toString();
    }
}

