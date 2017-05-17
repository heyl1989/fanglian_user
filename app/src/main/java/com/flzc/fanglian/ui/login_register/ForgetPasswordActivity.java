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
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.BaseInfoBean;
import com.flzc.fanglian.util.StringUtils;

/***
 * 
 * @ClassName: ForgetPasswordActivity
 * @Description: 忘记密码页面
 * @author: 薛东超
 * @date: 2016年3月4日 下午6:34:54
 */
public class ForgetPasswordActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_back;
	private TextView tv_title, tv_right;
	private EditText et_forgetpsd_phonenum, ed_forgetpsd_identifying,
			et_forgetpsd_psd;
	private ImageView iv_forgetpsd_reset_psd;

	private Button bt_forgetpsd_gain_identifying;
	private TimeCount time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpassword);
		initView();
	}

	private void initView() {
		//标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_title.setText("忘记密码");
		tv_right.setText("");
		//
		et_forgetpsd_phonenum = (EditText) findViewById(R.id.et_forgetpsd_phonenum);
		ed_forgetpsd_identifying = (EditText) findViewById(R.id.ed_forgetpsd_identifying);
		et_forgetpsd_psd = (EditText) findViewById(R.id.et_forgetpsd_psd);
		//重设密码按钮
		iv_forgetpsd_reset_psd = (ImageView) findViewById(R.id.iv_forgetpsd_reset_psd);
		//到几时
		bt_forgetpsd_gain_identifying = (Button) findViewById(R.id.bt_forgetpsd_gain_identifying);
		bt_forgetpsd_gain_identifying.setText("获取验证码（60）");
		time = new TimeCount(60000, 1000);// 构造CountDownTimer对象

		rl_back.setOnClickListener(this);
		bt_forgetpsd_gain_identifying.setOnClickListener(this);
		iv_forgetpsd_reset_psd.setOnClickListener(this);

		clearRequest();
	}

	/**
	 * 
	 * @Title: clearRequest
	 * @Description: 当每一个编辑框焦点监听事件
	 * @return: void
	 */
	private void clearRequest() {
		// 输入手机号监听焦点
		et_forgetpsd_phonenum
				.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							String phoneNumber = et_forgetpsd_phonenum
									.getText().toString().trim();
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
		case R.id.bt_forgetpsd_gain_identifying:
			String phoneNumber = et_forgetpsd_phonenum.getText().toString().trim();
			if (TextUtils.isEmpty(phoneNumber) || !StringUtils.isMobileNO(phoneNumber)) {
				showTost("请正确填写手机号码");
			}else{
				getSendFindPasswordCode();
				time.start();
				//checkIsRegest(phoneNumber);
			}
			break;
		// 重设密码
		case R.id.iv_forgetpsd_reset_psd:
			String phone = et_forgetpsd_phonenum.getText().toString().trim();
			String code = ed_forgetpsd_identifying.getText().toString().trim();
			String password = et_forgetpsd_psd.getText().toString().trim();
			if (TextUtils.isEmpty(phone) || !StringUtils.isMobileNO(phone)) {
				showTost("请正确输入手机号码");
			} else if (TextUtils.isEmpty(code)) {
				showTost("验证码不能为空");
			} else if (TextUtils.isEmpty(password) || password.length() < 6) {
				showTost("密码长度请控制在6-16位");
			} else {
				resetPassword(phone,password,code);
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 检查手机号码是否已注册
	 */
	private void checkIsRegest(String phoneNumber) {
		// 手机号如果没有误，验证该手机号是否已注册，未注册则提示：该手机号未注册
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phoneNumber);
		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.CHECK_REGISTER_PHONE,
				BaseInfoBean.class,
				new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(
							BaseInfoBean response) {
						if (response != null) {
							// 0表示手机号未注册 4表示手机号已注册
							if (0 == response.getStatus()) {
								showTost("该手机号未注册");
							}else if(4 == response.getStatus()){
								showTost("验证码已发送");
								getSendFindPasswordCode();
								time.start();
							}else{
								showTost(response.getMsg());
							}
						}
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	/**
	 * 
	 * @Title: getSendFindPasswordCode
	 * @Description: 发送找回密码验证码
	 * @return: void
	 */
	private void getSendFindPasswordCode() {
		Map<String, String> map = new HashMap<String, String>();
		String phoneNumber = et_forgetpsd_phonenum.getText().toString().trim();
		map.put("phone", phoneNumber);
		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.SENDFINDPASSWORDCODE, BaseInfoBean.class,
				new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(BaseInfoBean response) {
						if (response != null && 0 == response.getStatus()) {
							// 网络请求获取验证码成功，即已经向所注册的手机发送验证码了
							showTost("验证码已发送");
						} else {
							showTost(response.getMsg());
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
					}
				},map);
		RetryPolicy retryPolicy = new DefaultRetryPolicy(Constant.TIME_OUT, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		request.setRetryPolicy(retryPolicy);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	/**
	 * 重置密码请求 
	 * @param phone
	 * @param password
	 * @param code
	 */
	private void resetPassword(String phone,String password,String code) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phone);
		map.put("password", password);
		map.put("validateCode", code);
		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.FINDPASSWORD, BaseInfoBean.class,
				new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(BaseInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								// 密码找回成功
								finish();
							} else {
								// 密码找回失败
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
						showTost(getResources().getString(R.string.net_error));
					}
				}, map);
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

			bt_forgetpsd_gain_identifying.setText("重发验证码");
			bt_forgetpsd_gain_identifying.setBackgroundColor(Color
					.parseColor("#e6e9ee"));
			bt_forgetpsd_gain_identifying.setTextColor(Color
					.parseColor("#333333"));
			bt_forgetpsd_gain_identifying.setEnabled(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			bt_forgetpsd_gain_identifying.setEnabled(false);
			bt_forgetpsd_gain_identifying.setTextColor(Color.WHITE);
			bt_forgetpsd_gain_identifying.setText(millisUntilFinished / 1000
					+ "秒后重发");

		}
	}

}
