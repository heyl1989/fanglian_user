package com.flzc.fanglian.ui.message;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.flzc.fanglian.model.BaseInfoBean;
import com.flzc.fanglian.model.MessageListBean.Result;
import com.flzc.fanglian.util.DateUtils;
/**
 * 
 * @ClassName: MessageDetailActivity 
 * @Description: 消息详情
 * @author: LU
 * @date: 2016-3-9 下午4:25:49
 */
public class MessageDetailActivity extends BaseActivity implements OnClickListener{
	
	private RelativeLayout rl_back;
	private TextView tv_title;
	private Result messageResult;
	private ImageView msgIcon;
	private TextView msgType;
	private TextView msgTime;
	private TextView message;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_details_info);
		messageResult = (Result) getIntent().getExtras().getSerializable("messageResult");
		initView();
		initData();
		initListener();
	}

	private void initView() {
		//标题栏
		rl_back = (RelativeLayout)findViewById(R.id.rl_back);
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("查看消息");
		//内容分
		msgIcon = (ImageView)findViewById(R.id.iv_msgdetails_msgicon);
		msgType = (TextView)findViewById(R.id.tv_msgdetails_msgtype);
		msgTime = (TextView)findViewById(R.id.tv_msgdetails_msgtime);
		message = (TextView)findViewById(R.id.tv_msgdetails_message);
		
		if(messageResult != null){
			if(messageResult.getMessageRead() == 0){
				initMessageState(messageResult.getId()+"");
			}
		}
	}
	private void initData() {
		if(null != messageResult){
			switch (messageResult.getMessageType()) {
			//类型0为系统类型，1为活动类型、2为运营消息
			case 0:
				msgIcon.setImageResource(R.drawable.message_register);
				break;
			case 1:
				msgIcon.setImageResource(R.drawable.message_bid);
				break;
			case 2:
				msgIcon.setImageResource(R.drawable.message_user);
				break;
			}
			msgType.setText(messageResult.getTitle());
			msgTime.setText(DateUtils.formatSec(messageResult.getSendCreateTime()));
			message.setText(messageResult.getContent());
		}
		
		
	}

	
	/**
	 * 
	 * @Title: initMessageState 
	 * @Description: 根据消息ID改变消息的状态
	 * @return: void
	 */
	private void initMessageState(String ID) {
		loadingDialog.showDialog();
		Map<String,String> map = new HashMap<String,String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("id", ID+"");
		SimpleRequest<BaseInfoBean> req = new SimpleRequest<BaseInfoBean>(API.MESSAGE_STATE, BaseInfoBean.class, new Listener<BaseInfoBean>() {

			@Override
			public void onResponse(BaseInfoBean response) {
				if(null != response){
					if(response.getStatus() == 0){
					}
				}
				loadingDialog.dismissDialog();
				
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				loadingDialog.showDialog();
				
			}
		}, map);
		UserApplication.getInstance().addToRequestQueue(req);
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
		}
	}

}
