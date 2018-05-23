package com.zhengxyou.demo523;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RaDar2View radio = findViewById(R.id.radio);
        radio.setCount(3);
        RaDar2View radio2 = findViewById(R.id.radio2);
        radio2.setCount(8);
        radio2.setDrawCircle(true);
    }
}
