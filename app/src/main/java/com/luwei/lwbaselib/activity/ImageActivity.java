package com.luwei.lwbaselib.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.luwei.lwbaselib.R;

/**
 * 显示图片框架使用的 activity
 */
public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        setTitle("图片框架使用示例");
    }
}
