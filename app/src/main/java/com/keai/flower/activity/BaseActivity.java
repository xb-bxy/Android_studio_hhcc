package com.keai.flower.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    public void showToast(String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
    public void showToastSync(String msg){
        Looper.prepare();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    public void navigateTo(Class cls){
        Intent in = new Intent(mContext,cls);
        startActivityForResult(in,0);
    }
    protected void saveStringKey(String key,String val){
        SharedPreferences sp = mContext.getSharedPreferences("sp_key",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();

    }
    protected void saveInt(String key,int val){
        SharedPreferences sp = mContext.getSharedPreferences("sp_key",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, val);
        editor.commit();
    }
    protected String getStringKey(String key){
        SharedPreferences sp = mContext.getSharedPreferences("sp_key",MODE_PRIVATE);
        return sp.getString(key,"");
    }
    protected int getInt(String key){
        SharedPreferences sp = mContext.getSharedPreferences("sp_key",MODE_PRIVATE);
        return sp.getInt(key,6000000);
    }

}
