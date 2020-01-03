package com.zzh.auto.size;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.zzh.lib.core.HStringSpannable;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);
        HStringSpannable hs = new HStringSpannable();
        hs.append("AAA", Color.WHITE);
        hs.append("bbb", Color.BLUE);
        tv.setText(hs);
    }
}
