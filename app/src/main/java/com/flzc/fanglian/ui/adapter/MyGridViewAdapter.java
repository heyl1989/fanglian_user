package com.flzc.fanglian.ui.adapter;

import java.util.List;

import com.flzc.fanglian.R;
import com.flzc.fanglian.model.OptionQuestionModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class MyGridViewAdapter extends BaseAdapter{
	private Context context;
	private List<OptionQuestionModel> tagsList;
	
	public MyGridViewAdapter(Context context,List<OptionQuestionModel> tagsList){
		this.context = context;
		this.tagsList = tagsList;
	}
	
	@Override
	public int getCount() {
		return tagsList.size();
	}

	@Override
	public Object getItem(int position) {
		return tagsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_mygridview, null);
			viewHolder = new ViewHolder();
			viewHolder.iv_question_select = (ImageView) convertView.findViewById(R.id.iv_question_select);
			viewHolder.tv_quesion_title = (TextView) convertView.findViewById(R.id.tv_quesion_title);
			convertView.setTag(viewHolder);
		}else{
			viewHolder =(ViewHolder) convertView.getTag();
		}
		OptionQuestionModel optionQuestionModel = tagsList.get(position);
		if(null != optionQuestionModel){
			//是否选中
			boolean check = optionQuestionModel.isCheck();
			if(check){
				viewHolder.iv_question_select.setImageResource(R.drawable.delete_red);
			}else{
				viewHolder.iv_question_select.setImageResource(R.drawable.delete_grey);
			}
			viewHolder.tv_quesion_title.setText(optionQuestionModel.getName());
		}
		return convertView;
	}

	static class ViewHolder{
		ImageView iv_question_select;
		TextView tv_quesion_title;
	}
	
}
