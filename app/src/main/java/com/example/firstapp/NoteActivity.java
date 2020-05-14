package com.example.firstapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NoteActivity extends AppCompatActivity implements Runnable, AdapterView.OnItemClickListener {

    private String updateDate = "";
    String data[] = {"wait..."};
    EditText key;
    Handler handler;
    ListView show1;
    TextView show2;
    private final String TAG = "Rate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        final ListView listView=(ListView) findViewById(R.id.show1);
      key= (EditText) findViewById(R.id.key);
        show2= (TextView) findViewById(R.id.show2);
        show1= (ListView) findViewById(R.id.show1);
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        updateDate=sharedPreferences.getString("update_date","");
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr=sdf.format(today);






        Log.i(TAG, "OnCreate: sp updateDate=" + updateDate);
        Log.i(TAG, "OnCreate: sp todayStr=" + todayStr);


        //判断时间
        if(!todayStr.equals(updateDate)){
            Log.i(TAG,"onCreate:需要更新");
            //开启子线程
            Thread t =new Thread();
            t.start();
        }else{
            Log.i(TAG,"onCreate:不需要更新");
        }

        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        Thread t=new Thread(this);
        t.start();
        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7) {
                    List<String> list2=(List<String>)msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(NoteActivity.this,android.R.layout.simple_list_item_1,list2);
                    listView.setAdapter(adapter);
                    Log.i(TAG,"onCreate:dg更新");
                    SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("update_date",todayStr);
                    editor.commit();
                    Toast.makeText(NoteActivity.this, "通知已更新", Toast.LENGTH_SHORT).show();
                }

                super.handleMessage(msg);
            }
        };
        listView.setOnItemClickListener(this);



    }
        public void OnClick(View btn) {
            String str = key.getText().toString();


            if (str.length()>0) {


            } else {
                Toast.makeText(this, "找不到匹配标题", Toast.LENGTH_SHORT).show();

            }}


            private void openConfig() {
                Intent config = new Intent(this, RateListActivity.class);

                startActivityForResult(config, 1);
            }





    @Override
           public void run() {
            List<String> retList =new ArrayList<String>();
            Document doc = null;
        try {
            Thread.sleep(1000);
            doc = Jsoup.connect("https://it.swufe.edu.cn/index/tzgg.htm").get();
            Elements tables = doc.getElementsByTag("li");
            for (int i = 65; i < 85; i ++ ) {
                Elements tds = tables.get(i).getElementsByTag("span");
                Element td1=tds.get(0);
                String str1 = td1.text();
                Log.i(TAG, "run: text=" + td1.text() );
                retList.add(str1 );
//
            } }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //打开新的页面，传入参数
        Intent web=new Intent(Intent.ACTION_VIEW, Uri.parse("https://it.swufe.edu.cn/info/1093/6777.htm\n"));
        startActivityForResult(web, 1);

    }
}
