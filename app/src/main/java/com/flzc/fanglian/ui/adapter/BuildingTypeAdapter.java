package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.AgentHouseTypeBean.Result;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.XCFlowLayout;

/**
 * 
 * @ClassName: BuildingTypeAdapter
 * @Description: 楼盘户型适配器
 * @author: 薛东超
 * @date: 2016年3月5日 下午7:10:10
 */
public class BuildingTypeAdapter extends MyBaseAdapter<Result> {

	private int actStatus;// 1为未开始、2为进行中、3为已结束

	public BuildingTypeAdapter(Context context, List<Result> list, int actStatus) {
		super(context, list);
		this.actStatus = actStatus;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHower viewHower = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_building_type, null);
			viewHower = new ViewHower(convertView);
			convertView.setTag(viewHower);
		} else {
			viewHower = (ViewHower) convertView.getTag();
		}
		Result result = dList.get(position);
		if (null != result.getImgs() && result.getImgs().size() > 0) {
			imageLoader.displayImage(result.getImgs().get(0).getImgUrl(),
					viewHower.mPicture, options);
		}
		viewHower.houseType.setText(result.getHouseTypeName());
		viewHower.houseSize.setText(result.getSize() + "㎡");
		viewHower.houseFloor.setText(result.getFloor());
		viewHower.mQuan.setText(StringUtils.intMoney(result.getTicketAmount()) + "");
		if (actStatus == 2) {
			viewHower.mQuanStates.setText("可领");
		} else {
			viewHower.mQuanStates.setText("不可领");
		}
//		viewHower.houseTotalPrice
//				.setText("总价" + result.getTicketAmount() + "万");

		return convertView;
	}

	static class ViewHower {
		TextView tv_bdtype_house_type;
		ImageView mPicture;
		TextView houseType;
		TextView houseSize;
		TextView houseFloor;
		TextView mQuan;
		//TextView houseTotalPrice;
		TextView mQuanStates;
		XCFlowLayout flowlayout;

		public ViewHower(View convertView) {
			houseType = (TextView) convertView
					.findViewById(R.id.tv_bdtype_house_type);
			houseSize = (TextView) convertView
					.findViewById(R.id.tv_bdtype_house_area);
			houseFloor = (TextView) convertView
					.findViewById(R.id.tv_bdtype_house_position);
			mQuan = (TextView) convertView
					.findViewById(R.id.tv_bdtype_house_price);
			/*houseTotalPrice = (TextView) convertView
					.findViewById(R.id.tv_bdtype_house_totalnum);*/
			mQuanStates = (TextView) convertView
					.findViewById(R.id.tv_bdtype_ticket_status);
			mPicture = (ImageView) convertView
					.findViewById(R.id.iv_bdtype_house_picture);

		}

	}

}
