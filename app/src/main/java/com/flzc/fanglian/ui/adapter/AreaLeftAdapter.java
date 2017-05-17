package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.AreaBean.Result.Areas;
import com.flzc.fanglian.ui.adapter.CounselorAdapter.ViewHolder;

public class AreaLeftAdapter extends MyBaseAdapter<Areas> {

	public AreaLeftAdapter(Context context, List<Areas> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_area, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(dList.get(position).isSelected()){
			viewHolder.tv_area.setTextColor(Color.RED);
		}else{
			viewHolder.tv_area.setTextColor(Color.parseColor("#333333"));
		}
		viewHolder.tv_area.setText(dList.get(position).getAreaName());
		return convertView;
	}

	static class ViewHolder {
		TextView tv_area;
		public ViewHolder(View convertView) {
			tv_area = (TextView)convertView.findViewById(R.id.tv_area);
		}

	}
	

}
