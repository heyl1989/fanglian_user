package com.flzc.fanglian.ui.everyday_special_activity;

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
import com.flzc.fanglian.model.CreateOrderBean;
import com.flzc.fanglian.model.CreateOrderBean.CreateOrderResult;
import com.flzc.fanglian.model.DaySpecialActivityBean;
import com.flzc.fanglian.model.DaySpecialActivityBean.Result.DaySpecial;
import com.flzc.fanglian.model.DaySpecialHouseInfoBean;
import com.flzc.fanglian.model.YiBaoPayInfoBean;
import com.flzc.fanglian.ui.bidding_activity.PaymentSuccessActivity;
import com.flzc.fanglian.ui.common.PayWebViewActivity;
import com.flzc.fanglian.ui.me.myorder.OrderDetailActivity;
import com.flzc.fanglian.util.LogUtil;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.wxpay.GetPrepayIdTask;
import com.tencent.mm.sdk.openapi.IWXAPI;

/**
 * 
 * @ClassName: ScaleBuyingCashActivity
 * @Description: 确认需要缴纳的保证金
 * @author: LU
 * @date: 2016-3-7 下午8:00:00
 */
public class ScaleBuyingCashActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_back;
	private TextView tv_title;
	private TextView tv_auctionbail_sure;

	private static final int SDK_PAY_FLAG = 1;
	
	public static ScaleBuyingCashActivity scaleBuyingCashActivity;

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
					Intent intent = new Intent(ScaleBuyingCashActivity.this,SpecialPaymentSuccessActivity.class);
					intent.putExtra("orderCode", orderNum);
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
	private int payPay;
	private IWXAPI api;
	private String tokenId;
	private ImageView houseImg;
	private TextView houseName;
	private TextView atyPrice;
	private TextView houseAddress;
	private TextView totalPay;
	private TextView remainPay;
	protected String payMoney;
	protected String orderNum;//订单号
	private TextView marcketPrice;
	protected String mBalance;
	private PopupWindow bottomWindow;
	private String houseSourceId;
	private String activityPoolId;
	private String buildingId;
	private String activityId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_everyday_special_cash);
		scaleBuyingCashActivity = this;
		tokenId = UserInfoData.getData(Constant.TOKEN, "");
		Intent intent = getIntent();
		if(null != intent){
			houseSourceId = intent.getStringExtra("houseSourceId");
			buildingId = intent.getStringExtra("buildingId");
			activityPoolId = intent.getStringExtra("activityPoolId");
			activityId = intent.getStringExtra("activityId");
		}
		initView();
		initData();
		//initHouseData();
		initListener();
	}
	
	@Override
	protected void onResume() {
		initHouseData();
		ifWxpaySucess();
		ifYiBaoSucess();
		super.onResume();
	}

	private void initView() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("抢购保证金");
		// 内容
		houseImg = (ImageView) findViewById(R.id.iv_auctionbail_pic);
		houseName = (TextView) findViewById(R.id.tv_auctionbail_house_name);
		//参考价
		marcketPrice = (TextView) findViewById(R.id.tv_auctionbail_consult_price);
		atyPrice = (TextView) findViewById(R.id.tv_auctionbail_auction_num);
		houseAddress = (TextView) findViewById(R.id.tv_auctionbail_address);
		totalPay = (TextView) findViewById(R.id.tv_auctionbail_deposite_num);
		remainPay = (TextView) findViewById(R.id.tv_auctionbail_needpay);
		// 确认
		tv_auctionbail_sure = (TextView) findViewById(R.id.tv_specialbail_sure);
		tv_auctionbail_sure.setEnabled(false);
	}

	/**
	 * 
	 * @Title: initData
	 * @Description: 请求天天特价详细信息数据
	 * @return: void
	 */
	private void initData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildingId",buildingId);
		map.put("activityPoolId",activityPoolId);
		map.put("tokenId", tokenId);
		SimpleRequest<DaySpecialActivityBean> req = new SimpleRequest<DaySpecialActivityBean>(
				API.QUERY_DAY_SPECIAL_ACTIVITY, DaySpecialActivityBean.class,
				new Listener<DaySpecialActivityBean>() {
					@Override
					public void onResponse(DaySpecialActivityBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								DaySpecial result = response.getResult()
										.getDaySpecial();
								imageLoader.displayImage(response.getResult().getBuilding().getMaster(),
										houseImg, options);
								houseName.setText(response.getResult().getBuilding().getName());
								//活动价
								String activityPrice =  StringUtils.intMoney(result.getActivityPrice());
								try {
									double price = Double.parseDouble(activityPrice);
									atyPrice.setText("¥" + price/10000+ "万");
								} catch (Exception e) {
									showTost("数据错误");
								}
								// houseAddress.setText(result.get);
								mBalance = StringUtils.intMoney(result.getBalance());
								payMoney = StringUtils.intMoney(result.getDeposit());
								totalPay.setText(payMoney + "元");
								remainPay.setText("需支付：¥" + payMoney
										+ "元");

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
						showTost(getResources().getString(R.string.net_error));
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);

	}
	/**
	 * 
	 * @Title: initData
	 * @Description: 天天特价房源信息
	 * @return: void
	 */
	// “payStatus”:”0”,//0为未登录、1为未缴费、2为已缴费
	private void initHouseData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildingId", buildingId);
		map.put("houseSourceId",  houseSourceId);
		map.put("tokenId", tokenId);
		map.put("activityPoolId", activityPoolId);
		SimpleRequest<DaySpecialHouseInfoBean> req = new SimpleRequest<DaySpecialHouseInfoBean>(
				API.QUERY_HOUSE_SOURCE, DaySpecialHouseInfoBean.class,
				new Listener<DaySpecialHouseInfoBean>() {
					@Override
					public void onResponse(DaySpecialHouseInfoBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								//参考价
								String totalPrice = StringUtils.intMoney(response.getResult().getTotalPrice());
								marcketPrice.setText("参考价："+"¥" +totalPrice+"万");
								houseAddress.setText(response.getResult().getAddress());
								if(TextUtils.equals(response.getResult().getPayStatus(), "2")){
									tv_auctionbail_sure.setText("已支付");
									tv_auctionbail_sure.setBackgroundColor(Color.parseColor("#999999"));
									tv_auctionbail_sure.setEnabled(false);
								}else{
									tv_auctionbail_sure.setText("确认");
									tv_auctionbail_sure.setBackgroundColor(Color.parseColor("#ee4b4c"));
									tv_auctionbail_sure.setEnabled(true);
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
					}
				} ,map);
		UserApplication.getInstance().addToRequestQueue(req);

	}

	private void initListener() {
		rl_back.setOnClickListener(this);
		tv_auctionbail_sure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.tv_specialbail_sure:
			// 支付之前 首先先创建订单
			generatedOrder(payMoney + "");
			break;

		}
	}

	// 服务器生成支付订单
	private void generatedOrder(String amount) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", tokenId);
		map.put("amount", amount);
		map.put("summaryId", activityPoolId);
		map.put("orderType", "3902");

		SimpleRequest<CreateOrderBean> request = new SimpleRequest<CreateOrderBean>(
				API.CREATE_ACTIVITY_ORDER, CreateOrderBean.class,
				new Listener<CreateOrderBean>() {
					@Override
					public void onResponse(CreateOrderBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								CreateOrderResult result = response.getResult();
								orderNum = result.getOrderFlowId();
								// 需要 弹出一个popuwindow选择支付方式
								showBottomImagePopupwindow();
							} else {
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
				} ,map);
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
				payPay = 0;
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
		//余额
		LinearLayout rl_auctionbail_pay_yue = (LinearLayout) view
				.findViewById(R.id.rl_auctionbail_pay_yue);
		TextView tv_balance = (TextView)view.findViewById(R.id.tv_balance);
		ImageView iv_auctionbail_yue = (ImageView)view.findViewById(R.id.iv_auctionbail_yue);
		tv_balance.setText("账户余额："+mBalance+"元");
		if(Double.parseDouble(payMoney) > Double.parseDouble(mBalance)){
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
				payPay = 1;
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
				payPay = 2;
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
				payPay = 3;
				iv_auctionbail_weixin_pickon.setVisibility(View.GONE);
				iv_auctionbail_zhifubao_pickon.setVisibility(View.GONE);
				iv_auctionbail_lianlian_pickon.setVisibility(View.VISIBLE);
				iv_auctionbail_yue_pickon.setVisibility(View.INVISIBLE);
				iv_auctionbail_yibao_pickon.setVisibility(View.GONE);
				ll_popup.clearAnimation();
			}
		});
		//余额支付
		rl_auctionbail_pay_yue.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				payPay = 4;
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
				payPay = 5;
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
				if(payPay == 0){
					showTost("请选择支付方式");
					return;
				}
				switch (payPay) {
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
									Intent intent = new Intent(ScaleBuyingCashActivity.this,
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
								tv_auctionbail_sure.setText("已支付");
								tv_auctionbail_sure.setBackgroundColor(Color.parseColor("#999999"));
								showTost("支付成功");
								Intent intent = new Intent(ScaleBuyingCashActivity.this,SpecialPaymentSuccessActivity.class);
								intent.putExtra("orderCode", orderNum);
								startActivity(intent);
							} else {
								showTost("快捷支付支付失败");
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
		map.put("amount", payMoney+"");
		map.put("orderId", orderNum);
		map.put("remark", "余额支付描述");// 目前只有进竞拍进这个页面
		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.BANLANCEPAY, BaseInfoBean.class,
				new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(BaseInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								tv_auctionbail_sure.setText("已支付");
								tv_auctionbail_sure.setBackgroundColor(Color.parseColor("#999999"));
								showTost("支付成功");
								Intent intent = new Intent(ScaleBuyingCashActivity.this,SpecialPaymentSuccessActivity.class);
								intent.putExtra("orderCode", orderNum);
								startActivity(intent);
							} else {
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
				} ,map);
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
		//orderNum, payMoney+""
		String orderInfo = PayUtils.getOrderInfo(orderNum, payMoney+"");

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
				PayTask alipay = new PayTask(ScaleBuyingCashActivity.this);
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
			tv_auctionbail_sure.setText("已支付");
			tv_auctionbail_sure.setBackgroundColor(Color.parseColor("#999999"));
			showTost("支付成功");
			Intent intent = new Intent(ScaleBuyingCashActivity.this,SpecialPaymentSuccessActivity.class);
			intent.putExtra("orderCode", orderNum);
			startActivity(intent);
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
		PayOrder order = LianLianUtils.constructGesturePayOrder(orderNum,payMoney+"");
		LogUtil.e("orderNum", orderNum);
		String content4Pay = BaseHelper.toJSONString(order);
		// 关键 content4Pay 用于提交到支付SDK的订单支付串，如遇到签名错误的情况，请将该信息帖给我们的技术支持
		MobileSecurePayer msp = new MobileSecurePayer();
		boolean bRet = msp.pay(content4Pay, lianlianHandler,
				Constants.RQF_PAY, this, false);

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
						Intent intent = new Intent(ScaleBuyingCashActivity.this,SpecialPaymentSuccessActivity.class);
						intent.putExtra("orderCode", orderNum);
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
							BaseHelper.showDialog(ScaleBuyingCashActivity.this,
									"提示", objContent.optString("ret_msg")
											+ "交易状态码：" + retCode,
									android.R.drawable.ic_dialog_alert);
						}

					} else {
						// TODO 失败
						BaseHelper.showDialog(ScaleBuyingCashActivity.this, "提示",
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
