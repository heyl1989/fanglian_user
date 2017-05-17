package com.flzc.fanglian.ui.common;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseActivity;

public class SelectPayWayActivity extends BaseActivity implements OnClickListener{
	
	private boolean isUsebalance = false;
	private boolean isUselianalian = false;
	private ImageView balanceIcon;
	private ImageView payicon;
	private TextView pay_hint;
	private ImageView lianlianpayIcon;
	private TextView userBalance;
	private TextView needPay;
	private TextView paySure;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_way);
		initView();
	}

	private void initView() {
		//标题
		TextView title = (TextView)findViewById(R.id.tv_title);
		title.setText("支付方式");
		findViewById(R.id.rl_back).setOnClickListener(this);
		//选择余额支付
		findViewById(R.id.ll_select_balance).setOnClickListener(this);
		userBalance = (TextView)findViewById(R.id.tv_userbalance);
		balanceIcon = (ImageView)findViewById(R.id.img_pay_icon);
		payicon = (ImageView)findViewById(R.id.img_select_payicon);
		//提示语
		pay_hint = (TextView)findViewById(R.id.tv_pay_hint);
		//选择连连支付
		findViewById(R.id.ll_lianlianpay).setOnClickListener(this);
		lianlianpayIcon = (ImageView)findViewById(R.id.img_lianlianpay_select);
		//需支付及确认
		needPay = (TextView)findViewById(R.id.tv_needpay);
		paySure = (TextView)findViewById(R.id.tv_paysure);
		paySure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.ll_select_balance:
			if(isUsebalance){
				balanceIcon.setImageResource(R.drawable.pay_without_yu);
				payicon.setImageResource(R.drawable.pay_no_uese);
			}else{
				balanceIcon.setImageResource(R.drawable.pay_with_yu);
				payicon.setImageResource(R.drawable.pay_uese);
			}
			break;
		case R.id.ll_lianlianpay:
			if(isUselianalian){
				lianlianpayIcon.setImageResource(R.drawable.pay_no_uese);
			}else{
				lianlianpayIcon.setImageResource(R.drawable.pay_uese);
			}
			break;
		case R.id.tv_paysure:
			showTost("确认");
			break;
			
		default:
			break;
		}
		
	}

}
