package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.model.BiddingUserActivityBean.Result.ListUser;
import com.flzc.fanglian.util.DateUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BiddingUserActivityAdapter extends BaseAdapter {

	private Context context;
	private List<ListUser> listData;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	public BiddingUserActivityAdapter(Context context, List<ListUser> list) {
		this.context = context;
		this.listData = list;
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading_750_438)
				.showImageForEmptyUri(R.drawable.loading_750_438)
				.cacheInMemory(false)
				.cacheOnDisk(true)
				.build();
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_user_list, null);
			viewHolder = new ViewHolder();
			viewHolder.userHeadImg = (ImageView) convertView
					.findViewById(R.id.img_user_avatar);
			viewHolder.userName = (TextView) convertView
					.findViewById(R.id.tv_user_name);
			viewHolder.userpartTime = (TextView) convertView
					.findViewById(R.id.tv_time);
			viewHolder.tv_atv_sucess = (TextView) convertView
					.findViewById(R.id.tv_atv_sucess);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ListUser listUser = listData.get(position);
		if (listUser != null) {
			imageLoader.displayImage(listUser.getHeadUrl(),
					viewHolder.userHeadImg, options);
			viewHolder.userName.setText(listUser.getNickName());
			viewHolder.userpartTime.setText(DateUtils.getTime(
					"yyyy-MM-dd HH:mm", listUser.getJoinTime()));
			viewHolder.tv_atv_sucess.setVisibility(View.GONE);
			if(listUser.getAuctionStatus() == 1){
				viewHolder.tv_atv_sucess.setVisibility(View.VISIBLE);
			}
		}
		return convertView;
	}

	static class ViewHolder {
		TextView tv_atv_sucess;
		ImageView userHeadImg;
		TextView userName;
		TextView userpartTime;
	}

}
