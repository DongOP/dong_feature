package com.dong.cutoffscreen;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dong.cutoffscreen.utils.ScreenShotUtils;
import com.dong.cutoffscreen.utils.TimeUtils;


/**
 * Created by Dong on 2018/6/30.
 */
public class MainActivity extends AppCompatActivity {

    private ImageView mImage1;
    private Button mTakePhotots;
    private TextView mShowTime;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mContext = this;
        mImage1 = findViewById(R.id.image_1);
        mTakePhotots = findViewById(R.id.btn_screenshot);
        mShowTime = findViewById(R.id.show_time);

        mTakePhotots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String showMsg = "当前时间：" + TimeUtils.getCurTime();
                mShowTime.setText(showMsg);
                ScreenShotUtils.getInstance().doScreenshot(MainActivity.this);
            }
        });
    }

}
