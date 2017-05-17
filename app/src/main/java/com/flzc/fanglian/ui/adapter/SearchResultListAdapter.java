package com.flzc.fanglian.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.SearchBean.Result;
import com.flzc.fanglian.model.SearchBean.Result.SearchActivity;
import com.flzc.fanglian.model.SearchBean.Result.Tags;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class SearchResultListAdapter extends MyBaseAdapter<Result>{
	private Context context;
	private List<Result> totalList;
	
	public SearchResultListAdapter(Context context,List<Result> totalList) {
		super(context);
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.loading_710_310)
				.showImageForEmptyUri(R.drawable.loading_710_310).cacheInMemory().cacheInMemory().build();
		this.context = context;
		this.totalList = totalList;
	}
	
	@Override
	public int getCount() {
		return totalList.size();
	}

	@Override
	public Object getItem(int position) {
		return totalList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_newhouse_list, null);
			
			//实例化控件
			viewHolder.img_mainImg_item_newHouseList = (ImageView) convertView.findViewById(R.id.img_mainImg_item_newHouseList);
			viewHolder.iv_da_newHouseList = (ImageView) convertView.findViewById(R.id.iv_da_newHouseList);
			viewHolder.iv_te_newHouseList = (ImageView) convertView.findViewById(R.id.iv_te_newHouseList);
			viewHolder.iv_pai_newHouseList = (ImageView) convertView.findViewById(R.id.iv_pai_newHouseList);
			viewHolder.houseName = (TextView) convertView.findViewById(R.id.tv_houseName_item_newHouseList);
			viewHolder.tv_price_item_newHouseList = (TextView) convertView.findViewById(R.id.tv_price_item_newHouseList);
			viewHolder.tv_tags_item_newHouseList = (TextView) convertView.findViewById(R.id.tv_tags_item_newHouseList);
			
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Result result = totalList.get(position);
		if(result != null){
			imageLoader.displayImage(result.getMaster(), viewHolder.img_mainImg_item_newHouseList, options);
			viewHolder.houseName.setText(result.getName());
			//设置标记
			viewHolder.houseName.setTag(result);
			
			viewHolder.tv_price_item_newHouseList.setText(result.getSoldPrice() + "万/㎡");
			
			//活动数据
			List<SearchActivity> listActivities = result.getActivity();
			if(listActivities != null){
				List<String> listType = new ArrayList<String>();
				for(int i = 0; i < listActivities.size(); i++){
					listType.add(listActivities.get(i).getActivityType());
				}
				//3901表示竞拍活动
				if(listType.contains("3901")){
					viewHolder.iv_pai_newHouseList.setVisibility(View.VISIBLE);
				}else{
					viewHolder.iv_pai_newHouseList.setVisibility(View.GONE);
				}
				//3902表示特价
				if(listType.contains("3902")){
					viewHolder.iv_te_newHouseList.setVisibility(View.VISIBLE);
				}else{
					viewHolder.iv_te_newHouseList.setVisibility(View.GONE);
				}
				//3903答题
				if(listType.contains("3903")){
					viewHolder.iv_da_newHouseList.setVisibility(View.VISIBLE);
				}else{
					viewHolder.iv_da_newHouseList.setVisibility(View.GONE);
				}
			}
			
			//标签数据
			List<Tags> listTags = result.getTags();
			String tags = "";
			if(listTags != null){
				for(int i = 0; i < listTags.size(); i++){
					tags += listTags.get(i).getName() + " ";
				}
				viewHolder.tv_tags_item_newHouseList.setText(tags);
			}
		}
		return convertView;
	}
	
	static class ViewHolder{
		ImageView img_mainImg_item_newHouseList,iv_da_newHouseList,iv_te_newHouseList,iv_pai_newHouseList;
		TextView houseName,tv_price_item_newHouseList,tv_tags_item_newHouseList;
	}
}
