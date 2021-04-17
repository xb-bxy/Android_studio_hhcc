package com.keai.flower.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keai.flower.MainActivity;
import com.keai.flower.R;

public class SettingsActivity extends BaseActivity {
    LinearLayout api_key,upDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        api_key = findViewById(R.id.api_key);
        upDate = findViewById(R.id.update);
        setListeners();
       // btn_save = findViewById(R.id.btn_save);
        //editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
       // editTextTextPersonName.setText(getStringKey("api_key"));
//        btn_save.setOnClickListener(v -> {
//            saveStringKey("api_key",editTextTextPersonName.getText().toString());
//            navigateTo(MainActivity.class);
//        });
    }
    private void setListeners(){
        OnClick onClick = new OnClick();
        api_key.setOnClickListener(onClick);
        upDate.setOnClickListener(onClick);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void apiKeyDialog() {
        final EditText et = new EditText(this);
        et.setText(getStringKey("api_key"));
        new AlertDialog.Builder(this).
                setTitle("输入Api_Key").
                setView(et).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveStringKey("api_key",et.getText().toString());
                    }
                }).setNegativeButton("取消", null).setCancelable(false).create().show();
    }
    @SuppressLint("SetTextI18n")
    private void apiUpDialog() {
        final EditText et = new EditText(this);
        et.setText(Integer.toString((int) getInt("update")));
        new AlertDialog.Builder(this).
                setTitle("输入自动更新时间").
                setView(et).
                setPositiveButton("确定", (dialog, which) -> {
                    if (et.getText() != null){
                        saveInt("update",Integer.parseInt(et.getText().toString()));
                    }
                }).setNegativeButton("取消", null).setCancelable(false).create().show();
    }
    private class OnClick implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.api_key:
                    apiKeyDialog();
                    break;
                case R.id.update:
                    apiUpDialog();
                    break;
            }
        }
    }
}