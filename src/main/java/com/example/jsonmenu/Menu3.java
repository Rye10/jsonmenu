package com.example.jsonmenu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rye on 2017/11/20.
 */

public class Menu3 extends AppCompatActivity {
    private ListView lv;
    private static final String PATH = "http://apis.juhe.cn/cook/query?key=fbbcc01acef6e728583a2c522a410346&menu=%E8%A5%BF%E7%BA%A2%E6%9F%BF&rn=10&pn=3";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_list);
        lv=findViewById(R.id.listview);
        getData();
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL(PATH);
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    if (connection.getResponseCode()==200){
                        List list=new ArrayList();
                        InputStream is=connection.getInputStream();
                        InputStreamReader isr=new InputStreamReader(is);
                        String line="";
                        String result="";
                        BufferedReader br=new BufferedReader(isr);
                        while ((line=br.readLine())!=null){
                            result+=line;
                        }
                        JSONObject jsonObject;
                        jsonObject=new JSONObject(result);
                        JSONObject res=jsonObject.getJSONObject("result");
                        JSONArray data=res.getJSONArray("data");
                        jsonObject=data.getJSONObject(3);
                        JSONArray steps=jsonObject.getJSONArray("steps");
                        for (int i=0;i<steps.length();i++){
                            jsonObject=steps.getJSONObject(i);
                            String step=jsonObject.getString("step");
                            list.add(step);
                        }
                        Message msg=new Message();
                        msg.obj=list;
                        handler.sendMessage(msg);
                    }
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            List data= (List) msg.obj;
            ArrayAdapter adapter=new ArrayAdapter(Menu3.this,android.R.layout.simple_list_item_1,data);
            lv.setAdapter(adapter);
        }
    };
}