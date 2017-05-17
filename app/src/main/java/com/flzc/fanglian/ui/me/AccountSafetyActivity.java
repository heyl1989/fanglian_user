package com.flzc.fanglian.ui.me;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @ClassName: AccountSafetyActivity 
 * @Description: 账户安全页面
 * @author: Tien.
 * @date: 2016年3月8日 下午7:39:42
 */
public class AccountSafetyActivity extends BaseActivity implements OnClickListener{
	private RelativeLayout rl_back,rl_changePwd_accountSafety;
	private TextView titleName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_safety);
		
		initItem();
	}
	
	private void initItem(){
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		
		titleName = (TextView) findViewById(R.id.tv_title);
		titleName.setText("账户安全");
		
		rl_changePwd_accountSafety = (RelativeLayout) findViewById(R.id.rl_changePwd_accountSafety);
		rl_changePwd_accountSafety.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == rl_back) {
			finish();
		}else if (v == rl_changePwd_accountSafety) {
			goOthers(ChangePwdActivity.class);
		}
	}
}
