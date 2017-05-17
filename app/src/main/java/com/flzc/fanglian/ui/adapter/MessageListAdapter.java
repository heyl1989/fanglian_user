package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.MessageListBean.Result;
import com.flzc.fanglian.util.DateUtils;

public class MessageListAdapter extends MyBaseAdapter<Result> {

	public MessageListAdapter(Context context, List<Result> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_msg, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Result message = dList.get(position);
		if(message != null){
			switch (message.getMessageType()) {
			//类型0为系统类型，1为活动类型、2为运营消息
			case 0:
				viewHolder.msgIcon.setImageResource(R.drawable.message_register);
				break;
			case 1:
				viewHolder.msgIcon.setImageResource(R.drawable.message_bid);
				break;
			case 2:
				viewHolder.msgIcon.setImageResource(R.drawable.message_user);
				break;
			case 3:
				viewHolder.msgIcon.setImageResource(R.drawable.message_order);
				break;
			}
			viewHolder.msgInfo.setText(message.getContent());
			viewHolder.msgType.setText(message.getTitle());
			viewHolder.msgState.setVisibility(View.GONE);
			if(message.getMessageRead() == 0){
				viewHolder.msgState.setVisibility(View.VISIBLE);
			}
			
			viewHolder.msgTime.setText(DateUtils.formatDate(message.getSendCreateTime()));
		}
		
		

		return convertView;
	}
	
	static class ViewHolder {

		ImageView msgIcon;
		TextView msgType;
		TextView msgTime;
		TextView msgInfo;
		ImageView msgState;

		public ViewHolder(View convertView) {
			msgIcon = (ImageView)convertView.findViewById(R.id.iv_msg_msgicon);
			msgState = (ImageView)convertView.findViewById(R.id.iv_msg_red_dot);
			msgType = (TextView)convertView.findViewById(R.id.tv_msg_msgtype);
			msgTime = (TextView)convertView.findViewById(R.id.tv_msg_msgtime);
			msgInfo = (TextView)convertView.findViewById(R.id.tv_msg_msginfo);
			
		}

	}

}
