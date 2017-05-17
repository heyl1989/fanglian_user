package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.AreaBean.Result.Areas;
import com.flzc.fanglian.ui.adapter.CounselorAdapter.ViewHolder;

public class AreaRightAdapter extends MyBaseAdapter<Areas> {

	public AreaRightAdapter(Context context, List<Areas> list) {
		super(context, list);
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

		return convertView;
	}

	static class ViewHolder {

		public ViewHolder(View convertView) {

		}

	}

}
