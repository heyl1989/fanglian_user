package com.flzc.fanglian.ui.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.flzc.fanglian.R;
import com.flzc.fanglian.alipay.PayUtils;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseFragment;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.QueryUserBean;
import com.flzc.fanglian.model.QueryUserBean.Result.User;
import com.flzc.fanglian.ui.login_register.LoginActivity;
import com.flzc.fanglian.ui.me.FangLianQuanActivity;
import com.flzc.fanglian.ui.me.MyInfoActivity;
import com.flzc.fanglian.ui.me.MyParticipateActivity;
import com.flzc.fanglian.ui.me.SettingMainActivity;
import com.flzc.fanglian.ui.me.myorder.OrderListActivity;
import com.flzc.fanglian.ui.me.mypurse.MyPurseActivity;
import com.flzc.fanglian.view.CircleImageView;

/**
 * 
 * @ClassName: MeFragment
 * @Description: 我
 * @author: LU
 * @date: 2016-3-3 下午6:50:23
 */
public class MeFragment extends BaseFragment implements OnClickListener {

	private View view;
	private RelativeLayout rl_back;
	private TextView tv_title;
	private RelativeLayout rl_head;
	private TextView tv_username;
	private CircleImageView img_headview;
	private FrameLayout ll_flquan;
	private LinearLayout ll_mypurse;
	private LinearLayout ll_myorder;
	private LinearLayout ll_activity;
	private LinearLayout ll_setting;
	private ImageView imgbt_ercode;
	private ImageView arrow;
 	private TextView login_register;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_me, container, false);
		initView();
		initListener();
		return view;
	}
	

	@Override
	public void onResume() {
		super.onResume();
		//判断是否登录，显示的ui风格不一样
		if(UserInfoData.isSingIn()){
			initData();
			login_register.setVisibility(View.GONE);
			arrow.setVisibility(View.VISIBLE);
			imgbt_ercode.setVisibility(View.VISIBLE);
		}else{
			img_headview.setImageResource(R.drawable.no_login);
			tv_username.setText("您还没有登录呢~~");
			login_register.setVisibility(View.VISIBLE);
		    arrow.setVisibility(View.GONE);
			imgbt_ercode.setVisibility(View.GONE);
		}
	}
	
	

	private void initView() {
		// 隐藏左边返回
		rl_back = (RelativeLayout) view.findViewById(R.id.rl_back);
		rl_back.setVisibility(View.GONE);
		// 标题
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_title.setText("我");
		// 头像栏
		rl_head = (RelativeLayout) view.findViewById(R.id.rl_head);
		img_headview = (CircleImageView) view.findViewById(R.id.img_headview);
		tv_username = (TextView) view.findViewById(R.id.tv_username);
		imgbt_ercode = (ImageView) view.findViewById(R.id.imgbt_ercode);
		arrow = (ImageView) view.findViewById(R.id.arrow);
		// 三个按钮栏
		ll_flquan = (FrameLayout) view.findViewById(R.id.ll_flquan);
		//img_red_dot = (ImageView) view.findViewById(R.id.img_red_dot);
		ll_mypurse = (LinearLayout) view.findViewById(R.id.ll_mypurse);
		ll_myorder = (LinearLayout) view.findViewById(R.id.ll_myorder);
		// 4个条目
		ll_activity = (LinearLayout) view.findViewById(R.id.ll_activity);
		ll_setting = (LinearLayout) view.findViewById(R.id.ll_setting);
		//未登陆时候的显示文字
		login_register = (TextView) view.findViewById(R.id.login_register);
	}
	
	private void initData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));

		SimpleRequest<QueryUserBean> request = new SimpleRequest<QueryUserBean>(
				API.QUERYUSERBYTOKENID, QueryUserBean.class,
				new Listener<QueryUserBean>() {
					@Override
					public void onResponse(QueryUserBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								User user = response.getResult().getUser();
								if (user != null) {
									imageLoader.displayImage(user.getHeadUrl(),
											img_headview,options);
									//将用户的头像保存起来
									UserInfoData.saveData(Constant.HEAD_URL, user.getHeadUrl());
									tv_username.setText(user.getNickName()+"");
								}
							}else{
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	private void initListener() {
		rl_head.setOnClickListener(this);
		// 中间横向3个按钮
		ll_flquan.setOnClickListener(this);
		ll_mypurse.setOnClickListener(this);
		ll_myorder.setOnClickListener(this);
		// 4个条目
		ll_activity.setOnClickListener(this);
		ll_setting.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_head:
			if (UserInfoData.isSingIn()) {
				goOthers(MyInfoActivity.class);
			} else {
				goOthers(LoginActivity.class);
			}
			break;
		case R.id.ll_flquan:
			if (UserInfoData.isSingIn()) {
				goOthers(FangLianQuanActivity.class);
			} else {
				goOthers(LoginActivity.class);
			}
			break;
		case R.id.ll_mypurse:
			if (UserInfoData.isSingIn()) {
				goOthers(MyPurseActivity.class);
			} else {
				goOthers(LoginActivity.class);
			}
			break;
		case R.id.ll_myorder:
			if (UserInfoData.isSingIn()) {
				goOthers(OrderListActivity.class);
			} else {
				goOthers(LoginActivity.class);
			}
			break;
		case R.id.ll_activity:
			// showTost("我参与的活动");
			//做测试
			//goOthers(QuestionActivity.class);
			if (UserInfoData.isSingIn()) {
				goOthers(MyParticipateActivity.class);
			} else {
				goOthers(LoginActivity.class);
			}
			break;
		case R.id.ll_setting:
			//设置
//			GetPrepayIdTask wxPay = new GetPrepayIdTask(mActivity);
//			wxPay.setOut_trade_no("20160520140901139010343");
//			wxPay.setAmount(0.01);
//			wxPay.execute();
			//aliPay();
			goOthers(SettingMainActivity.class);
			break;
		}
	}
	
	private void aliPay(){
		String orderInfo = PayUtils.getOrderInfo("20160427162141539036799", "0.01");

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = PayUtils.sign(orderInfo);
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ PayUtils.getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(mActivity);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo, true);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

}
