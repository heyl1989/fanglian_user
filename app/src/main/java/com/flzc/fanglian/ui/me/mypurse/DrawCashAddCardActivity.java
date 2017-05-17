package com.flzc.fanglian.ui.me.mypurse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
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
import com.flzc.fanglian.model.BankBean;
import com.flzc.fanglian.model.BankBean.Result;
import com.flzc.fanglian.model.MessageBean;
import com.flzc.fanglian.ui.adapter.BankListPOPAdapter;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.dialog.CustomDialog;
import com.flzc.fanglian.view.dialog.CustomDialog.Builder;

public class DrawCashAddCardActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_back;
	private TextView tv_title;
	private EditText et_card_name;
	private EditText et_card_identificationId;
	private EditText et_card_number;
	private LinearLayout ll_select_bank;
	private PopupWindow popupWindow;
	private ImageView img_delete_button;
	private TextView bankName;
	private ArrayList<Result> bank = new ArrayList<Result>();
	private TextView bindcard_commit;
	private EditText et_bindcard_phone;
	private String bankId;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypurse_addcard);
		initView();
		initData();
	}

	/**
	 * 
	 * @Title: initView
	 * @Description: 初始化布局
	 * @return: void
	 */
	private void initView() {
		// 标题
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("绑定银行卡");
		// 姓名
		et_card_name = (EditText) findViewById(R.id.et_card_name);
		et_card_name.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String editable = et_card_name.getText().toString();
				String str = StringUtils.isNumber(editable.toString());
				if (!editable.equals(str)) {
					showTost("只支持汉字或字母");
					et_card_name.setText(str);
					et_card_name.setSelection(str.length()); // 光标置后
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		// 身份证号
		et_card_identificationId = (EditText) findViewById(R.id.et_card_identificationId);
//		et_card_identificationId
//				.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//					@Override
//					public void onFocusChange(View v, boolean hasFocus) {
//						if (!hasFocus) {
//							if (et_card_identificationId.getText().toString()
//									.trim().length() < 18) {
//								et_card_identificationId.setText("");
//								showTost("身份证号有误");
//							}
//						}
//					}
//				});
		//选择银行
		ll_select_bank = (LinearLayout)findViewById(R.id.ll_select_bank);
		ll_select_bank.setOnClickListener(this);
		bankName = (TextView)findViewById(R.id.tv_card_selectbank);
		// 银行卡
		et_card_number = (EditText) findViewById(R.id.et_card_number);
//		et_card_number
//				.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//					@Override
//					public void onFocusChange(View v, boolean hasFocus) {
//						if (!hasFocus) {
//							if (et_card_number.getText().toString().trim()
//									.length() < 15) {
//								et_card_number.setText("");
//								showTost("银行卡号有误");
//							}
//						}
//					}
//				});
		//电话号
		et_bindcard_phone = (EditText) findViewById(R.id.et_bindcard_phone);
		et_bindcard_phone.setText(UserInfoData.getData(Constant.PHONE, ""));
		//删除
		img_delete_button = (ImageView)findViewById(R.id.img_delete_button);
		img_delete_button.setOnClickListener(this);
		//提交按钮
		bindcard_commit = (TextView)findViewById(R.id.tv_bindcard_commit);
		bindcard_commit.setOnClickListener(this);

	}
	
	/**
	 * 
	 * @Title: initData 
	 * @Description: 查询银行卡类型列表
	 * @return: void
	 */
	private void initData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));

		SimpleRequest<BankBean> request = new SimpleRequest<BankBean>(
				API.QUERYBANKCARD, BankBean.class,
				new Listener<BankBean>() {
					@Override
					public void onResponse(BankBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								bank.addAll(response.getResult());
							}
						}
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			showbackDialog();
			break;
		case R.id.ll_select_bank:
			showselectBankPOP();
			break;
		case R.id.img_delete_button:
			et_card_number.setText("");
			break;
		case R.id.tv_bindcard_commit:
			//提交
			String name = et_card_name.getText().toString().trim();
			String idnum = et_card_identificationId.getText().toString().trim();
			String cardnumber = et_card_number.getText().toString().trim();
			String phone = et_bindcard_phone.getText().toString().trim();
			if(TextUtils.isEmpty(name)){
				showTost("请输入姓名");
			}else if(TextUtils.isEmpty(idnum)){
				showTost("请输入身份证号");
			}else if(idnum.length() < 18) {
				showTost("身份证号有误");
			}else if(TextUtils.isEmpty(bankId)){
				showTost("请选择银行");
			}else if(TextUtils.isEmpty(cardnumber)){
				showTost("请输入银行卡号");
			}else if(cardnumber.length() < 15) {
				showTost("银行卡号有误");
			}else if(!StringUtils.isMobileNO(phone)){
				showTost("手机格式有误");
			}else{
				commitBindCard(name,idnum,bankId,cardnumber,phone);
			}
			break;
		default:
			break;
		}

	}
	/**
	 * 
	 * @Title: commitBindCard 
	 * @Description: 提交绑定的银行卡号
	 * @return: void
	 */
	private void commitBindCard(String name,String idNO,String bankType,String cardNo,String phone) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("cardNo", cardNo);
		map.put("idNO", idNO);
		map.put("name", name);
		map.put("bankType",bankType);
		map.put("phone", phone);
		loadingDialog.showDialog();
		SimpleRequest<MessageBean> request = new SimpleRequest<MessageBean>(
				API.SAVEUSERBANKCARD, MessageBean.class,
				new Listener<MessageBean>() {
					@Override
					public void onResponse(MessageBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								finish();
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
			showbackDialog();
            return true;
        }
		return super.onKeyDown(keyCode, event);
	}
	
	

	private void showbackDialog() {
		CustomDialog.Builder bulider = new Builder(this);
		bulider.setTitle("放弃绑定");
		bulider.setContent("银行卡未绑定完成，请确认是否放弃绑定？放弃后将失去已填写数据");
		bulider.setNegativeButton("放弃", new CustomDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
				
			}
		});
		bulider.setPositiveButton("继续绑定", new CustomDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		bulider.create().show();
		
	}

	/**
	 * 
	 * @Title: showselectBankPOP 
	 * @Description: 选择银行的pop
	 * @return: void
	 */
	private void showselectBankPOP() {
		View popView = LayoutInflater.from(this).inflate(R.layout.popwindow_select_bank,
				null);
		ListView lv_banklist = (ListView)popView.findViewById(R.id.lv_banklist);
		TextView tv_cancel = (TextView)popView.findViewById(R.id.tv_cancel);
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		BankListPOPAdapter bankListPOPAdapter = new BankListPOPAdapter(this,bank);
		lv_banklist.setAdapter(bankListPOPAdapter);
		lv_banklist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				bankName.setText(bank.get(position).getBankName()+"");
				bankName.setTextColor(Color.parseColor("#666666"));
				bankId = bank.get(position).getId()+"";
				popupWindow.dismiss();
			}
		});
		if (null == popupWindow || !popupWindow.isShowing()) {
			popupWindow = new PopupWindow(getApplicationContext());
			//设置破普window的属性
			popupWindow.setWidth(LayoutParams.MATCH_PARENT);
			popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setContentView(popView);
			popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
			popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			popupWindow.showAtLocation(findViewById(R.id.ll_mypurse_addcard), Gravity.BOTTOM, 0, 0);
	        WindowManager.LayoutParams lp = getWindow().getAttributes();
	        lp.alpha = 0.5f;
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	        getWindow().setAttributes(lp);
	        popupWindow.setOnDismissListener(new OnDismissListener() {
	 
	            @Override
	            public void onDismiss() {
	                WindowManager.LayoutParams lp = getWindow().getAttributes();
	                lp.alpha = 1f;
	                getWindow().setAttributes(lp);
	            }
	        });
		}
		
	}

}
