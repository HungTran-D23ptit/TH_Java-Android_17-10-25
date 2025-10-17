package com.example.s_task;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView tvAbout = findViewById(R.id.tvAbout);
        tvAbout.setText("Ứng dụng S-Task\nNgười thực hiện: Hưng Trần\nỨng dụng quản lý công việc cá nhân với nhắc việc.");
    }
}
