package com.flzc.fanglian.ui.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.MessageBean;
import com.flzc.fanglian.model.UserBinbCardBean.BindCardList;
import com.flzc.fanglian.util.LogUtil;
import com.flzc.fanglian.view.dialog.CustomDialog;

public class BankCardAdapter extends MyBaseAdapter<BindCardList> {

	public BankCardAdapter(Context context, List<BindCardList> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHower = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_bankcard, null);
			viewHower = new ViewHolder(convertView);
			convertView.setTag(viewHower);
		} else {
			viewHower = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(dList.get(position).getCardImageUrl(), viewHower.img_bankicon, options);
		viewHower.tv_bankname.setText(dList.get(position).getBankName());
		String cardNum = dList.get(position).getBankCode();
		viewHower.tv_cardnumber.setText("尾号"+cardNum.substring(cardNum.length()-4, cardNum.length()));
		viewHower.tv_unbindcard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(dList.get(position).getId(),position);
			}
		});
		return convertView;
	}
	static class ViewHolder {

		TextView tv_bankname;
		ImageView img_bankicon;
		TextView tv_cardnumber;
		TextView tv_unbindcard;

		public ViewHolder(View convertView) {
			img_bankicon = (ImageView)convertView.findViewById(R.id.img_bankicon);
			tv_bankname = (TextView)convertView.findViewById(R.id.tv_bankname);
			tv_cardnumber = (TextView)convertView.findViewById(R.id.tv_cardnumber);
			tv_unbindcard = (TextView)convertView.findViewById(R.id.tv_unbindcard);
		}

	}
	
	private void showDialog(final int id,final int position){
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setTitle("解除绑定");
		builder.setContent("请确认是否解除绑定该银行卡？取消后您将无法使用该卡进行提现等操作。");
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				unBindCard(id,position);
				dialog.dismiss();
			}

		});
		builder.create().show();
	}
	
	private void unBindCard(int id,final int position){
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("bankCardId", id+"");
		SimpleRequest<MessageBean> request = new SimpleRequest<MessageBean>(
				API.UNBUNDLING_BANKCARD, MessageBean.class,
				new Listener<MessageBean>() {
					@Override
					public void onResponse(MessageBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								if(TextUtils.equals(dList.get(position).getBankCode(), 
										UserInfoData.getData("cardNum",""))){
									LogUtil.e("删除", dList.get(position).getBankCode()+"?"+UserInfoData.getData("cardNum",""));
									UserInfoData.clearKey("bank");
									UserInfoData.clearKey("cardNum");
								}
								dList.remove(position);
								notifyDataSetChanged();
							}else{
								showToast(response.getMsg());
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

}
