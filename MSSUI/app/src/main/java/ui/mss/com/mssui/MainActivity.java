package ui.mss.com.mssui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import ui.mss.com.mssui.view.SettingDialog;

public class MainActivity extends Activity {

    private Button mStartDialog;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mContext = MainActivity.this;
        mStartDialog = (Button) findViewById(R.id.btn_dialog);

        mStartDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
            }
        });
    }

    private void startDialog() {
        SettingDialog settingDialog = new SettingDialog(mContext);
//        settingDialog.setCancelable(false);
        settingDialog.show();
    }
}
