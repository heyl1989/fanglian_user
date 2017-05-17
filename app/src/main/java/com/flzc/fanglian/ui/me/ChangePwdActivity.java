package com.flzc.fanglian.ui.me;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
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
import com.flzc.fanglian.model.BaseInfoBean;
import com.flzc.fanglian.ui.login_register.LoginActivity;
import com.flzc.fanglian.util.StringUtils;

/**
 * 
 * @ClassName: ChangePwdActivity
 * @Description: 修改密码页面
 * @author: Tien.
 * @date: 2016年3月8日 下午8:01:56
 */
public class ChangePwdActivity extends BaseActivity implements OnClickListener {
	private RelativeLayout rl_back;
	private TextView titleName;
	private EditText et_newPwd_changeLoginPwd, et_confirmPwd_changeLoginPwd;
	private Button btn_confirm_changeLoginPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		initItem();
	}

	private void initItem() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);

		titleName = (TextView) findViewById(R.id.tv_title);
		titleName.setText("修改密码");

		et_newPwd_changeLoginPwd = (EditText) findViewById(R.id.et_newPwd_changeLoginPwd);
		et_confirmPwd_changeLoginPwd = (EditText) findViewById(R.id.et_confirmPwd_changeLoginPwd);
		btn_confirm_changeLoginPwd = (Button) findViewById(R.id.btn_confirm_changeLoginPwd);

		btn_confirm_changeLoginPwd.setOnClickListener(this);

		et_newPwd_changeLoginPwd
				.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						String oldPsd = et_newPwd_changeLoginPwd.getText()
								.toString();
						if (!hasFocus) {
							if (StringUtils.isBlank(oldPsd)) {
								showTost("旧密码不能为空");
							}
						}
					}
				});

		et_confirmPwd_changeLoginPwd
				.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						String newPsd = et_confirmPwd_changeLoginPwd.getText()
								.toString();
						if (!hasFocus) {
							if (StringUtils.isBlank(newPsd)) {
								showTost("新密码不能为空");
							}
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.btn_confirm_changeLoginPwd:
			String oldPsd = et_newPwd_changeLoginPwd.getText().toString();
			String newPsd = et_confirmPwd_changeLoginPwd.getText().toString();
			if (newPsd.length() < 6 || oldPsd.length() < 6) {
				showTost("密码长度请控制在6-16位");
			} else {
				changePsdSure(oldPsd,newPsd);
			}
			break;
		default:
			break;
		}
	}

	private void changePsdSure(String oldPsd,String newPsd) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("oldPassword", oldPsd);
		map.put("password", newPsd);

		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.UPDATE_PASSWORD, BaseInfoBean.class,
				new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(BaseInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								// 修改密码成功
//								AppManager manager = AppManager.getAppManager();
//								manager.finishActivity(AccountSafetyActivity.class);
//								manager.finishActivity(MyInfoActivity.class);
								showTost("密码修改成功");
								goOthers(LoginActivity.class);
							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
						loadingDialog.dismissDialog();
					}
				},map);
		UserApplication.getInstance().addToRequestQueue(request);

	}
}
