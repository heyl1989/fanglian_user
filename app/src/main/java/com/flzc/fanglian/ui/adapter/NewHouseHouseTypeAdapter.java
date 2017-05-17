package com.flzc.fanglian.ui.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.NewHouseDetailBean.Result.HouseTypes;
import com.flzc.fanglian.ui.newhouse.HouseTypePhotoActivity;
import com.flzc.fanglian.util.StringUtils;

public class NewHouseHouseTypeAdapter extends MyBaseAdapter<HouseTypes> {

	private String price;
	private DecimalFormat df;

	public NewHouseHouseTypeAdapter(Context context, List<HouseTypes> list,
			String price) {
		super(context, list);
		df = new DecimalFormat("###.00");  
		this.price = price;
	}

	public int getCount() {
		return dList.size() > 3 ? 3 : dList.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_newhouse_housetype, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(dList.get(position).getImg(),
				viewHolder.houseMaster, options);
		viewHolder.houseMaster.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,HouseTypePhotoActivity.class);
				intent.putExtra("houseTypeUrl", dList.get(position).getBigImg());
				context.startActivity(intent);
			}
		});
		HouseTypes houseType = dList.get(position);
		viewHolder.houseType.setText(houseType.getRoom() + "室"
				+ houseType.getHall() + "厅  " + houseType.getSize() + "㎡");
		String size = houseType.getSize();
		try {
			double dbSize = Double.parseDouble(size);
			double  unitPrice = Double.parseDouble(price);
			if(dbSize == 0 || unitPrice == 0){
				viewHolder.consultPrice.setText("¥" + 0 + "万元");
			}else{
				viewHolder.consultPrice.setText("¥" + StringUtils.intMoney(df.format(dbSize*unitPrice/10000)) + "万元");
			}
		} catch (Exception e) {
			showToast("数据错误");
		}
		return convertView;
	}

	static class ViewHolder {

		ImageView houseMaster;
		TextView houseType;
		TextView consultPrice;

		public ViewHolder(View convertView) {
			houseMaster = (ImageView) convertView
					.findViewById(R.id.img_houseType_master);
			houseType = (TextView) convertView.findViewById(R.id.tv_housetype);
			consultPrice = (TextView) convertView
					.findViewById(R.id.tv_consult_price);
		}

	}

}
