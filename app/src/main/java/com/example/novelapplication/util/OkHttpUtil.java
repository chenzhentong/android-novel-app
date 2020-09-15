package com.example.novelapplication.util;


import android.annotation.SuppressLint;
import android.net.wifi.WifiManager;
import android.util.Log;


import java.net.InetAddress;
import java.net.NetworkInterface;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {



    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
    /**
     * 网络连接是否正常
     *
     * @return true:有网络    false:无网络
     */
    public static  void httpGet(String address, Callback callback) {

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(address).get()
                .build();
        client.newCall(request).enqueue(callback);

    }
    public static  void httpPost(String address,  String jsonString,Callback callback) {
        //MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , jsonString);
        Request request=new Request.Builder()
                .url(address).post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);

    }



}
