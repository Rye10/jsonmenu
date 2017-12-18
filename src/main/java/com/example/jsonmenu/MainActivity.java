package com.example.jsonmenu;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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


public class MainActivity extends AppCompatActivity {
    private static final String PATH = "http://apis.juhe.cn/cook/query?key=fbbcc01acef6e728583a2c522a410346&menu=%E8%A5%BF%E7%BA%A2%E6%9F%BF&rn=10&pn=3";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lv);
        getMenu();

    }

    private void getMenu() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(PATH);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    if (connection.getResponseCode() == 200) {
                        InputStream in = connection.getInputStream();
                        InputStreamReader isr = new InputStreamReader(in, "utf-8");
                        String result = "";
                        String line = "";
                        BufferedReader br = new BufferedReader(isr);
                        while ((line = br.readLine()) != null) {
                            result += line;
                        }

                        List list = new ArrayList();
                        JSONObject jsonObject;
                        jsonObject = new JSONObject(result);
                        JSONObject res = jsonObject.getJSONObject("result");
                        JSONArray data = res.getJSONArray("data");
                    /*       jsonObject = data.getJSONObject(0);
                        String title = jsonObject.getString("title");
                        list.add(title);
                         JSONArray steps = jsonObject.getJSONArray("steps");
                    for (int i = 0; i < steps.length(); i++) {
                            jsonObject = steps.getJSONObject(i);
                            String step = jsonObject.getString("step");
                            list.add(step);
                        }
                        jsonObject = data.getJSONObject(1);
                        String title1 = jsonObject.getString("title");
                        list.add(title1);
                        JSONArray steps1 = jsonObject.getJSONArray("steps");
                        for (int i = 0; i < steps1.length(); i++) {
                            jsonObject = steps1.getJSONObject(i);
                            String step1 = jsonObject.getString("step");
                            list.add(step1);
                        }
                        jsonObject = data.getJSONObject(2);
                        String title2 = jsonObject.getString("title");
                        list.add(title2);
                        JSONArray steps2 = jsonObject.getJSONArray("steps");
                        for (int i = 0; i < steps2.length(); i++) {
                            jsonObject = steps2.getJSONObject(i);
                            String step2 = jsonObject.getString("step");
                            list.add(step2);
                        }      */
                       for (int i = 0; i < data.length(); i++) {
                           jsonObject = data.getJSONObject(i);
                           String title=jsonObject.getString("title");
                           list.add(title);
//                           JSONArray steps = jsonObject.getJSONArray("steps");
//                          for (int j = 0; j < steps.length(); j++) {
//                               jsonObject = steps.getJSONObject(j);                                                //这里的值是j不是i
//                              String step = jsonObject.getString("step");
//                              list.add(step);
//                          }
                     }

                        Message msg = new Message();
                        msg.obj = list;
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

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            final List data = (List) msg.obj;
            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, data);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0:
                            startActivity(new Intent(MainActivity.this,MenuList.class));
                            break;
                        case 1:
                            startActivity(new Intent(MainActivity.this,Menu1.class));
                            break;
                        case 2:
                            startActivity(new Intent(MainActivity.this,Menu2.class));
                            break;
                        case 3:
                            startActivity(new Intent(MainActivity.this,Menu3.class));
                            break;
                        case 4:
                            startActivity(new Intent(MainActivity.this,Menu4.class));
                            break;
                        case 5:
                            startActivity(new Intent(MainActivity.this,Menu5.class));
                            break;
                        case 6:
                            startActivity(new Intent(MainActivity.this,Menu6.class));
                            break;
                        case 7:
                            startActivity(new Intent(MainActivity.this,Menu7.class));
                            break;
                        case 8:
                            startActivity(new Intent(MainActivity.this,Menu8.class));
                            break;
                        case 9:
                            startActivity(new Intent(MainActivity.this,Menu9.class));
                            break;
                            default:
                                break;
                    }

                }
            });
        }
    };
}

//点击list item进入这个菜的做法   把每个菜的做法都各自放入了一个activity中，点击item，根据itemclick事件的position判断点击了第几个item，如何显示该菜的菜单activity