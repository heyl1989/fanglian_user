package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.model.UserOrderListBean.Result;
import com.flzc.fanglian.util.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 
 * @ClassName: MyOrderListAdapter 
 * @Description:我的订单列表
 * @author: jack
 * @date: 2016-3-8 下午7:25:14
 */
public class MyOrderListAdapter extends BaseAdapter{
	private Context context;
	private List<Result> listData;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	public MyOrderListAdapter(Context context, List<Result> list) {
		this.context = context;
		this.listData = list;
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.loading_750_438)
				.showImageForEmptyUri(R.drawable.loading_750_438).cacheInMemory().cacheInMemory().build();
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_me_order, null);
			viewHolder = new ViewHolder();
			viewHolder.iv_icon_activity = (ImageView) convertView.findViewById(R.id.iv_icon_activity);
			viewHolder.tv_order_activity_name = (TextView) convertView.findViewById(R.id.tv_order_activity_name);
			viewHolder.tv_activity_state = (TextView) convertView.findViewById(R.id.tv_activity_state);
			viewHolder.img_order_house = (ImageView) convertView.findViewById(R.id.img_order_house);
			viewHolder.tv_order_activity_type = (TextView) convertView.findViewById(R.id.tv_order_activity_type);
			viewHolder.tv_orderlist_money = (TextView) convertView.findViewById(R.id.tv_orderlist_money);
			viewHolder.tv_order_time = (TextView) convertView.findViewById(R.id.tv_order_time);
			viewHolder.tv_pay_status = (TextView) convertView.findViewById(R.id.tv_pay_status);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Result result = listData.get(position);
		viewHolder.tv_order_activity_type.setText(result.getActName());
		int actType = result.getActType();
		switch (actType) {
		case 3901:
			//竞拍
			viewHolder.iv_icon_activity.setImageResource(R.drawable.act_type_pai);
			break;
		case 3902:
			//天天特价
			viewHolder.iv_icon_activity.setImageResource(R.drawable.act_type_te);
			break;
		case 3903:
			//优惠房
			viewHolder.iv_icon_activity.setImageResource(R.drawable.act_type_bighui);
			break;
		}
		//现在后台给的数据是文字(不知道以后会不会换成0,2,3这种东西)
		viewHolder.tv_activity_state.setText(result.getActStatus());
		imageLoader.displayImage(result.getMaster(), viewHolder.img_order_house, options);
		viewHolder.tv_order_activity_name.setText(result.getBuildingName());
		viewHolder.tv_orderlist_money.setText("¥"+StringUtils.intMoney(result.getOrderAmount())+"元");
		viewHolder.tv_order_time.setText("订单时间：" + result.getOrderTime());
		int orderStatus = result.getOrderStatus();
		switch (orderStatus) {
		case 6201:
			if(TextUtils.equals("已结束", result.getActStatus())){
				viewHolder.tv_pay_status.setText("已关闭");
			}else{
				viewHolder.tv_pay_status.setText("待付款");
			}
			break;
		case 6202:
			viewHolder.tv_pay_status.setText("已付款");
			break;
		case 6203:
			viewHolder.tv_pay_status.setText("已取消");
			break;
		case 6204:
			viewHolder.tv_pay_status.setText("已关闭");
			break;
		case 6205:
			viewHolder.tv_pay_status.setText("已退款");
			break;
		case 6206:
			viewHolder.tv_pay_status.setText("失败");
			break;
		}
		viewHolder.tv_order_time.setTag(result);
		return convertView;
	}
	
	static class ViewHolder {
		ImageView iv_icon_activity;
		TextView tv_order_activity_name;
		TextView tv_activity_state;
		ImageView img_order_house;
		TextView tv_order_activity_type;
		TextView tv_orderlist_money;
		TextView tv_order_time;
		TextView tv_pay_status;
	}
}
