package ui.mss.com.mssui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import ui.mss.com.mssui.R;

public class SettingDialog extends Dialog implements OnClickListener {
	private Context mContext;
	private Button mFinalButton;
	private Switch mSwitchMSS;

	public SettingDialog(Context context, int theme) {
		super(context, R.style.DialogTheme);
	}

	public SettingDialog(Context context) {
		this(context, R.style.DialogTheme);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_dialog);
		initView();
	}

	private void initView() {
		mFinalButton = (Button) findViewById(R.id.close_dialog);
		mFinalButton.setOnClickListener(this);

		mSwitchMSS = (Switch) findViewById(R.id.switch_mss);
		// 是否暂停同步Switch开关
		mSwitchMSS.setChecked(false);
		// 设置Switch上的文字颜色
		mSwitchMSS.setSwitchTextAppearance(mContext, R.style.s_false);
		mSwitchMSS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mSwitchMSS.setSwitchTextAppearance(mContext, R.style.s_true);
				} else {
					mSwitchMSS.setSwitchTextAppearance(mContext, R.style.s_false);
				}
			}
		});
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.close_dialog:
			dismiss();
			break;

		default:
			break;
		}
	}

}
