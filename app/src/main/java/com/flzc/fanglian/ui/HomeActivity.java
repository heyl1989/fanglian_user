package com.flzc.fanglian.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseFragmentActivity;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.ui.fragment.BuildFragment;
import com.flzc.fanglian.ui.fragment.HomeFragment;
import com.flzc.fanglian.ui.fragment.MeFragment;
import com.flzc.fanglian.ui.fragment.MessageFragment;
import com.flzc.fanglian.ui.login_register.LoginActivity;
import com.flzc.fanglian.update.UpdateChecker;

/**
 * 
 * @ClassName: HomeActivity
 * @Description:主Fragment容器
 * @author: LU
 * @date: 2016-3-4 下午3:12:13
 */
public class HomeActivity extends BaseFragmentActivity implements
		OnCheckedChangeListener {

	private RadioGroup rg_bottom;
	private HomeFragment homeFragment;
	private BuildFragment buildFragment;
	private MessageFragment messageFragment;
	private MeFragment meFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		UpdateChecker.checkForForceDialog(this, false);
		initView();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//rg_bottom.check(R.id.rb_home);
	}
	
	private void initView() {
		rg_bottom = (RadioGroup) findViewById(R.id.rg_bottom_home);
		rg_bottom.setOnCheckedChangeListener(this);
		rg_bottom.check(R.id.rb_home);		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		FragmentManager supportFragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = supportFragmentManager
				.beginTransaction();

		switch (checkedId) {
		case R.id.rb_home:
			if(null == homeFragment){
				homeFragment = new HomeFragment();
			}
			transaction.replace(R.id.ll_content, homeFragment);
			break;
		case R.id.rb_build:
			if(null == buildFragment){
				buildFragment = new BuildFragment();
			}
			transaction.replace(R.id.ll_content, buildFragment);
			break;
		case R.id.rb_message:
			if(UserInfoData.isSingIn()){
				if(null == messageFragment){
					messageFragment = new MessageFragment();
				}
				transaction.replace(R.id.ll_content,messageFragment);
			}else{
				goOthers(this, LoginActivity.class);
				rg_bottom.check(R.id.rb_home);
			}			
			break;
		case R.id.rb_me:
			if(null == meFragment){
				meFragment = new MeFragment();
			}
			transaction.replace(R.id.ll_content, meFragment);
			break;
		}
		transaction.commit();
	}

	// 再按一次退出程序
	private long exitTime = 0;

	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			showTost("再按一次退出房链");
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
