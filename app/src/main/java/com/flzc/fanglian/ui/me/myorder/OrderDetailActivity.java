package com.flzc.fanglian.ui.me.myorder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.flzc.fanglian.R;
import com.flzc.fanglian.alipay.PayResult;
import com.flzc.fanglian.alipay.PayUtils;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.lianlianpay.BaseHelper;
import com.flzc.fanglian.lianlianpay.Constants;
import com.flzc.fanglian.lianlianpay.LianLianUtils;
import com.flzc.fanglian.lianlianpay.MobileSecurePayer;
import com.flzc.fanglian.lianlianpay.PayOrder;
import com.flzc.fanglian.model.BaseInfoBean;
import com.flzc.fanglian.model.OrderDetailsBean;
import com.flzc.fanglian.model.YiBaoPayInfoBean;
import com.flzc.fanglian.model.OrderDetailsBean.Result;
import com.flzc.fanglian.ui.bidding_activity.AuctionBailActivity;
import com.flzc.fanglian.ui.bidding_activity.PaymentSuccessActivity;
import com.flzc.fanglian.ui.common.PayWebViewActivity;
import com.flzc.fanglian.ui.everyday_special_activity.SpecialPaymentSuccessActivity;
import com.flzc.fanglian.util.LogUtil;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.dialog.CustomDialog;
import com.flzc.fanglian.wxpay.GetPrepayIdTask;
import com.tencent.mm.sdk.openapi.IWXAPI;

public class OrderDetailActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_back;
	private TextView tv_title;

	private ImageView iv_auctionbail_pic;
	private TextView tv_auctionbail_house_name;
	private TextView tv_auctionbail_consult_price;
	private TextView tv_auctionbail_auction_num;
	private TextView tv_auctionbail_address;

	private TextView tv_order_number;
	private TextView tv_create_time;
	private TextView tv_pay_time;
	private TextView tv_pay_way;
	private TextView tv_link_discount;

	private TextView tv_auctionbail_needpay;
	private TextView tv_auctionbail_sure;

	private String master;
	private String orderFlowId;
	private int orderStatus;// 6201==待付款 6202==已付款 6203==已取消 6204==已关闭 6205==已退款
							// 6206==失败

	private String orderNum;// 唯一订单编号
	private String callBackUrl; // 支付时的回调地址
	protected Integer payWay;

	private static final int SDK_PAY_FLAG = 1;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					showTost("支付成功");
					tv_right.setVisibility(View.GONE);
					Intent intent = new Intent();
					intent.putExtra("orderCode", orderNum);
					if (actType == 3902) {
						intent.setClass(OrderDetailActivity.this,
								SpecialPaymentSuccessActivity.class);
					}
					if (actType == 3901) {
						intent.setClass(OrderDetailActivity.this,
								PaymentSuccessActivity.class);
					}
					startActivity(intent);
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						showTost("支付结果确认中");
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						showTost("支付失败");
					}
				}
				break;
			}
			default:
				break;
			}
		};
	};
	private TextView tv_right;
	private TextView tv_auctionbail_auction_price;
	protected int orderId;
	protected String balance;
	protected String payMoney;
	protected int actType;
	private String actState;
	private IWXAPI api;
	private PopupWindow bottomWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me_orderdetail);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void onResume() {
		if (null != tv_right) {
			tv_right.setVisibility(View.GONE);
		}
		ifWxpaySucess();
		ifYiBaoSucess();
		getOrderDetailActivity();
		super.onResume();
	}

	private void initView() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("订单详情");
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_right.setText("取消订单");
		tv_right.setVisibility(View.GONE);

		iv_auctionbail_pic = (ImageView) findViewById(R.id.iv_auctionbail_pic);
		tv_auctionbail_house_name = (TextView) findViewById(R.id.tv_auctionbail_house_name);
		tv_auctionbail_consult_price = (TextView) findViewById(R.id.tv_auctionbail_consult_price);
		tv_auctionbail_auction_price = (TextView) findViewById(R.id.tv_auctionbail_auction_price);
		tv_auctionbail_auction_num = (TextView) findViewById(R.id.tv_auctionbail_auction_num);
		tv_auctionbail_address = (TextView) findViewById(R.id.tv_auctionbail_address);
		tv_order_number = (TextView) findViewById(R.id.tv_order_number);
		tv_create_time = (TextView) findViewById(R.id.tv_create_time);
		tv_pay_time = (TextView) findViewById(R.id.tv_pay_time);
		tv_pay_way = (TextView) findViewById(R.id.tv_pay_way);
		tv_link_discount = (TextView) findViewById(R.id.tv_link_discount);
		tv_auctionbail_needpay = (TextView) findViewById(R.id.tv_auctionbail_needpay);
		tv_auctionbail_sure = (TextView) findViewById(R.id.tv_auctionbail_sure);
		tv_auctionbail_sure.setEnabled(false);
	}

	private void initData() {
		Intent intent = getIntent();
		master = intent.getStringExtra("master");
		orderFlowId = intent.getStringExtra("orderFlowId");
		actState = intent.getStringExtra("actState");
		//getOrderDetailActivity();
	}

	private void getOrderDetailActivity() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("orderFlowId", orderFlowId);
		SimpleRequest<OrderDetailsBean> request = new SimpleRequest<OrderDetailsBean>(
				API.USER_ORDER_DETAIL, OrderDetailsBean.class,
				new Listener<OrderDetailsBean>() {
					@Override
					public void onResponse(OrderDetailsBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								Result result = response.getResult();
								// 余额
								balance = StringUtils.intMoney(result.getBalance());
								orderId = result.getOrderId();
								orderNum = result.getOrderFlow();
								imageLoader.displayImage(master,
										iv_auctionbail_pic, options);
								tv_auctionbail_house_name.setText(result
										.getBuildingName());
								tv_auctionbail_consult_price.setText("参考价：¥"
										+ StringUtils.intMoney(result.getReferPrice()) + "元/㎡");
								actType = result.getActType();
								if (result.getActType() == 3902) {
									// 隐藏竞拍价
									tv_auctionbail_auction_price
											.setVisibility(View.GONE);
									tv_auctionbail_auction_num
											.setVisibility(View.GONE);
								} else {
									tv_auctionbail_auction_price
									.setVisibility(View.VISIBLE);
									tv_auctionbail_auction_num
									.setVisibility(View.VISIBLE);
									tv_auctionbail_auction_num.setText("¥"
											+ result.getAuctionPrice() + "元/㎡");
								}
								tv_auctionbail_address.setText("开发商："
										+ result.getBuilder());
								tv_order_number.setText(result.getOrderFlow());
								tv_create_time.setText(result.getOrderTime());
								tv_pay_time.setText(result.getPayTime());
								switch (result.getPayType()) {
								case 1:
									tv_pay_way.setText("余额支付");
									break;
								case 2:
									tv_pay_way.setText("支付宝支付");
									break;
								case 3:
									tv_pay_way.setText("微信支付");
									break;
								case 4:
									tv_pay_way.setText("连连支付");
									break;
								case 5:
									tv_pay_way.setText("财务代付");
									break;
								case 6:
									tv_pay_way.setText("易宝网银");
									break;
								case 7:
									tv_pay_way.setText("易宝扫码");
									break;
								case 8:
									tv_pay_way.setText("易宝快捷");
									break;
								default:
									tv_pay_way.setText("");
									break;
								}
								tv_link_discount.setText(result.getActName());
								payMoney = StringUtils.intMoney(result.getOrderAmount());
								tv_auctionbail_needpay.setText("共计： ¥"
										+ payMoney + "元");
								// 订单状态
								orderStatus = result.getOrderStatus();
								switch (orderStatus) {
								case 6201:
									if (TextUtils.equals("已结束", actState)) {
										tv_auctionbail_sure
												.setBackgroundColor(Color
														.parseColor("#d3d3d3"));
										tv_auctionbail_sure.setText("已关闭");
									} else {
										tv_auctionbail_sure.setEnabled(true);
										tv_auctionbail_sure
												.setBackgroundColor(Color
														.parseColor("#ee4b4c"));
										tv_auctionbail_sure.setText("付款");
										tv_right.setVisibility(View.VISIBLE);
									}
									break;
								case 6202:
									tv_auctionbail_sure
											.setBackgroundColor(Color
													.parseColor("#d3d3d3"));
									tv_auctionbail_sure.setText("已付款");
									break;
								case 6203:
									tv_auctionbail_sure
											.setBackgroundColor(Color
													.parseColor("#d3d3d3"));
									tv_auctionbail_sure.setText("已取消");
									break;
								case 6204:
									tv_auctionbail_sure
											.setBackgroundColor(Color
													.parseColor("#d3d3d3"));
									tv_auctionbail_sure.setText("已关闭");
									break;
								case 6205:
									tv_auctionbail_sure
											.setBackgroundColor(Color
													.parseColor("#d3d3d3"));
									tv_auctionbail_sure.setText("已退款");
									break;
								case 6206:
									tv_auctionbail_sure
											.setBackgroundColor(Color
													.parseColor("#d3d3d3"));
									tv_auctionbail_sure.setText("失败");
									break;
								}
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
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	private void initListener() {
		rl_back.setOnClickListener(this);
		tv_right.setOnClickListener(this);
		tv_auctionbail_sure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.tv_right:
			showCancelOrderDialog();
			break;
		case R.id.tv_auctionbail_sure:
			if (6201 == orderStatus) {
				showBottomImagePopupwindow();
			}
			break;
		}
	}

	/**
	 * 
	 * @Title: showCancelOrderDialog 
	 * @Description: 取消订单弹框
	 * @return: void
	 */
	private void showCancelOrderDialog() {
		CustomDialog.Builder builder  = new CustomDialog.Builder(this);
		builder.setTitle("取消订单");
		builder.setContent("是否确认取消订单？");
		builder.setNegativeButton("取消", new CustomDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("确定", new CustomDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				cancelOrder();
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	/**
	 * 
	 * @Title: cancelOrder
	 * @Description:取消订单
	 * @return: void
	 */
	private void cancelOrder() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("orderCode", orderNum + "");
		//map.put("status", 6203 + "");
		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.CANCEL_ORDER, BaseInfoBean.class,
				new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(BaseInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								tv_right.setVisibility(View.GONE);
								getOrderDetailActivity();
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
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);

	}

	// 弹出一个popuwindow选择支付方式
	private void showBottomImagePopupwindow() {
		View view = getLayoutInflater().inflate(
				R.layout.item_auctionbail_popuwindow, null);

		final LinearLayout ll_popup = (LinearLayout) view
				.findViewById(R.id.ll_auctionbail_popup);
		if(null == bottomWindow){
			bottomWindow = new PopupWindow(getApplicationContext());
		}
		// 设置破普window的属性
		bottomWindow.setWidth(LayoutParams.MATCH_PARENT);
		bottomWindow.setHeight(LayoutParams.WRAP_CONTENT);
		bottomWindow.setBackgroundDrawable(new BitmapDrawable());
		bottomWindow.setFocusable(true);
		bottomWindow.setOutsideTouchable(true);
		bottomWindow.setContentView(view);
		bottomWindow.setAnimationStyle(R.style.anim_menu_bottombar);
		bottomWindow
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		bottomWindow.showAtLocation(tv_auctionbail_sure, Gravity.BOTTOM, 0, 0);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
		bottomWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
				payWay = null;
			}
		});

		RelativeLayout parent = (RelativeLayout) view
				.findViewById(R.id.rl_auctionbail_parent);
		RelativeLayout rl_auctionbail_pay_weixin = (RelativeLayout) view
				.findViewById(R.id.rl_auctionbail_pay_weixin);
		RelativeLayout rl_auctionbail_pay_zhifubao = (RelativeLayout) view
				.findViewById(R.id.rl_auctionbail_pay_zhifubao);
		RelativeLayout rl_auctionbail_pay_lianlian = (RelativeLayout) view
				.findViewById(R.id.rl_auctionbail_pay_lianlian);
		RelativeLayout rl_auctionbail_pay_yibao = (RelativeLayout) view
				.findViewById(R.id.rl_auctionbail_pay_yibao);
		// 余额
		LinearLayout rl_auctionbail_pay_yue = (LinearLayout) view
				.findViewById(R.id.rl_auctionbail_pay_yue);
		TextView tv_balance = (TextView) view.findViewById(R.id.tv_balance);
		ImageView iv_auctionbail_yue = (ImageView) view
				.findViewById(R.id.iv_auctionbail_yue);
		tv_balance.setText("账户余额：" + balance + "元");
		if (Double.parseDouble(payMoney) > Double.parseDouble(balance)) {
			iv_auctionbail_yue.setImageResource(R.drawable.pay_with_yu);
			rl_auctionbail_pay_yue.setEnabled(false);
		}

		final ImageView iv_auctionbail_weixin_pickon = (ImageView) view
				.findViewById(R.id.iv_auctionbail_weixin_pickon);
		final ImageView iv_auctionbail_zhifubao_pickon = (ImageView) view
				.findViewById(R.id.iv_auctionbail_zhifubao_pickon);
		final ImageView iv_auctionbail_lianlian_pickon = (ImageView) view
				.findViewById(R.id.iv_auctionbail_lianlian_pickon);
		final ImageView iv_auctionbail_yue_pickon = (ImageView) view
				.findViewById(R.id.iv_auctionbail_yue_pickon);
		final ImageView iv_auctionbail_yibao_pickon = (ImageView) view
				.findViewById(R.id.iv_auctionbail_yibao_pickon);

		ImageView iv_auctionbail_sure = (ImageView) view
				.findViewById(R.id.iv_auctionbail_sure);
		TextView tv_auctionbail_cancle = (TextView) view
				.findViewById(R.id.tv_auctionbail_cancle);

		// 取消操作
		tv_auctionbail_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				bottomWindow.dismiss();
				ll_popup.clearAnimation();
			}
		});

		// 微信支付方式
		rl_auctionbail_pay_weixin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				payWay = 1;
				iv_auctionbail_weixin_pickon.setVisibility(View.VISIBLE);
				iv_auctionbail_zhifubao_pickon.setVisibility(View.GONE);
				iv_auctionbail_lianlian_pickon.setVisibility(View.GONE);
				iv_auctionbail_yue_pickon.setVisibility(View.INVISIBLE);
				iv_auctionbail_yibao_pickon.setVisibility(View.GONE);
				ll_popup.clearAnimation();
			}
		});

		// 支付宝支付
		rl_auctionbail_pay_zhifubao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				payWay = 2;
				iv_auctionbail_weixin_pickon.setVisibility(View.GONE);
				iv_auctionbail_zhifubao_pickon.setVisibility(View.VISIBLE);
				iv_auctionbail_lianlian_pickon.setVisibility(View.GONE);
				iv_auctionbail_yue_pickon.setVisibility(View.INVISIBLE);
				iv_auctionbail_yibao_pickon.setVisibility(View.GONE);
				ll_popup.clearAnimation();
			}
		});

		// 连连支付
		rl_auctionbail_pay_lianlian.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				payWay = 3;
				iv_auctionbail_weixin_pickon.setVisibility(View.GONE);
				iv_auctionbail_zhifubao_pickon.setVisibility(View.GONE);
				iv_auctionbail_lianlian_pickon.setVisibility(View.VISIBLE);
				callBackUrl = Constant.LL_CALLBACK;
				iv_auctionbail_yue_pickon.setVisibility(View.INVISIBLE);
				iv_auctionbail_yibao_pickon.setVisibility(View.GONE);
				ll_popup.clearAnimation();
			}
		});
		// 余额支付
		rl_auctionbail_pay_yue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				payWay = 4;
				iv_auctionbail_weixin_pickon.setVisibility(View.GONE);
				iv_auctionbail_zhifubao_pickon.setVisibility(View.GONE);
				iv_auctionbail_lianlian_pickon.setVisibility(View.GONE);
				iv_auctionbail_yue_pickon.setVisibility(View.VISIBLE);
				iv_auctionbail_yibao_pickon.setVisibility(View.GONE);
				ll_popup.clearAnimation();
			}
		});
		//易宝
		rl_auctionbail_pay_yibao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				payWay = 5;
				iv_auctionbail_weixin_pickon.setVisibility(View.GONE);
				iv_auctionbail_zhifubao_pickon.setVisibility(View.GONE);
				iv_auctionbail_lianlian_pickon.setVisibility(View.GONE);
				iv_auctionbail_yue_pickon.setVisibility(View.INVISIBLE);
				iv_auctionbail_yibao_pickon.setVisibility(View.VISIBLE);
				ll_popup.clearAnimation();
			}
		});

		// 确认按钮
		iv_auctionbail_sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (payWay == null) {
					showTost("请选择支付方式");
					return;
				}
				switch (payWay) {
				case 1:
					wxPay();
					break;
				case 2:
					aliPay();
					break;
				case 3:
					lianlianPay();
					break;
				case 4:
					yuEpay();
					break;
				case 5:
					yiBaoPay();
					break;
				default:
					showTost("请选择支付方式");
					break;
				}
				bottomWindow.dismiss();
				ll_popup.clearAnimation();
			}
		});
	}

	/**
	 * 
	 * @Title: yiBaoPay 
	 * @Description: 易宝支付
	 * @return: void
	 */
	protected void yiBaoPay() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("orderAmount", payMoney + "");
		map.put("orderId", orderNum);
		SimpleRequest<YiBaoPayInfoBean> request = new SimpleRequest<YiBaoPayInfoBean>(
				API.YIBAO_PAY, YiBaoPayInfoBean.class,
				new Listener<YiBaoPayInfoBean>() {
					@Override
					public void onResponse(YiBaoPayInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								if(null != response.getResult()){
									Intent intent = new Intent(OrderDetailActivity.this,
											PayWebViewActivity.class);
									intent.putExtra("yibaoPay", response.getResult().getPayUrl()+"");
									startActivity(intent);
								}
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
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
		
	}
	/**
	 * 
	 * @Title: ifYiBaoSucess 
	 * @Description: 易宝支付回调
	 * @return: void
	 */
	private void ifYiBaoSucess() {
		if(!"YIBAORESULT".equals(UserInfoData.getData("YIBAORESULT", ""))){
			return;
		}else{
			UserInfoData.clearKey("YIBAORESULT");
		}
		String tokenId = UserInfoData.getData(Constant.TOKEN, "");
		SimpleRequest<YiBaoPayInfoBean> request = new SimpleRequest<YiBaoPayInfoBean>(
				API.CHECK_ORDER+"?tokenId="+tokenId+"&orderId="+orderNum, YiBaoPayInfoBean.class,
				new Listener<YiBaoPayInfoBean>() {
					@Override
					public void onResponse(YiBaoPayInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								if (0 == response.getStatus()) {
									showTost("支付成功");
									tv_right.setVisibility(View.GONE);
									Intent intent = new Intent();
									intent.putExtra("orderCode", orderNum);
									if (actType == 3902) {
										intent.setClass(OrderDetailActivity.this,
												SpecialPaymentSuccessActivity.class);
									}
									if (actType == 3901) {
										intent.setClass(OrderDetailActivity.this,
												PaymentSuccessActivity.class);
									}
									startActivity(intent);
								} else {
									showTost("快捷支付支付失败");
								}
							} else {
								showTost(response.getMsg());
							}
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
					}
				});
		UserApplication.getInstance().addToRequestQueue(request);
	}

	/**
	 * 
	 * @Title: yuEpay
	 * @Description: 余额支付
	 * @return: void
	 */
	protected void yuEpay() {
		// TODO Auto-generated method stub
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("amount", payMoney + "");
		map.put("orderId", orderNum);
		map.put("remark", "余额支付描述");// 目前只有进竞拍进这个页面
		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.BANLANCEPAY, BaseInfoBean.class,
				new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(BaseInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								showTost("支付成功");
								tv_right.setVisibility(View.GONE);
								Intent intent = new Intent();
								intent.putExtra("orderCode", orderNum);
								if (actType == 3902) {
									intent.setClass(OrderDetailActivity.this,
											SpecialPaymentSuccessActivity.class);
								}
								if (actType == 3901) {
									intent.setClass(OrderDetailActivity.this,
											PaymentSuccessActivity.class);
								}
								startActivity(intent);
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
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	/**
	 * 
	 * @Title: aliPay
	 * @Description: 支付宝支付请求
	 * @return: void
	 */
	protected void aliPay() {
		if (TextUtils.isEmpty(Constant.PARTNER)
				|| TextUtils.isEmpty(Constant.RSA_PRIVATE)
				|| TextUtils.isEmpty(Constant.SELLER)) {
			new AlertDialog.Builder(this)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									finish();
								}
							}).show();
			return;
		}
		String orderInfo = PayUtils.getOrderInfo(orderNum, payMoney);

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
				PayTask alipay = new PayTask(OrderDetailActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();

	}

	/**
	 * 
	 * @Title: wxPay
	 * @Description: 微信支付
	 * @return: void
	 */
	protected void wxPay() {
		GetPrepayIdTask wxPay = new GetPrepayIdTask(this);
		wxPay.setOut_trade_no(orderNum);
		wxPay.setAmount(Double.parseDouble(payMoney));
		wxPay.execute();
	}
	/**
	 * 
	 * @Title: ifWxpaySucess 
	 * @Description: 微信支付成功回调
	 * @return: void
	 */
	private void ifWxpaySucess() {
		String errCode = UserInfoData.getData("wxPay", "3");
		LogUtil.e("wxPay=20", errCode);
		int ispaySucess = Integer.parseInt(errCode);
		switch (ispaySucess) {
		case 0:
			// 微信支付成功
			showTost("支付成功");
			tv_right.setVisibility(View.GONE);
			Intent intent1 = new Intent();
			intent1.putExtra("orderCode", orderNum);
			if (actType == 3902) {
				intent1.setClass(OrderDetailActivity.this,
						SpecialPaymentSuccessActivity.class);
			}
			if (actType == 3901) {
				intent1.setClass(OrderDetailActivity.this,
						PaymentSuccessActivity.class);
			}
			startActivity(intent1);
			break;
		case -2:
			showTost("用户取消支付");
			break;
		case -1:
			showTost("支付失败");
			break;
		default:
			break;
		}
		UserInfoData.saveData("wxPay", "3");
	}
	
	/**
	 * 
	 * @Title: lianlianPay
	 * @Description: 连连支付
	 * @return: void
	 */
	protected void lianlianPay() {
		PayOrder order = LianLianUtils.constructGesturePayOrder(orderNum,payMoney);
		String content4Pay = BaseHelper.toJSONString(order);
		// 关键 content4Pay 用于提交到支付SDK的订单支付串，如遇到签名错误的情况，请将该信息帖给我们的技术支持
		MobileSecurePayer msp = new MobileSecurePayer();
		boolean bRet = msp.pay(content4Pay, lianlianHandler, Constants.RQF_PAY,
				this, false);
	}

	private Handler lianlianHandler = createHandler();
	private Handler createHandler() {
		return new Handler() {
			public void handleMessage(Message msg) {
				String strRet = (String) msg.obj;
				switch (msg.what) {
				case Constants.RQF_PAY: {
					JSONObject objContent = BaseHelper.string2JSON(strRet);
					String retCode = objContent.optString("ret_code");
					String retMsg = objContent.optString("ret_msg");

					// 成功
					if (Constants.RET_CODE_SUCCESS.equals(retCode)) {
						// TODO 卡前置模式返回的银行卡绑定协议号，用来下次支付时使用，此处仅作为示例使用。正式接入时去掉
						// if (pay_type_flag == 1) {
						// TextView tv_agree_no = (TextView)
						// findViewById(R.id.tv_agree_no);
						// tv_agree_no.setVisibility(View.VISIBLE);
						// tv_agree_no.setText(objContent.optString(
						// "agreementno", ""));
						// }
						tv_right.setVisibility(View.GONE);
						Intent intent = new Intent();
						intent.putExtra("orderCode", orderNum);
						if (actType == 3902) {
							intent.setClass(OrderDetailActivity.this,
									SpecialPaymentSuccessActivity.class);
						}
						if (actType == 3901) {
							intent.setClass(OrderDetailActivity.this,
									PaymentSuccessActivity.class);
						}
						startActivity(intent);

						// BaseHelper.showDialog(BiddingConfirmActivity.this,
						// "提示",
						// "支付成功，交易状态码：" + retCode,
						// android.R.drawable.ic_dialog_alert);
					} else if (Constants.RET_CODE_PROCESS.equals(retCode)) {
						// TODO 处理中，掉单的情形
						String resulPay = objContent.optString("result_pay");
						if (Constants.RESULT_PAY_PROCESSING
								.equalsIgnoreCase(resulPay)) {
							BaseHelper.showDialog(OrderDetailActivity.this,
									"提示", objContent.optString("ret_msg")
											+ "交易状态码：" + retCode,
									android.R.drawable.ic_dialog_alert);
						}

					} else {
						// TODO
						BaseHelper.showDialog(OrderDetailActivity.this, "提示",
								retMsg + "，交易状态码:" + retCode,
								android.R.drawable.ic_dialog_alert);
					}
				}
					break;
				}
				super.handleMessage(msg);
			}
		};
	}

}
