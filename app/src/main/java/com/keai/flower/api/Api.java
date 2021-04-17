package com.keai.flower.api;

import android.util.Log;

import com.keai.flower.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Api {
    private static OkHttpClient client;
    private static String requestUrl;
    private static HashMap<String, Object> mParams;
    public static Api api = new Api();
    public Api(){}

    public static Api config(String url, HashMap<String,Object> params){
        client = new OkHttpClient();
        requestUrl = url;
        mParams = params;
        return api;
    }

    public void getRequest(final TtitCallback callback){
        String url = getAppendUrl(requestUrl,mParams);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage() );
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                callback.onSuccess(result);
            }
        });
    }
    private String getAppendUrl(String url, Map<String, Object> map){
        if (map != null && !map.isEmpty()){
            StringBuffer buffer = new StringBuffer();
            Iterator<Map.Entry<String,Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> entry = iterator.next();
                if (StringUtils.isEmpty(buffer.toString())){
                    buffer.append("?");
                }else {
                    buffer.append("&");
                }
                buffer.append(entry.getKey()).append("=").append(entry.getValue());
            }
            url += buffer.toString();
        }
        return url;
    }
}
