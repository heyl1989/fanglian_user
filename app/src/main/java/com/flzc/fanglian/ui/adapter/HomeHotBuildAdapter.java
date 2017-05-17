package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.NewHouseListBean;
import com.flzc.fanglian.model.NewHouseListBean.Result;
import com.flzc.fanglian.model.NewHouseListBean.Result.Activity;
import com.flzc.fanglian.model.NewHouseListBean.Result.Tags;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.XCFlowLayout;

public class HomeHotBuildAdapter extends MyBaseAdapter<NewHouseListBean.Result> {

	public HomeHotBuildAdapter(Context context,
			List<NewHouseListBean.Result> list) {
		super(context, list);
	}
	@Override
	public int getCount() {
		return dList.size()>3?3:dList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_home_hotbuild, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Result result = dList.get(position);
		if (result != null) {
			// 楼盘主图
			imageLoader.displayImage(result.getMaster(),
					viewHolder.img_hot_master, options);
			// 楼盘名称
			viewHolder.tv_buildname.setText(result.getName());
			// 楼盘价格
			viewHolder.tv_price.setText(StringUtils.intMoney(result.getSoldPrice())+"");
			List<Tags> tagList = result.getTags();
			viewHolder.tv_tag1.setVisibility(View.GONE);
			viewHolder.tv_tag2.setVisibility(View.GONE);
			viewHolder.tv_tag3.setVisibility(View.GONE);
			if (null != tagList && tagList.size() > 0) {
				viewHolder.tv_tag1.setText(tagList.get(0).getName());
				viewHolder.tv_tag1.setVisibility(View.VISIBLE);
			}
			if (null != tagList && tagList.size() > 1) {
				viewHolder.tv_tag2.setText(tagList.get(1).getName());
				viewHolder.tv_tag2.setVisibility(View.VISIBLE);
			}
			if (null != tagList && tagList.size() > 2) {
				viewHolder.tv_tag3.setText(tagList.get(2).getName());
				viewHolder.tv_tag3.setVisibility(View.VISIBLE);
			}
			// 活动数据
			List<Activity> acivityList = result.getActivity();
			if (null != acivityList) {
				viewHolder.img_hui.setVisibility(View.GONE);
				viewHolder.img_te.setVisibility(View.GONE);
				viewHolder.img_pai.setVisibility(View.GONE);
				for (int i = 0; i < acivityList.size(); i++) {
					switch (acivityList.get(i).getActivityType()) {
					case 3901:
						viewHolder.img_pai.setVisibility(View.VISIBLE);
						break;
					case 3902:
						viewHolder.img_te.setVisibility(View.VISIBLE);
						break;
					case 3903:
						viewHolder.img_hui.setVisibility(View.VISIBLE);
						break;
					}
				}
			}

		}
		return convertView;
	}

	static class ViewHolder {

		ImageView img_hot_master;
		TextView tv_buildname;
		TextView tv_price;
		ImageView img_hui;
		ImageView img_te;
		ImageView img_pai;
		TextView tv_tag1;
		TextView tv_tag2;
		TextView tv_tag3;

		public ViewHolder(View convertView) {
			img_hot_master = (ImageView) convertView
					.findViewById(R.id.img_hot_master);
			tv_buildname = (TextView) convertView
					.findViewById(R.id.tv_buildname);
			tv_tag1 = (TextView) convertView.findViewById(R.id.tv_tag1);
			tv_tag2 = (TextView) convertView.findViewById(R.id.tv_tag2);
			tv_tag3 = (TextView) convertView.findViewById(R.id.tv_tag3);
			tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			img_hui = (ImageView) convertView.findViewById(R.id.img_hui);
			img_te = (ImageView) convertView.findViewById(R.id.img_te);
			img_pai = (ImageView) convertView.findViewById(R.id.img_pai);

		}

	}

}
