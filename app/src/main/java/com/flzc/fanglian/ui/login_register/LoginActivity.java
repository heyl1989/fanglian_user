package com.flzc.fanglian.ui.login_register;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.UserLoginBean;
import com.flzc.fanglian.model.UserLoginResultBean;
import com.flzc.fanglian.ui.HomeActivity;
import com.flzc.fanglian.ui.home.ChooseCityActivity;
import com.flzc.fanglian.util.JpushUtil;
import com.flzc.fanglian.util.StringUtils;

/**
 * 
 * @ClassName: LoginActivity
 * @Description: 登录
 * @author: LU
 * @date: 2016-3-4 下午6:04:20
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout rl_back;
	private TextView tv_right;
	private TextView forgetPassword;
	private EditText et_landing_phon, et_landing_psd;
	private ImageView img_login;
	private TextView phone_quick_login;
	private ImageView img_password;
	public static LoginActivity loginActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loginActivity = this;
		setContentView(R.layout.activity_login);
		initView();
		initListener();
	}

	private void initView() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_right = (TextView) findViewById(R.id.tv_right);

		et_landing_phon = (EditText) findViewById(R.id.et_landing_phon);
		et_landing_psd = (EditText) findViewById(R.id.et_landing_psd);
		img_password = (ImageView)findViewById(R.id.img_password);
		et_landing_psd.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					img_password.setImageResource(R.drawable.icon_password_focus);
				}else{
					img_password.setImageResource(R.drawable.icon_password);
				}
			}
		});
		// 忘记密码
		forgetPassword = (TextView) findViewById(R.id.tv_forget_password);
		// 确认
		img_login = (ImageView) findViewById(R.id.img_login);
		phone_quick_login = (TextView) findViewById(R.id.phone_quick_login);
		
		// 手机号框失去焦点时候判断手机号是否合法
		et_landing_phon.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					String phone = et_landing_phon.getText().toString().trim();
					if(TextUtils.isEmpty(phone)){
						showTost("手机号码不能为空");
					}else if(!StringUtils.isMobileNO(phone)){
						showTost("手机号格式有误");
					}
				}
			}
		});
	}

	private void initListener() {
		// 标题栏
		rl_back.setOnClickListener(this);
		tv_right.setOnClickListener(this);
		// 忘记密码
		forgetPassword.setOnClickListener(this);
		// 确认
		img_login.setOnClickListener(this);
		phone_quick_login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
//			if (UserInfoData.isContainKey(Constant.LOC_CITY_ID)) {
//				// 跳到主界面
//				finish();
//				//goOthers(HomeActivity.class);
//			} else {
//				Intent intent = new Intent(LoginActivity.this,ChooseCityActivity.class);
//				intent.putExtra("from", "notFromHome");
//				startActivity(intent);
//				finish();
//			}
			finish();
			break;
		case R.id.tv_right:
			goOthers(RegisterActivity.class);
			break;
		case R.id.tv_forget_password:
			goOthers(ForgetPasswordActivity.class);
			break;
		case R.id.img_login:
			String phone = et_landing_phon.getText().toString();
			String password = et_landing_psd.getText().toString();
			if (TextUtils.isEmpty(phone) || !StringUtils.isMobileNO(phone)) {
				showTost("请正确输入手机号码");
			} else if (TextUtils.isEmpty(password)) {
				showTost("请输入密码");
			} else {
				loginValidate(phone, password);
			}
			break;
		case R.id.phone_quick_login:
			goOthers(QuickLoginActivity.class);
			break;
		}
	}

	private void loginValidate(String phone, String password) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phone);
		map.put("password", password);
		SimpleRequest<UserLoginBean> request = new SimpleRequest<UserLoginBean>(
				API.USER_LOGIN_BY_PASSWORD, UserLoginBean.class,
				new Listener<UserLoginBean>() {
					@Override
					public void onResponse(UserLoginBean response) {
						if (response != null) {
							// 表示登录成功
							if (0 == response.getStatus()) {
								UserLoginResultBean result = response
										.getResult();
								UserInfoData.saveData(Constant.TOKEN,
										result.getTokenId());
								UserInfoData.saveData(Constant.PHONE,
										result.getPhone());
								UserInfoData.saveData(Constant.NICK_NAME,
										result.getNickName());
								UserInfoData.saveData(Constant.MY_INVITE_CODE,
										result.getMyInviteCode());
								// 登录成功后会把用户的人头像传回
								UserInfoData.saveData(Constant.HEAD_URL,
										result.getHeadUrl());
								new JpushUtil().setBrokerAlias(result.getMyInviteCode()+"_5");
								showTost("登录成功");
								// showTost(response.getResult().getNickName());
								if (UserInfoData
										.isContainKey(Constant.LOC_CITY_ID)) {
									// 跳到主界面
									finish();
									//goOthers(HomeActivity.class);
								} else {
									Intent intent = new Intent(LoginActivity.this,ChooseCityActivity.class);
									intent.putExtra("from", "notFromHome");
									startActivity(intent);
									finish();
								}
							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
						loadingDialog.dismissDialog();
					}
				} ,map);
		UserApplication.getInstance().addToRequestQueue(request);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//goOthers(HomeActivity.class);
			finish();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
