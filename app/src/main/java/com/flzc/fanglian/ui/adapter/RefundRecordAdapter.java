package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.model.AccountBackDetailBean.Result;
import com.flzc.fanglian.util.StringUtils;

public class RefundRecordAdapter extends BaseAdapter {

	private Context context;
	private List<Result> list;
	
	public RefundRecordAdapter(Context context, List<Result> list) {
		this.context = context;
		this.list = list;
	}

	

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_mypurse_refund, null);
			viewHolder = new ViewHolder();
			viewHolder.tv_accountBack_time = (TextView) convertView.findViewById(R.id.tv_accountBack_time);
			viewHolder.tv_accountBack_money = (TextView) convertView.findViewById(R.id.tv_accountBack_money);
			viewHolder.tv_accountBack_way = (TextView) convertView.findViewById(R.id.tv_accountBack_way);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Result result = list.get(position);
		viewHolder.tv_accountBack_time.setText(result.getDate());
		String amount = StringUtils.intMoney(result.getAmount());
		viewHolder.tv_accountBack_money.setText("+" + amount);
		switch (result.getState()) {
		case 0:
			viewHolder.tv_accountBack_way.setText("原路退回");
			break;
		case 1:
			viewHolder.tv_accountBack_way.setText("原路退回");
			break;
		case 2:
			viewHolder.tv_accountBack_way.setText("原路退回");
			break;
		}
		
		return convertView;
	}

	static class ViewHolder {
		TextView tv_accountBack_time;
		TextView tv_accountBack_money;
		TextView tv_accountBack_way;
	}
}
