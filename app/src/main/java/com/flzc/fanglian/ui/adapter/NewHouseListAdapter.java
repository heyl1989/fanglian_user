package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.R.animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.NewHouseListBean.Result;
import com.flzc.fanglian.model.NewHouseListBean.Result.Activity;
import com.flzc.fanglian.model.NewHouseListBean.Result.Tags;
import com.flzc.fanglian.util.StringUtils;

/**
 * @ClassName: NewHouseListAdapter
 * @Description: 新房列表 适配器
 * @author: Tien.
 * @date: 2016年3月7日 下午5:28:12
 */
public class NewHouseListAdapter extends MyBaseAdapter<Result> {

	public NewHouseListAdapter(Context context, List<Result> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		ViewHolder viewHolder;
		if (convertView == null) {

			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_newhouse_list, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Result result = dList.get(position);
		if (result != null) {
			// 楼盘主图
			imageLoader.displayImage(result.getMaster(),
					viewHolder.img_mainImg_item_newHouseList, options);
			// 楼盘名称
			viewHolder.houseName.setText(result.getName());
			// 楼盘价格
			viewHolder.tv_price_item_newHouseList.setText(StringUtils.intMoney(result.getSoldPrice())+"");
			// 楼盘地址
			String address = result.getAddress();
			// 标签
			List<Tags> tagList = result.getTags();
			String tags = "";
			if (tagList != null) {
				for (int i = 0; i < tagList.size(); i++) {
					tags += tagList.get(i).getName() + " ";
				}
				viewHolder.tv_tags_item_newHouseList.setText(tags);
			}

			// 活动数据
			List<Activity> acivityList = result.getActivity();
			if (null != acivityList) {
				viewHolder.iv_pai_newHouseList.setVisibility(View.GONE);
				viewHolder.iv_te_newHouseList.setVisibility(View.GONE);
				viewHolder.iv_da_newHouseList.setVisibility(View.GONE);
				for (int i = 0; i < acivityList.size(); i++) {
					switch (acivityList.get(i).getActivityType()) {
					case 3901:
						viewHolder.iv_pai_newHouseList
								.setVisibility(View.VISIBLE);
						break;
					case 3902:
						viewHolder.iv_te_newHouseList
								.setVisibility(View.VISIBLE);
						break;
					case 3903:
						viewHolder.iv_da_newHouseList
								.setVisibility(View.VISIBLE);
						break;
					}
				}
			}

		}

		return convertView;
	}

	public class ViewHolder {
		ImageView img_mainImg_item_newHouseList, iv_da_newHouseList,
				iv_te_newHouseList, iv_pai_newHouseList;
		TextView houseName, tv_price_item_newHouseList,
				tv_tags_item_newHouseList;

		public ViewHolder(View convertView) {
			// 实例化控件
			img_mainImg_item_newHouseList = (ImageView) convertView
					.findViewById(R.id.img_mainImg_item_newHouseList);
			int width = ((android.app.Activity) context).getWindowManager().getDefaultDisplay().getWidth();
	        float height = width/2.27f;//得到对应的高
	        img_mainImg_item_newHouseList.getLayoutParams().height = (int)height;
	        img_mainImg_item_newHouseList.requestLayout();
			iv_da_newHouseList = (ImageView) convertView
					.findViewById(R.id.iv_da_newHouseList);
			iv_te_newHouseList = (ImageView) convertView
					.findViewById(R.id.iv_te_newHouseList);
			iv_pai_newHouseList = (ImageView) convertView
					.findViewById(R.id.iv_pai_newHouseList);
			houseName = (TextView) convertView
					.findViewById(R.id.tv_houseName_item_newHouseList);
			tv_price_item_newHouseList = (TextView) convertView
					.findViewById(R.id.tv_price_item_newHouseList);
			tv_tags_item_newHouseList = (TextView) convertView
					.findViewById(R.id.tv_tags_item_newHouseList);

		}

	}

}
