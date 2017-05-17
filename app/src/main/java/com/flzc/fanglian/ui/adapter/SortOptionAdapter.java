package com.flzc.fanglian.ui.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.SortTypeBean.SortList;

public class SortOptionAdapter extends MyBaseAdapter<SortList> {

	private int isCenter;// 0 代表不居中 1 代表居中

	public SortOptionAdapter(Context context, List<SortList> list,
			int isCenter) {
		super(context, list);
		this.isCenter = isCenter;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (convertView == null) {

			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_area_option, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(dList.get(position).isSelect()){
			viewHolder.name.setTextColor(Color.RED);
		}else{
			viewHolder.name.setTextColor(Color.parseColor("#333333"));
		}
		viewHolder.name.setText(dList.get(position).getSortName());
		return convertView;
	}

	static class ViewHolder {
		TextView name;

		public ViewHolder(View convertView) {
			name = (TextView) convertView
					.findViewById(R.id.tv_name_item_areaOption);
		}
	}

}
