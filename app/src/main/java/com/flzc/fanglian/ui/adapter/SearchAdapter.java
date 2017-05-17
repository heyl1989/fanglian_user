package com.flzc.fanglian.ui.adapter;

import java.util.List;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.SearchHintBean.Result.SearchList;
import com.flzc.fanglian.util.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchAdapter extends MyBaseAdapter<SearchList>{

	public SearchAdapter(Context context, List<SearchList> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHower viewHower = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_search, null);
			viewHower = new ViewHower();
			viewHower.tv_search_seaarch_result = (TextView) convertView.findViewById(R.id.tv_search_seaarch_result);
			convertView.setTag(viewHower);
		}else{
			viewHower = (ViewHower)convertView.getTag();
		}
		SearchList searchList = dList.get(position);
		if(searchList != null){
			if(StringUtils.isNotBlank(searchList.getName())){
				viewHower.tv_search_seaarch_result.setText(searchList.getName());
			}else if(StringUtils.isNotBlank(searchList.getBuildingName())){
				viewHower.tv_search_seaarch_result.setText(searchList.getBuildingName());
			}
			
			viewHower.tv_search_seaarch_result.setTag(searchList);
		}
		return convertView;
	}

	static class ViewHower{
		TextView tv_search_seaarch_result;
	}
	
}
