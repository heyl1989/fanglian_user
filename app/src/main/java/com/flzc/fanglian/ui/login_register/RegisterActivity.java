package com.flzc.fanglian.ui.login_register;

import java.util.HashMap;
import java.util.Map;

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
import com.flzc.fanglian.model.BaseInfoBean;
import com.flzc.fanglian.model.UserRegisterBean;
import com.flzc.fanglian.ui.UserRegisterDealWebViewActivity;
import com.flzc.fanglian.util.StringUtils;

/**
 * 
 * @ClassName: RegisterActivity
 * @Description: 注册页面
 * @author: 薛东超
 * @date: 2016年3月4日 下午7:57:26
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout rl_back;
	private TextView tv_title, tv_right, tv_register_agreement;
	private EditText et_register_phonenum, ed_register_identifying,
			et_register_psd;
	private ImageView iv_register_register_user;

	private Button bt_register_gain_identifying;
	private TimeCount time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		initView();
	}

	private void initView() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_register_agreement = (TextView) findViewById(R.id.tv_register_agreement);
		tv_title.setText("注册");
		tv_title.setTextColor(Color.parseColor("#333333"));
		tv_right.setText("已有账号");
		tv_right.setTextColor(Color.parseColor("#333333"));
		//
		et_register_phonenum = (EditText) findViewById(R.id.et_register_phonenum);
		ed_register_identifying = (EditText) findViewById(R.id.ed_register_identifying);
		et_register_psd = (EditText) findViewById(R.id.et_register_psd);

		bt_register_gain_identifying = (Button) findViewById(R.id.bt_register_gain_identifying);
		bt_register_gain_identifying.setText("获取验证码（60）");
		time = new TimeCount(60000, 1000);// 构造CountDownTimer对象

		iv_register_register_user = (ImageView) findViewById(R.id.iv_register_register_user);

		// 添加单机事件
		rl_back.setOnClickListener(this);
		bt_register_gain_identifying.setOnClickListener(this);
		iv_register_register_user.setOnClickListener(this);
		tv_register_agreement.setOnClickListener(this);
		tv_right.setOnClickListener(this);

		// 监听输入手机号文本框的焦点事件
		et_register_phonenum
				.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							String phoneNumber = et_register_phonenum.getText()
									.toString().trim();
							if(TextUtils.isEmpty(phoneNumber)){
								showTost("手机号码不能为空");
							}else if(!StringUtils.isMobileNO(phoneNumber)){
								showTost("手机号格式有误");
							}
						}
					}
				});

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		// 返回
		case R.id.rl_back:
			finish();
			break;
		// 计时器的Button按钮
		case R.id.bt_register_gain_identifying:
			String phoneNumber = et_register_phonenum.getText().toString();
			if (StringUtils.isMobileNO(phoneNumber)) {
				isRegisterPhone();
			} else {
				showTost("请正确输入手机号码");
			}
			break;
		// 同意用户协议并注册
		case R.id.iv_register_register_user:
			String phoneNumberS = et_register_phonenum.getText().toString();
			String identifying = ed_register_identifying.getText().toString();
			String password = et_register_psd.getText().toString();

			if (!StringUtils.isMobileNO(phoneNumberS)) {
				showTost("请正确输入手机号码");
			} else if (StringUtils.isBlank(identifying)) {
				showTost("请输入验证码");
			} else if (StringUtils.isBlank(password)) {
				showTost("请输入密码");
			} else if (password.length() < 6) {
				showTost("密码长度请控制在6-16位");
			} else {
				agreementRegister(phoneNumberS,identifying,password);
			}
			break;
		// 房链用户协议条款
		case R.id.tv_register_agreement:
			goOthers(UserRegisterDealWebViewActivity.class);
			break;
		// 已有账号
		case R.id.tv_right:
			finish();
			break;
		default:
			break;
		}
	}

	private void isRegisterPhone() {
		String phoneNumber = et_register_phonenum.getText().toString();
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phoneNumber);

		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.CHECK_REGISTER_PHONE, BaseInfoBean.class,
				new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(BaseInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								showTost("验证码已发送");
								time.start();
								getRegisterCode();
							} else {
								showTost(response.getMsg());
							}
						}
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	/**
	 * 
	 * @Title: getRegisterCode
	 * @Description: 获取注册验证码
	 * @return: void
	 */
	private void getRegisterCode() {
		Map<String, String> map = new HashMap<String, String>();
		String phoneNumber = et_register_phonenum.getText().toString();
		map.put("phone", phoneNumber);
		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.SEND_REGISTER_CODE, BaseInfoBean.class,
				new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(BaseInfoBean response) {
						if (response != null && 0 == response.getStatus()) {
							// 网络请求获取验证码成功，即已经向所注册的手机发送验证码了
						} else {
							showTost(response.getMsg());
						}
					}
				}, map);
		RetryPolicy retryPolicy = new DefaultRetryPolicy(Constant.TIME_OUT, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		request.setRetryPolicy(retryPolicy);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	/**
	 * 
	 * @Title: agreementRegister
	 * @Description: 确定按钮，同意注册
	 * @return: void
	 */
	private void agreementRegister(String phoneNumber, String identifying,
			String password) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phoneNumber);
		map.put("validateCode", identifying);
		map.put("password", password);
		SimpleRequest<UserRegisterBean> request = new SimpleRequest<UserRegisterBean>(
				API.USER_REGISTER, UserRegisterBean.class,
				new Listener<UserRegisterBean>() {
					@Override
					public void onResponse(UserRegisterBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								// 注册成功
								showTost(response.getMsg());
								UserInfoData.saveData(Constant.TOKEN, response
										.getResult().getTokenId());
								finish();
							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
						showTost(getResources().getString(R.string.net_error));
					}
				},map);
		UserApplication.getInstance().addToRequestQueue(request);

	}

	/**
	 * 
	 * @ClassName: TimeCount
	 * @Description: 计时器
	 * @author: flzc
	 * @date: 2016年3月10日 下午6:56:29
	 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			bt_register_gain_identifying.setText("重发验证码");
			bt_register_gain_identifying.setBackgroundColor(Color
					.parseColor("#e6e9ee"));
			bt_register_gain_identifying.setTextColor(Color
					.parseColor("#333333"));
			bt_register_gain_identifying.setEnabled(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			bt_register_gain_identifying.setEnabled(false);
			bt_register_gain_identifying.setTextColor(Color.WHITE);
			bt_register_gain_identifying.setText(millisUntilFinished / 1000
					+ "秒后重发");
		}
	}
}
