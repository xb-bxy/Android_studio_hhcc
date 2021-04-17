package com.keai.flower.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.keai.flower.MainActivity;
import com.keai.flower.R;

public class SettingsActivity extends BaseActivity {
    private Button btn_save;
    private TextView editTextTextPersonName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btn_save = findViewById(R.id.btn_save);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextPersonName.setText(getStringKey("api_key"));
        btn_save.setOnClickListener(v -> {
            saveStringKey("api_key",editTextTextPersonName.getText().toString());
            navigateTo(MainActivity.class);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateTo(MainActivity.class);
    }
}