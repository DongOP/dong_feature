package com.mss.custom;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mss.custom.view.RingView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private RingView mRingView;
    private Button mOutterColorBtn;
    private Button mInnerColorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        initView();
    }

    private void initView() {
        mRingView = (RingView) findViewById(R.id.circle_bar);
        mOutterColorBtn = findViewById(R.id.outer_color_btn);
        mInnerColorBtn = findViewById(R.id.inner_color_btn);


        mOutterColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRingView.setOuterRingColor(getRandomColor());
            }
        });

        mInnerColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRingView.setInnerRingColor(getRandomColor());
            }
        });
    }

    public static int getRandomColor() {
        Random random = new Random();
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < 2; i++) {
            //       result=result*10+random.nextInt(10);
            int temp = random.nextInt(16);
            r = r * 16 + temp;
            temp = random.nextInt(16);
            g = g * 16 + temp;
            temp = random.nextInt(16);
            b = b * 16 + temp;
        }
        return Color.rgb(r, g, b);
    }
}
