package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.BankBean.Result;
import com.flzc.fanglian.ui.adapter.ActivityListAdapter.ViewHolder;

public class BankListPOPAdapter extends MyBaseAdapter<Result> {

	public BankListPOPAdapter(Context context, List<Result> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHower = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_bank, null);
			viewHower = new ViewHolder(convertView);
			convertView.setTag(viewHower);
		} else {
			viewHower = (ViewHolder) convertView.getTag();
		}
		viewHower.tv_bankname.setText(dList.get(position).getBankName());
		return convertView;
	}
	static class ViewHolder {

		TextView tv_bankname;

		public ViewHolder(View convertView) {
			tv_bankname = (TextView)convertView.findViewById(R.id.tv_bankname);
		}

	}

}
