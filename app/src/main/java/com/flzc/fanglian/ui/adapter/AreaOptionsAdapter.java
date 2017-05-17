package com.flzc.fanglian.ui.adapter;

import java.util.List;
import java.util.Map;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.OpenCityBean.Result.Citys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @ClassName: AreaOptionsAdapter
 * @Description: 筛选选项适配器
 * @author: Tien.
 * @date: 2015年10月29日 上午11:48:03
 */
public class AreaOptionsAdapter extends MyBaseAdapter<Citys> {
	
	public AreaOptionsAdapter(Context context, List<Citys> list) {
		super(context, list);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {

			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_city_option, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.name.setText(dList.get(position).getCityName());
		return convertView;
	}

	static class ViewHolder {
		TextView name;

		public ViewHolder(View convertView) {
			name = (TextView) convertView
					.findViewById(R.id.tv_name_item_areaOption);
		}
	}

}
