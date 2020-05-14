package com.example.firstapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyList2Activity extends ListActivity implements Runnable, AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    Handler handler;
    private final String TAG = "mylist2";
    private ArrayList<HashMap<String, String>> listItems;//存放文字，图片信息
    private SimpleAdapter listItemAdapter;//适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        this.setListAdapter(listItemAdapter);

        //MyAdapter myAdapter =new MyAdapter(this,R.layout.list_item,listItems);
        //this.setListAdapter(myAdapter);
        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    List<HashMap<String,String>>list2 = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(MyList2Activity.this, list2,//listTtems数据源
                            R.layout.list_item,//ListItem的XML布局实现
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail}
                            );
                    setListAdapter(listItemAdapter);
                }

                super.handleMessage(msg);
            }
        };
        //getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
          //  @Override
           // public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

          //  }
        //});
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);

    }



    private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate:  " + i);//标题文字
            map.put("ItemDetail", "detail" + i);//详细描述
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this, listItems,//listTtems数据源
                R.layout.list_item,//ListItem的XML布局实现
                new String[]{"ItemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle, R.id.itemDetail}
        );
    }


    @Override
    public void run() {
//获取网络数据，放入list带回到到主线程中
        List<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();
        Document doc = null;
        try {
            Thread.sleep(1000);
            doc = Jsoup.connect("http://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG, "run:" + doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table2 = tables.get(1);
            //获取TD中的元素
            Elements tds = table2.getElementsByTag("td");
            for (int i = 0; i < tds.size(); i += 8) {
                Element td1 = tds.get(i);
                Element td2 = tds.get(i + 5);

                String str1 = td1.text();
                String val = td2.text();
                Log.i(TAG, "run: text=" + td1.text() + "==>" + td2.text());

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemTitle", str1);
                map.put("ItemDetail", val);
                retList.add(map);

//
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i(TAG,"onItemClick: parent="+parent);
        Log.i(TAG,"onItemClick: view="+view);
        Log.i(TAG,"onItemClick: position="+position);
        Log.i(TAG,"onItemClick: id="+id);
        HashMap<String,String> map=(HashMap<String,String>) getListView().getItemAtPosition(position);
        String titleStr=map.get("ItemTitle");
        String detailStr =map.get("ItemDetail");
        Log.i(TAG,"onItemClick: titleStr="+titleStr);
        Log.i(TAG,"onItemClick: detailStr="+detailStr);

        TextView title=(TextView)view.findViewById(R.id.itemTitle);
        TextView detail=(TextView)view.findViewById(R.id.itemDetail);
        String title2=String.valueOf(title.getText());
        String detail2=String.valueOf(detail.getText());
        Log.i(TAG,"onItemClick: title2="+title2);
        Log.i(TAG,"onItemClick: detail2="+detail2);

        //打开新的页面，传入参数
        Intent rateCalc=new Intent(this,RateCalActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("rate",Float.parseFloat(detailStr));
        startActivity(rateCalc);


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,"OnItemLongClick:长按列表项position"+position);
        //return false;//短按事件依然可以实现


        return true;

    }
}

