package com.flzc.fanglian.ui.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.NewHouseTypeBean.Result.HouseTypes;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.FooterView;

public class NewHouseResourceAdapter extends MyBaseAdapter<HouseTypes> {

	private String unitPrice;
	private DecimalFormat df;

	public NewHouseResourceAdapter(Context context, List<HouseTypes> list,
			String unitPrice) {
		super(context, list);
		df = new DecimalFormat("###.00");  
		this.unitPrice = unitPrice;
	}
	
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_newhouse_resourcelist, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		HouseTypes houseTypes = dList.get(position);
		imageLoader.displayImage(houseTypes.getImg(), viewHolder.mainImg,
				options);
		viewHolder.houseType.setText(houseTypes.getType());
		viewHolder.size.setText(houseTypes.getSize() + "㎡");
		String size = houseTypes.getSize();
		try {
			double dbSize = Double.parseDouble(size);
			double  dbunitPrice = Double.parseDouble(unitPrice);
			if(dbSize == 0||dbunitPrice==0){
				viewHolder.price.setText("¥" + 0);
			}else{
				viewHolder.price.setText("¥" + StringUtils.intMoney(df.format(dbSize*dbunitPrice/10000)));
			}
		} catch (Exception e) {
			showToast("数据错误");
		}
		return convertView;
	}

	static class ViewHolder {
		ImageView mainImg;
		TextView size;
		TextView houseType;
		TextView price;

		public ViewHolder(View convertView) {
			mainImg = (ImageView) convertView
					.findViewById(R.id.mainImg_item_newHouseResource);
			houseType = (TextView) convertView
					.findViewById(R.id.tv_houseType_item_newHouseResource);
			size = (TextView) convertView
					.findViewById(R.id.tv_houseType_item_newHousesize);
			price = (TextView) convertView
					.findViewById(R.id.tv_price_item_newHouseResource);
		}
	}

}
