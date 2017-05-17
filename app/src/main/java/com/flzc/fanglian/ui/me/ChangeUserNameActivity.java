package com.flzc.fanglian.ui.me;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.flzc.fanglian.model.ChangeUserNameBean;

/**
 * 
 * @ClassName: ChangeUserNameActivity
 * @Description: 修改用户名
 * @author: Tien.
 * @date: 2015年10月28日 下午3:45:21
 */
public class ChangeUserNameActivity extends BaseActivity implements
		OnClickListener {
	SharedPreferences share;
	SharedPreferences.Editor sEditor;

	private RelativeLayout layout_back;
	private TextView titleName, tv_save_change_UserName;
	private EditText et_change_userName;
	private String nickName;// 上个页面传过来的NickName

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_username);

		initItem();
	}

	private void initItem() {
		layout_back = (RelativeLayout) findViewById(R.id.rl_back);
		layout_back.setOnClickListener(this);

		titleName = (TextView) findViewById(R.id.tv_title);
		titleName.setText("修改昵称");

		tv_save_change_UserName = (TextView) findViewById(R.id.tv_savaUserName_changeUsername);
		tv_save_change_UserName.setTextColor(Color.parseColor("#999999"));
		tv_save_change_UserName.setEnabled(false);
		tv_save_change_UserName.setOnClickListener(this);

		et_change_userName = (EditText) findViewById(R.id.et_changeUsername);
		et_change_userName
				.setText(UserInfoData.getData(Constant.NICK_NAME, ""));
		et_change_userName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				tv_save_change_UserName.setTextColor(Color
						.parseColor("#dc4242"));
				tv_save_change_UserName.setEnabled(true);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (et_change_userName.getText().toString().trim() == null
						|| "".equals(et_change_userName.getText().toString()
								.trim())) {
					tv_save_change_UserName.setTextColor(Color
							.parseColor("#999999"));
					tv_save_change_UserName.setEnabled(false);
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
		case R.id.tv_savaUserName_changeUsername:// 提交修改用户名
			if (TextUtils.isEmpty(et_change_userName.getText().toString()
					.trim())) {
				showTost("用户名不能为空");
			} else if (!isIllegel(et_change_userName.getText().toString()
					.trim())) {
				showTost("昵称不符合规则，请重新输入");
				et_change_userName.setText("");
			} else {
				postNickName();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * @Title: postNickName
	 * @Description: 提交修改的昵称
	 * @return: void
	 */
	private void postNickName() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("nickName", et_change_userName.getText().toString().trim());
		SimpleRequest<ChangeUserNameBean> request = new SimpleRequest<ChangeUserNameBean>(
				API.UPDATE_NICK_NAME, ChangeUserNameBean.class,
				new Listener<ChangeUserNameBean>() {
					@Override
					public void onResponse(ChangeUserNameBean response) {
						if(response != null){
							if(0 == response.getStatus()){
								UserInfoData.saveData(Constant.NICK_NAME, response.getNickName());
								UserApplication.getInstance().setReflush(true);
								finish();
							}else{
								showTost(response.getMsg());
							}
						}
						loadingDialog.showDialog();
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.showDialog();
						showTost(getResources().getString(R.string.net_error));
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	// 判断用户名是否符合规则
	private boolean isIllegel(String str) {
		int currentLong = 0; // 当前输入的字符总长度 一个汉字算两个 字符 一个字母或下划线算一个字符 总长度不超过20
		for (int i = 0; i < str.length(); i++) {
			char item = str.charAt(i);
			if (isHanzi(item)) {// 如果是汉字
				currentLong += 2;
			} else if (userNameIsIllegel(item)) { // 如果是字母 数字 下划线
				currentLong += 1;
			} else {
				return false;
			}
		}
		if (currentLong >= 4 && currentLong <= 16) {
			return true;
		} else {
			return false;
		}
	}

	// 是否是汉字
	private boolean isHanzi(char str) {
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher matcher = pattern.matcher(String.valueOf(str));
		return matcher.matches();
	}

	// 判断是否是 字母 数字 和下划线
	private boolean userNameIsIllegel(char str) {
		// /^[A-Za-z0-9-_]*$/
		Pattern pattern = Pattern.compile("^[A-Za-z0-9_]$");
		Matcher matcher = pattern.matcher(String.valueOf(str));
		return matcher.matches();
	}
}
