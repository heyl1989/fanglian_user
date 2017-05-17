package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.NewHouseDetailBean.Result.BuildActivity;
import com.flzc.fanglian.util.DateUtils;

public class NewHouseActivityTypeAdapter extends MyBaseAdapter<BuildActivity> {

	public NewHouseActivityTypeAdapter(Context context, List<BuildActivity> list) {
		super(context, list);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_newhouse_activity, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 活动数据
		BuildActivity acivity = dList.get(position);
		if (null != acivity) {
			switch (acivity.getActivityType()) {
			case 3901:
				viewHolder.activityImage
						.setImageResource(R.drawable.act_type_pai);
				break;
			case 3902:
				viewHolder.activityImage
						.setImageResource(R.drawable.act_type_te);
				break;
			case 3903:
				viewHolder.activityImage
						.setImageResource(R.drawable.act_type_bighui);
				break;
			}
		}

		long actEndTime = Long.parseLong(acivity.getActEndTime());
		long actStartTime = Long.parseLong(acivity.getActStartTime());
		long currentTime = DateUtils.currentTime();
		if (currentTime > actStartTime && currentTime < actEndTime) {
			// 进行中
			viewHolder.activityStates.setText("[进行中]");

		} else if (currentTime > actEndTime) {
			// 已结束
			viewHolder.activityStates.setText("[已结束]");
		} else if (currentTime < actStartTime) {
			// 即将开始
			viewHolder.activityStates.setText("[即将开始]");
		}

		viewHolder.activityName.setText(acivity.getActivityName());

		return convertView;
	}

	static class ViewHolder {

		ImageView activityImage;
		TextView activityStates;
		TextView activityName;

		public ViewHolder(View convertView) {
			activityImage = (ImageView) convertView
					.findViewById(R.id.img_biddingActIcon_newHouseDetail);
			activityStates = (TextView) convertView
					.findViewById(R.id.tv_biddingActStatus_newHouseDetail);
			activityName = (TextView) convertView
					.findViewById(R.id.tv_biddingActTitle_newHouseDetail);
		}

	}

}
