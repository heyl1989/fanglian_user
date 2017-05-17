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
import com.flzc.fanglian.model.DaySpecialPartUserListBean.Result.UserList;
import com.flzc.fanglian.util.DateUtils;

public class DaySpecialUserListAdapter extends MyBaseAdapter<UserList> {

	public DaySpecialUserListAdapter(Context context, List<UserList> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_user_list, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		UserList uesr = dList.get(position);
		imageLoader.displayImage(uesr.getHeadUrl(), viewHolder.userHeadImg, options);
		viewHolder.userName.setText(uesr.getNickName());
		viewHolder.userpartTime.setText(DateUtils.getTime("yyyy-MM-dd HH:mm", uesr.getJoinTime()));
		viewHolder.tv_atv_sucess.setVisibility(View.GONE);
		if(uesr.getDaySpecialStatus() == 1){
			viewHolder.tv_atv_sucess.setText("抢购成功");
			viewHolder.tv_atv_sucess.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
	
	static class ViewHolder {

		ImageView userHeadImg;
		TextView userName;
		TextView userpartTime;
		TextView tv_atv_sucess;

		public ViewHolder(View convertView) {
			userHeadImg = (ImageView)convertView.findViewById(R.id.img_user_avatar);
			userName = (TextView)convertView.findViewById(R.id.tv_user_name);
			userpartTime = (TextView)convertView.findViewById(R.id.tv_time);
			tv_atv_sucess = (TextView)convertView.findViewById(R.id.tv_atv_sucess);
			
		}

	}

}
