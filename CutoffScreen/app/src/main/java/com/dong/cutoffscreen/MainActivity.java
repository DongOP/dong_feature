package com.dong.cutoffscreen;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dong.cutoffscreen.utils.ScreenShotUtils;
import com.dong.cutoffscreen.utils.TimeUtils;


public class MainActivity extends AppCompatActivity {

    private ImageView mImage1;
    private Button mTakePhotots;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mContext = this;
        mImage1 =findViewById(R.id.image_1);
        mTakePhotots = findViewById(R.id.btn_screenshot);

        mTakePhotots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeUtils.getCurTime();
                ScreenShotUtils.getInstance().doScreenshot(MainActivity.this);
            }
        });
    }

}
