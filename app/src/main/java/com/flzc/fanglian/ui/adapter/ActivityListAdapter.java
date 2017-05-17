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
import com.flzc.fanglian.model.RecommendActivityBean.Result;

public class ActivityListAdapter extends MyBaseAdapter<Result> {

	public ActivityListAdapter(Context context, List<Result> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHower = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_atylist, null);
			viewHower = new ViewHolder(convertView);
			convertView.setTag(viewHower);
		} else {
			viewHower = (ViewHolder) convertView.getTag();
		}
		Result activityInfo = dList.get(position); 
		imageLoader.displayImage(activityInfo.getMaster(),
				viewHower.iv_atylist_mainimage_item, options);
		viewHower.buildName.setText(activityInfo.getName());
		return convertView;
	}

	static class ViewHolder {
		TextView tv_atylist_tags_item;
		ImageView iv_atylist_mainimage_item, iv_atylist_activityTag;
		TextView buildName;

		public ViewHolder(View convertView) {
			// 活动状态
			iv_atylist_activityTag = (ImageView) convertView
					.findViewById(R.id.iv_atylist_activityTag);
			// 楼盘主图
			iv_atylist_mainimage_item = (ImageView) convertView
					.findViewById(R.id.iv_atylist_mainimage_item);
			// 楼盘主图
			buildName = (TextView) convertView
					.findViewById(R.id.tv_atylist_name_item);
			//标签
			tv_atylist_tags_item = (TextView) convertView
					.findViewById(R.id.tv_atylist_tags_item);

		}

	}
}
