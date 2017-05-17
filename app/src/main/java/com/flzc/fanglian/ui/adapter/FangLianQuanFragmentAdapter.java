package com.flzc.fanglian.ui.adapter;

import java.text.DecimalFormat;
import java.util.List;

import com.flzc.fanglian.R;
import com.flzc.fanglian.model.FangLianQuanBean.Result.FangLianQuanList;
import com.flzc.fanglian.util.DateUtils;
import com.flzc.fanglian.util.StringUtils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FangLianQuanFragmentAdapter extends BaseAdapter {

	private Context context;
	private List<FangLianQuanList> data;
	private DecimalFormat df;
	private String sAmount;

	public FangLianQuanFragmentAdapter(Context context, List<FangLianQuanList> data) {
		this.context = context;
		df = new DecimalFormat("###.00");  
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHower viewHower = null;
		if(arg1 == null){
			arg1 = LayoutInflater.from(context).inflate(R.layout.adapter_flquan, null);
			viewHower = new ViewHower();
			viewHower.tv_flquan_gainxbao = (TextView) arg1.findViewById(R.id.tv_flquan_gainxbao);
			viewHower.tv_flquan_numdata = (TextView) arg1.findViewById(R.id.tv_flquan_numdata);
			viewHower.tv_flquan_numunit = (TextView) arg1.findViewById(R.id.tv_flquan_numunit);
			viewHower.tv_flquan_type = (TextView) arg1.findViewById(R.id.tv_flquan_type);
			viewHower.tv_flquan_states = (TextView) arg1.findViewById(R.id.tv_flquan_states);
			viewHower.tv_bulidname = (TextView) arg1.findViewById(R.id.tv_bulidname);
			viewHower.tv_flquan_com_deadtime = (TextView) arg1.findViewById(R.id.tv_flquan_com_deadtime);
			arg1.setTag(viewHower);
		}else{
			viewHower = (ViewHower) arg1.getTag();
		}
		
		FangLianQuanList fangLianQuan = data.get(arg0);
		if(fangLianQuan != null){
			viewHower.tv_flquan_gainxbao.setText(fangLianQuan.getSourceName());
			viewHower.tv_flquan_type.setText(fangLianQuan.getTicketTypeName());
			//表示当前卡券的使用状态  0表示未使用  1表示已使用  2表示已过期
			String status = fangLianQuan.getStatus();
			String amount = StringUtils.intMoney(fangLianQuan.getAmount());
			try {
				double dbAmount = Double.parseDouble(amount);
				sAmount = StringUtils.intMoney(df.format(dbAmount/10000));
			} catch (Exception e) {
			}
			if(StringUtils.isNotBlank(status)){
				if("0".equals(status)){
					viewHower.tv_flquan_numdata.setTextColor(Color.parseColor("#ed4c4c"));
					viewHower.tv_flquan_numunit.setTextColor(Color.parseColor("#ed4c4c"));
					viewHower.tv_flquan_states.setTextColor(Color.parseColor("#ed4c4c"));
					if(Integer.parseInt(fangLianQuan.getSourceId())== 3902){
						viewHower.tv_flquan_numdata.setText(sAmount+"");
					}else{
						viewHower.tv_flquan_numdata.setText(amount+"");
					}
					viewHower.tv_flquan_numunit.setText(fangLianQuan.getUnit());
					viewHower.tv_flquan_states.setText("未使用");
				}else if("1".equals(status)){
					viewHower.tv_flquan_numdata.setTextColor(Color.parseColor("#ed4c4c"));
					viewHower.tv_flquan_numunit.setTextColor(Color.parseColor("#ed4c4c"));
					viewHower.tv_flquan_states.setTextColor(Color.parseColor("#ed4c4c"));
					if(Integer.parseInt(fangLianQuan.getSourceId())== 3902){
						viewHower.tv_flquan_numdata.setText(sAmount+"");
					}else{
						viewHower.tv_flquan_numdata.setText(amount+"");
					}
					viewHower.tv_flquan_numunit.setText(fangLianQuan.getUnit());
					viewHower.tv_flquan_states.setText("已使用");
				}else if("2".equals(status)){
					viewHower.tv_flquan_numdata.setTextColor(Color.parseColor("#666666"));
					viewHower.tv_flquan_numunit.setTextColor(Color.parseColor("#666666"));
					viewHower.tv_flquan_states.setTextColor(Color.parseColor("#666666"));
					if(Integer.parseInt(fangLianQuan.getSourceId())== 3902){
						viewHower.tv_flquan_numdata.setText(sAmount+"");
					}else{
						viewHower.tv_flquan_numdata.setText(amount+"");
					}
					viewHower.tv_flquan_numunit.setText(fangLianQuan.getUnit());
					viewHower.tv_flquan_states.setText("已过期");
				}
			}
			
			//已字符串拼接的方式组装成最后一条数据
			viewHower.tv_bulidname.setText(fangLianQuan.getBuildingName());
			String startDate = DateUtils.formatDate(fangLianQuan.getStartDate());//开始时间
			String endDate = DateUtils.formatDate(fangLianQuan.getEndDate());//结束时间
			String times = startDate + "至" + endDate; 
			viewHower.tv_flquan_com_deadtime.setText("有效期" + times);
			viewHower.tv_flquan_com_deadtime.setTag(fangLianQuan);
		}
		return arg1;
	}

	static class ViewHower {
		TextView tv_flquan_gainxbao, tv_flquan_numdata, tv_flquan_numunit,
				tv_flquan_type, tv_flquan_states, tv_flquan_com_deadtime,tv_bulidname;
	}
}
