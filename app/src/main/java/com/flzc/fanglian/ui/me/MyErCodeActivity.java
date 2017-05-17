package com.flzc.fanglian.ui.me;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.util.CreateQRImageUtil;
import com.flzc.fanglian.util.LogUtil;
import com.flzc.fanglian.view.CircleImageView;
import com.google.gson.Gson;
/**
 * 
 * @ClassName: MyErCodeActivity 
 * @Description: 我的二维码 
 * @author: LU
 * @date: 2016-3-4 下午3:12:35
 */
public class MyErCodeActivity extends BaseActivity implements OnClickListener{
	
	private RelativeLayout rl_back;
	private TextView tv_title;
	private CircleImageView head_img;
	private TextView userName;
	private ImageView erCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myercode);
		initView();
		initListener();
	}

	private void initView() {
		//标题
		rl_back = (RelativeLayout)findViewById(R.id.rl_back);
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("我的二维码");
		//二维码
		erCode = (ImageView)findViewById(R.id.img_er_code);
		Gson gson = new Gson();
		Map<String,String> map = new HashMap<String,String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("type", "1");
		String ercode = gson.toJson(map);
		LogUtil.e("二维码", ercode);
		new CreateQRImageUtil(erCode).createQRImage(ercode);
		//头像
		head_img = (CircleImageView)findViewById(R.id.cimg_head_img);
		imageLoader.displayImage(UserInfoData.getData(Constant.HEAD_URL, ""), head_img, options);
		//用户名
		userName = (TextView)findViewById(R.id.tv_username);
		userName.setText(UserInfoData.getData(Constant.NICK_NAME, ""));
		
	}

	private void initListener() {
		rl_back.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;

		default:
			break;
		}
		
	}

}
