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
import com.flzc.fanglian.model.AgentRecommendlActivityBean.Result.SaleswayList;
import com.flzc.fanglian.util.StringUtils;

public class RecommendHouseListAdapter extends MyBaseAdapter<SaleswayList> {

	public RecommendHouseListAdapter(Context context, List<SaleswayList> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHower = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.layout_activity_houseinfo_recommend, null);
			viewHower = new ViewHolder(convertView);
			convertView.setTag(viewHower);
		} else {
			viewHower = (ViewHolder) convertView.getTag();
		}
		SaleswayList saleswayHouse = dList.get(position);
		imageLoader.displayImage(saleswayHouse.getHouseTypeImg(), viewHower.houseMaster,options);
		viewHower.houseKind.setText(saleswayHouse.getHouseKind());
		viewHower.houseType.setText(saleswayHouse.getHouseType());
		viewHower.houseSize.setText(saleswayHouse.getSize()+"㎡");
		viewHower.houseFloor.setText(saleswayHouse.getFloor());
		viewHower.housePosition.setText(saleswayHouse.getArea()+saleswayHouse.getBuildingNum());
		viewHower.housePrice.setText("优惠单价："+StringUtils.intMoney(saleswayHouse.getPrice())+"元/㎡");
		viewHower.discount.setText(StringUtils.intMoney(saleswayHouse.getTicketAmount())+"");
		try {
			double total = Double.parseDouble(saleswayHouse.getAmount());
			viewHower.discountTotal.setText("优惠总价："+StringUtils.intMoney(total/10000+"")+"万");
		} catch (Exception e) {
			showToast("优惠总价数据错误");
		}
		return convertView;
	}

	static class ViewHolder {
		
		ImageView houseMaster;
		TextView houseKind;
		TextView houseType;
		TextView houseSize;
		TextView houseFloor;
		TextView housePosition;
		TextView housePrice;
		TextView houseTotal;
		TextView discount;
		TextView discountTotal;

		public ViewHolder(View convertView) {
			houseMaster = (ImageView)convertView.findViewById(R.id.img_house_master);
			houseKind = (TextView)convertView.findViewById(R.id.tv_housekind);
			houseType = (TextView)convertView.findViewById(R.id.tv_house_type);
			houseSize = (TextView)convertView.findViewById(R.id.tv_house_size);
			houseFloor = (TextView)convertView.findViewById(R.id.tv_house_floor);
			housePosition = (TextView)convertView.findViewById(R.id.tv_house_position);
			housePrice = (TextView)convertView.findViewById(R.id.tv_house_price);
			houseTotal = (TextView)convertView.findViewById(R.id.tv_house_total);
			discount = (TextView)convertView.findViewById(R.id.tv_discount);
			discountTotal = (TextView)convertView.findViewById(R.id.tv_discounttotal);

		}
	}

}
