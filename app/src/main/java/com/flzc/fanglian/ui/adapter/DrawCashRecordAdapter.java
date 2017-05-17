package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.AccountDrawList.Result;
import com.flzc.fanglian.util.StringUtils;

public class DrawCashRecordAdapter extends MyBaseAdapter<Result> {

	public DrawCashRecordAdapter(Context context, List<Result> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_mypurse_drawcash, null);
			viewHolder = new ViewHolder();
			viewHolder.tv_cashList_money = (TextView) convertView.findViewById(R.id.tv_cashList_money);
			viewHolder.tv_cashList_cardId = (TextView) convertView.findViewById(R.id.tv_cashList_cardId);
			viewHolder.tv_cashList_status = (TextView) convertView.findViewById(R.id.tv_cashList_status);
			viewHolder.tv_cashList_time = (TextView) convertView.findViewById(R.id.tv_cashList_time);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Result result = dList.get(position);
		String amount = StringUtils.intMoney(result.getAmount());
		viewHolder.tv_cashList_money.setText( amount+ "元");
		viewHolder.tv_cashList_cardId.setText("卡号：" + result.getCardNo() + "(" + result.getName() + ")");
		viewHolder.tv_cashList_time.setText(result.getDate());
		int status = result.getState();
		switch (status) {
		case 0:
			viewHolder.tv_cashList_status.setText("处理中");
			break;
		case 1:
			viewHolder.tv_cashList_status.setText("成功");
			break;	
		case 2:
			viewHolder.tv_cashList_status.setText("失败");
			break;
		}
		return convertView;
	}

	static class ViewHolder {
		TextView tv_cashList_money;
		TextView tv_cashList_cardId;
		TextView tv_cashList_status;
		TextView tv_cashList_time;
	}

}
