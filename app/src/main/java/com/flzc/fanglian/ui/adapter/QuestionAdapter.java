package com.flzc.fanglian.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.model.ItemQuestionModel;
import com.flzc.fanglian.model.OptionQuestionModel;
import com.flzc.fanglian.view.MyGridView;

public class QuestionAdapter extends BaseAdapter{
	private Context context;
	private List<ItemQuestionModel> listData;
	
	public QuestionAdapter(Context context,List<ItemQuestionModel> listData){
		this.context = context;
		this.listData = listData;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_questionnaire, null);
			viewHolder = new ViewHolder();
			viewHolder.item_question_title = (TextView) convertView.findViewById(R.id.item_question_title);
			viewHolder.myGridView = (MyGridView) convertView.findViewById(R.id.question_myGridView);
			convertView.setTag(viewHolder);
		}else{
			viewHolder =(ViewHolder) convertView.getTag();
		}
		ItemQuestionModel itemQuestionModel = listData.get(position);
		if(null != itemQuestionModel){
			viewHolder.item_question_title.setText(position + 1 + "." + itemQuestionModel.getTitle());
			List<OptionQuestionModel> tagsList = new ArrayList<OptionQuestionModel>();
			tagsList = itemQuestionModel.getTagsList();
			MyGridViewAdapter mAdapter = new MyGridViewAdapter(context, tagsList);
			viewHolder.myGridView.setAdapter(mAdapter);
			
			viewHolder.myGridView.setTag(R.id.tag_first, tagsList);
			viewHolder.myGridView.setTag(R.id.tag_second, mAdapter);
			viewHolder.myGridView.setOnItemClickListener(myOnItemClickListener);
		}
		return convertView;
	}

	private OnItemClickListener myOnItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			List<OptionQuestionModel> tagsList =(List<OptionQuestionModel>) parent.getTag(R.id.tag_first);
			MyGridViewAdapter mAdapter = (MyGridViewAdapter) parent.getTag(R.id.tag_second);
			for(int i = 0; i < tagsList.size(); i++){
				if(i == position){
					tagsList.get(i).setCheck(true);
				}else{
					tagsList.get(i).setCheck(false);
				}
			}
			mAdapter.notifyDataSetChanged();
		}
	};
	
	static class ViewHolder{
		TextView item_question_title;
		MyGridView myGridView;
	}
	
}
