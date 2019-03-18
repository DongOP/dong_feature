package ui.mss.com.mssui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ui.mss.com.mssui.util.CheckVirtualUtil;
import ui.mss.com.mssui.view.SettingDialog;

public class MainActivity extends Activity {

    private Context mContext;
    private Button mStartDialog;
    private Button mPingPangBtn;
    private Button mCheckVirtualBtn;
    // 乒乓键的状态
    private Boolean isTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mContext = MainActivity.this;
        // 默认乒乓键第一次为false
        isTrue = false;
        mStartDialog = (Button) findViewById(R.id.btn_dialog);
        mPingPangBtn = (Button) findViewById(R.id.btn_pingpang);
        mCheckVirtualBtn = (Button) findViewById(R.id.check_v_btn);

        mStartDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
            }
        });

        // 需要先初始化按钮的状态
        checkBtnStatus();
        mPingPangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTrue) {
                    mPingPangBtn.setText("乒乓键（打开）");
                    isTrue = false;
                } else {
                    mPingPangBtn.setText("乒乓键（关闭）");
                    isTrue = true;
                }
            }
        });

        mCheckVirtualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
                mCheckVirtualBtn.setText("当前手机是否带有虚拟按键：" + CheckVirtualUtil.checkNavigationBar(mContext) + ", 高度=" + CheckVirtualUtil.getVirtualUtilHeight(mContext));
            }
        });
    }

    private void checkBtnStatus() {
        if (isTrue) {
            mPingPangBtn.setText("乒乓键（打开）");
            isTrue = false;
        } else {
            mPingPangBtn.setText("乒乓键（关闭）");
            isTrue = true;
        }
    }

    private void startDialog() {
        SettingDialog settingDialog = new SettingDialog(mContext);
        settingDialog.show();
    }
}
