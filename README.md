# jsonmenu
点击listview的菜单出现菜式的做法
=====

知识点：httpURLconnection使用，json解析，handler使用
-----

httpURLconnection步骤：
-------

1、新建子线程更新UI，主线程无法更新UI，会出现线程阻塞或者ANR（application not response）情况，new Thread（new Runnable（））<br>
2、Url网络地址 URL url=new URL（PATH）；<br>
3、打开连接 HttpURLConnection connection=(HttpURLConnection)url.openconnection();<br>
4、设置请求方式和超时时间  connection.setRequestMethod("GET");    connection.setConnectTimeout(5000);<br>
5、判断是否请求是否成功  if (connection.getResponseCode() == 200){}<br>
6、获取字节流 InputStream in = connection.getInputStream();<br>
7、转换为字符流  InputStreamReader isr = new InputStreamReader(in, "utf-8");<br>
InputStreamReader ： 是字节流与字符流之间的桥梁，能将字节流输出为字符流，并且能为字节流指定字符集，可输出一个个的字符；<br>
8、缓存 BufferedReader br = new BufferedReader(isr);<br>
提供通用的缓冲方式文本读取，readLine读取一个文本行， 从字符输入流中读取文本，缓冲各个字符，从而提供字符、数组和行的高效读取。<br>
9、读取字符流  当readLine读取不为null时，字符写入line中添加到result里，读取数据，为null说明数据读取完成  while ((line = br.readLine()) != null) {result += line;}<br>

Json解析：
---------

将获取的字符流变成json数据  JSONObject jsonObject= new JSONObject(result);<br>
获取其中需要的json数据    JSONObject res=jsonObject.getJSONObject(res);<br>
json数组获取    JSONArray data = res.getJSONArray("data");<br>
要获取json数组中的字符串string  先遍历数组 for循环，得到每一个jsonobject<br>
将jsonobject变成string    String title=jsonObject.getString("title");<br>

handler使用：
----------

新建handler 用来异步处理，传送message  用于接受子线程发送的数据, 并用此数据配合主线程更新UI。<br>
 Handler handler = new Handler() {<br>
        public void handleMessage(Message msg) {<br>
            final List data = (List) msg.obj;                  在子线程中数据是List类型的，传递出来要强转为List类型数据<br>
            }<br>
在子线程Thread中把数据传递出来<br>
Message msg=new Message();<br>
msg.obj=list;<br>
handler.sendmessage(msg);<br>
