package com.flzc.fanglian.ui.login_register;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.RetryPolicy;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.MessageBean;
import com.flzc.fanglian.model.UserLoginBean;
import com.flzc.fanglian.model.UserLoginResultBean;
import com.flzc.fanglian.ui.HomeActivity;
import com.flzc.fanglian.ui.home.ChooseCityActivity;
import com.flzc.fanglian.util.JpushUtil;
import com.flzc.fanglian.util.StringUtils;

public class QuickLoginActivity extends BaseActivity implements OnClickListener{

	private RelativeLayout rl_back;
	private TextView tv_title;
	
	private EditText ed_phone_quickLogin;
	private Button bt_code_quickLogin;
	private EditText ed_messageCode_quickLogin;
	private TimeCount time;
	
	private ImageView login_quickLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quick_login);
		initView();
	}
	
	private void initView(){
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("手机号快捷登录");
		
		ed_phone_quickLogin = (EditText) findViewById(R.id.ed_phone_quickLogin);
		ed_phone_quickLogin.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					String phoneNumber = ed_phone_quickLogin.getText()
							.toString().trim();
					if(TextUtils.isEmpty(phoneNumber)){
						showTost("手机号码不能为空");
					}else if(!StringUtils.isMobileNO(phoneNumber)){
						showTost("手机号格式有误");
					}
				}
				
			}
		});
		bt_code_quickLogin = (Button) findViewById(R.id.bt_code_quickLogin);
		bt_code_quickLogin.setText("获取验证码（60）");
		bt_code_quickLogin.setOnClickListener(this);
		time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
		ed_messageCode_quickLogin = (EditText) findViewById(R.id.ed_messageCode_quickLogin);
		
		login_quickLogin = (ImageView) findViewById(R.id.login_quickLogin);
		login_quickLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back://返回
			finish();
			break;
		case R.id.bt_code_quickLogin://获取验证码
			String phoneNumber = ed_phone_quickLogin.getText().toString().trim();
			if (TextUtils.isEmpty(phoneNumber) || !StringUtils.isMobileNO(phoneNumber)) {
				showTost("手机号格式有误");
			}else{
				time.start();
				sendCode();
			}
			break;
		case R.id.login_quickLogin://登录按钮
			submitData();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 
	 * @Title: sendCode 
	 * @Description: 发送验证码
	 * @return: void
	 */
	private void sendCode(){
		String phone = ed_phone_quickLogin.getText().toString().trim();
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phone);
		SimpleRequest<MessageBean> request = new SimpleRequest<MessageBean>(API.SENDLOGINCODE, MessageBean.class,
				new Listener<MessageBean>() {
					@Override
					public void onResponse(MessageBean response) {
						if(response.getStatus() == 0){
							showTost("验证码已发送");
						}else{
							showTost(response.getMsg());
						}
					}
				}, map); 
		RetryPolicy retryPolicy = new DefaultRetryPolicy(Constant.TIME_OUT, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		request.setRetryPolicy(retryPolicy);
		UserApplication.getInstance().addToRequestQueue(request);
	}
	
	private void submitData(){
		String code = ed_messageCode_quickLogin.getText().toString().trim();
		String phone = ed_phone_quickLogin.getText().toString().trim();
		if(TextUtils.isEmpty(phone) || !StringUtils.isMobileNO(phone)){
			showTost("手机号格式有误");
			return;
		}
		if(TextUtils.isEmpty(code)){
			showTost("验证码不能空");
			return;
		}
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phone);
		map.put("validateCode", code);
		SimpleRequest<UserLoginBean> request = new SimpleRequest<UserLoginBean>(API.USERLOGINBYVALIDATECODE, UserLoginBean.class,
				new Listener<UserLoginBean>() {
					@Override
					public void onResponse(UserLoginBean response) {
						if(null != response.getResult()){
							if(response.getResult().getStatus() == 0){
								UserLoginResultBean result = response
										.getResult();
								UserInfoData.saveData(Constant.TOKEN,
										result.getTokenId());
								UserInfoData.saveData(Constant.NICK_NAME,
										result.getNickName());
								UserInfoData.saveData(Constant.MY_INVITE_CODE,
										result.getMyInviteCode());
								new JpushUtil().setBrokerAlias(result.getMyInviteCode()+"_5");
								showTost("登录成功");
								// showTost(response.getResult().getNickName());
								if (UserInfoData
										.isContainKey(Constant.LOC_CITY_ID)) {
									// 跳到主界面
									LoginActivity.loginActivity.finish();
									finish();
								} else {
									Intent intent = new Intent(QuickLoginActivity.this,ChooseCityActivity.class);
									intent.putExtra("from", "notFromHome");
									startActivity(intent);
									finish();
								}
								
							}else{
								showTost(response.getResult().getMsg());
							}
						}
						
						loadingDialog.dismissDialog();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
						
					}
				},map);
		UserApplication.getInstance().addToRequestQueue(request);
	}
	
	/**
	 * 
	 * @ClassName: TimeCount
	 * @Description: 定时器
	 * @author: 何永亮
	 * @date: 2016年3月11日 下午12:37:24
	 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发

			bt_code_quickLogin.setText("重发验证码");
			bt_code_quickLogin.setBackgroundColor(Color
					.parseColor("#e6e9ee"));
			bt_code_quickLogin.setTextColor(Color
					.parseColor("#333333"));
			bt_code_quickLogin.setEnabled(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			bt_code_quickLogin.setEnabled(false);
			bt_code_quickLogin.setTextColor(Color.WHITE);
			bt_code_quickLogin.setText(millisUntilFinished / 1000
					+ "秒后重发");

		}
	}
}
