package com.flzc.fanglian.ui.me.mypurse;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.flzc.fanglian.model.AccountWithDraw;
import com.flzc.fanglian.model.AccountWithDraw.Result;
import com.flzc.fanglian.model.BalanceBean;
import com.flzc.fanglian.model.BaseInfoBean;
import com.flzc.fanglian.util.StringUtils;

/**
 * 
 * @ClassName: DrawCashActivity
 * @Description: 提现 提现过程是先请求接口绑卡，得到绑卡的id，然后再次请求接口提现
 * @author: LU
 * @date: 2016-3-4 下午3:12:52
 */
public class DrawCashActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout rl_back;
	private TextView tv_title;
	private TextView tv_right;

	private TextView tv_draw_cash_balance;
	private TextView tv_draw_cash_all;
	private EditText et_draw_cash_money;
	private TextView et_draw_cash_idCard;
	private TextView bt_draw_cash_submit;

	private String balance;
	private LinearLayout ll_select_card;
	
	final int RESULT_CODE=101;
    final int REQUEST_CODE=1;
	private String bank;
	private String cardNum;
	private String bindCardId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw_cash);
		initView();
		initData();
		initListerner();
	}

	private void initView() {
		// 标题
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("提现");
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_right.setText("提现记录");

		tv_draw_cash_balance = (TextView) findViewById(R.id.tv_draw_cash_balance);
		tv_draw_cash_all = (TextView) findViewById(R.id.tv_draw_cash_all);
		// 提现金额
		et_draw_cash_money = (EditText) findViewById(R.id.et_draw_cash_money);
		// 银行卡
		ll_select_card = (LinearLayout) findViewById(R.id.ll_select_card);
		ll_select_card.setOnClickListener(this);
		et_draw_cash_idCard = (TextView) findViewById(R.id.et_draw_cash_idCard);
		bank = UserInfoData.getData("bank","");
		cardNum = UserInfoData.getData("cardNum","");
		bindCardId = UserInfoData.getData("bindCardId","");
		//银行卡号在提现成功后保存，在解绑银行卡后删除
		if(TextUtils.isEmpty(bank) || TextUtils.isEmpty(cardNum)){
			et_draw_cash_idCard.setText("选择银行卡");
		}else{
			et_draw_cash_idCard.setText(bank+"****"+cardNum.substring(cardNum.length()-4, cardNum.length()));
		}
		// 提交
		bt_draw_cash_submit = (TextView) findViewById(R.id.bt_draw_cash_submit);

	}

	private void initData() {

		getBalanceData();
	}

	private void getBalanceData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));

		SimpleRequest<BalanceBean> request = new SimpleRequest<BalanceBean>(
				API.QUERYBALANCE, BalanceBean.class,
				new Listener<BalanceBean>() {
					@Override
					public void onResponse(BalanceBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								balance = StringUtils.intMoney(response
										.getResult().getBalance());
								tv_draw_cash_balance.setText(balance + "元");
							}
						}
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	private void initListerner() {
		// 标题
		rl_back.setOnClickListener(this);
		tv_right.setOnClickListener(this);
		bt_draw_cash_submit.setOnClickListener(this);
		tv_draw_cash_all.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		// 提现记录
		case R.id.tv_right:
			goOthers(DrawCashRecordActivity.class);
			break;
		// 选择银行卡
		case R.id.ll_select_card:
			Intent intent=new Intent(this,DrawCashCardListActivity.class);
	        startActivityForResult(intent, REQUEST_CODE);
			break;
		// 提交
		case R.id.bt_draw_cash_submit:
			submitData();
			break;
		// 全部提现
		case R.id.tv_draw_cash_all:
			drawCashAll();
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if(requestCode==REQUEST_CODE) {
	            if(resultCode==RESULT_CODE) {
	                bank = data.getStringExtra("bank");
	                cardNum = data.getStringExtra("cardNum");
	                bindCardId = data.getStringExtra("id");
	                et_draw_cash_idCard.setText(bank+"****"+cardNum.substring(cardNum.length()-4, cardNum.length()));
	            }
	        }
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 
	 * @Title: drawCashAll
	 * @Description: 提现全部
	 * @return: void
	 */
	private void drawCashAll() {
		et_draw_cash_money.setText(balance + "");
	}

	/**
	 * 
	 * @Title: submitData
	 * @Description: 提交
	 * @return: void
	 */
	private void submitData() {
		boolean submit = true;
		// 提现的金额
		String money = et_draw_cash_money.getText().toString();
		// 银行账号
		String cardId = bindCardId;
		if (StringUtils.isBlank(money)) {
			showTost("提现金额不能为空");
			return;
		}
		if (StringUtils.isNotBlank(money)) {
			if (Double.parseDouble(money) < 10) {
				showTost("提现金额不能小于10");
				//et_draw_cash_money.setText("");
				return;
			}
		}
		try {
			double dMoney = Double.parseDouble(money);
			double mbalance = Double.parseDouble(balance);
			if (dMoney > mbalance) {
				showTost("余额不足");
				return;
				//et_draw_cash_money.setText("");
			}
		} catch (Exception e) {
			showTost("余额不足");
			return;
		}

		if (StringUtils.isBlank(cardId)) {
			showTost("银行账号不能为空");
			submit = false;
		} 

		if (submit) {
			 drawCash(money, cardId, bank);

			//drawCashWithUser(money, cardId, "", "");
		}

	}

	/**
	 * 
	 * @Title: drawCashWithUser
	 * @Description: 用户api提现
	 * @param money
	 * @param cardId
	 * @param identificationId
	 * @param name
	 * @return: void
	 */
	private void drawCashWithUser(String money, String cardId,
			String identificationId, String name) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("amount", money);
		map.put("cardNo", cardId);
		map.put("idNO", identificationId);
		map.put("name", name);

		SimpleRequest<AccountWithDraw> request = new SimpleRequest<AccountWithDraw>(
				API.ACCOUNT_USER_WITH_DRAW, AccountWithDraw.class,
				new Listener<AccountWithDraw>() {
					@Override
					public void onResponse(AccountWithDraw response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								// showTost("申请提现成功");
								Result result = response.getResult();
								String amount = result.getAmount();
								String bindCardId = result.getBindCardId();
								String remark = result.getRemark();
								drawCash(amount, bindCardId, remark);
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
	 * @Title: drawCash
	 * @Description: 向邱杰发起请求提现
	 * @param amount
	 *            提现金额
	 * @param bindCardId
	 *            绑卡id
	 * @param remark
	 *            描述
	 * @return: void
	 */
	private void drawCash(String amount, final String bindCardId, String remark) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("userType", "5");// 5代表用户
		map.put("amount", amount);
		map.put("bindCardId", bindCardId);
		map.put("remark", remark);

		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.ACCOUNT_WITH_DRAW, BaseInfoBean.class,
				new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(BaseInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								// 说明提现成功，刷新一下页面
								showTost(response.getMsg());
								UserInfoData.saveData("bank",bank);
								UserInfoData.saveData("cardNum",cardNum);
								UserInfoData.saveData("bindCardId",bindCardId);
								finish();
								// et_draw_cash_money.setText("");
								// et_draw_cash_idCard.setText("");
								// et_draw_cash_name.setText("");
								// et_draw_cash_identificationId.setText("");
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

}
