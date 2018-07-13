package com.dong.cutoffscreen;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dong.cutoffscreen.utils.JsonDataUtils;
import com.dong.cutoffscreen.utils.ScreenShotUtils;
import com.dong.cutoffscreen.utils.TimeUtils;


/**
 * Created by Dong on 2018/6/30.
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private ImageView mImage1;
    private Button mTakePhotots;
    private TextView mShowTime, mShowRGBA;
    private Context mContext;
    private Bitmap mBitmap;
    private LinearLayout mLLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mContext = this;
        mImage1 = (ImageView) findViewById(R.id.image_1);
        mTakePhotots = (Button) findViewById(R.id.btn_screenshot);
        mShowTime = (TextView) findViewById(R.id.show_time);
        mShowRGBA = (TextView) findViewById(R.id.show_rgba);
        mLLayout = (LinearLayout) findViewById(R.id.layout);
        // 初始化数据
        Resources res = getResources();
        mBitmap = BitmapFactory.decodeResource(res, R.mipmap.colors);

        mTakePhotots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String showMsg = "当前时间：" + TimeUtils.getCurTime();
                mShowTime.setText(showMsg);
                ScreenShotUtils.getInstance().doScreenshot(MainActivity.this);
            }
        });

//        mImage1.setOnTouchListener(new ImageOnTouchListener());
        // 验证getStreamPixel方法
        mLLayout.setOnTouchListener(new LLayoutOnTouchListener());

        // 测试
        JsonDataUtils.buildJson();
    }

    private class ImageOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                try {
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();
                    // 获取图片坐标点的颜色
                    int color = mBitmap.getPixel(x, y);
//                    int r = Color.red(color);
//                    int g = Color.green(color);
//                    int b = Color.blue(color);
//                    int a = Color.alpha(color);
//                    LogUtils.logd(TAG, "x=" + x + ",y=" + y + "，mBitmap.width=" + mBitmap.getWidth() + ",mBitmap.getHeight=" + mBitmap.getHeight());
//                    String msg = "r=" + r + ",g=" + g + ",b=" + b + ",a=" + a;
                    mShowRGBA.setText("图片的像素值：" + color);
//                    mShowRGBA.setTextColor(Color.rgb(r, g, b));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    private class LLayoutOnTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                ScreenShotUtils.getInstance().getStreamPixel(MainActivity.this, x, y);
            }
            return true;
        }
    }
}
