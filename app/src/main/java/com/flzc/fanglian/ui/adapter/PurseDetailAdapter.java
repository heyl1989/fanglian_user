package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.model.AccountDetail.Result;
import com.flzc.fanglian.util.StringUtils;
/**
 * 
 * @ClassName: PurseDetailAdapter 
 * @Description: 我的钱包明细记录
 * @author: LU
 * @date: 2016-3-8 下午7:24:40
 */
public class PurseDetailAdapter extends BaseAdapter {

	private Context context;
	private List<Result> list;
	
	public PurseDetailAdapter(Context context, List<Result> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
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
					R.layout.item_mypurse_detail, null);
			viewHolder = new ViewHolder();
			viewHolder.tv_accountDetail_name = (TextView) convertView.findViewById(R.id.tv_accountDetail_name);
			viewHolder.tv_accountDetail_info = (TextView) convertView.findViewById(R.id.tv_accountDetail_info);
			viewHolder.tv_accountDetail_money = (TextView) convertView.findViewById(R.id.tv_accountDetail_money);
			viewHolder.tv_accountDetail_pay_way = (TextView) convertView.findViewById(R.id.tv_accountDetail_pay_way);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Result result = list.get(position);
		if(result.getActType() == 3901){
			viewHolder.tv_accountDetail_name.setText("活动保证金 [竞拍]");
		}else if(result.getActType() == 3902){
			viewHolder.tv_accountDetail_name.setText("活动保证金 [天天特价]");
		}else{
			viewHolder.tv_accountDetail_name.setText(result.getActName());
		}
		
		viewHolder.tv_accountDetail_info.setText(result.getDate());
		String amount = StringUtils.intMoney(result.getAmount());
		if(1 == result.getInOutType()){
			viewHolder.tv_accountDetail_money.setText("+" + amount+"元");
		}else if(2 == result.getInOutType()){
			viewHolder.tv_accountDetail_money.setText("-" + amount+"元");
		}
		
		switch (result.getPayType()) {
		case 1:		
			viewHolder.tv_accountDetail_pay_way.setText("余额支付");
			break;
		case 2:
			viewHolder.tv_accountDetail_pay_way.setText("支付宝支付");
			break;
		case 3:		
			viewHolder.tv_accountDetail_pay_way.setText("微信支付");
			break;
		case 4:		
			viewHolder.tv_accountDetail_pay_way.setText("连连支付");
			break;
		case 5:
			viewHolder.tv_accountDetail_pay_way.setText("财务代付");
			break;
		case 6:
			viewHolder.tv_accountDetail_pay_way.setText("易宝网银");
			break;
		case 7:
			viewHolder.tv_accountDetail_pay_way.setText("易宝扫码");
			break;
		case 8:
			viewHolder.tv_accountDetail_pay_way.setText("易宝快捷");
			break;
		default:
			viewHolder.tv_accountDetail_pay_way.setText("");
			break;
		}
		if(TextUtils.equals(result.getActName(), "退款")){
			viewHolder.tv_accountDetail_pay_way.setText("原路退回");
		}
		
		return convertView;
	}

	static class ViewHolder {
		TextView tv_accountDetail_name;
		TextView tv_accountDetail_info;
		TextView tv_accountDetail_money;
		TextView tv_accountDetail_pay_way;
	}
}
