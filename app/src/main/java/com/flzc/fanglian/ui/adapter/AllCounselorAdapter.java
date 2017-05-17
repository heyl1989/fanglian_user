package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.CounselorListBean.Result.CounselorList;

public class AllCounselorAdapter extends MyBaseAdapter<CounselorList> {
	
	public AllCounselorAdapter(Context context, List<CounselorList> list) {
		super(context, list);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_counselor, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(dList.get(position).getHeadUrl(), viewHolder.headImg,options);
		viewHolder.callPone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+dList.get(position).getPhone()));
				context.startActivity(intent);
				
			}
		});
		viewHolder.counselorName.setText(dList.get(position).getLinkMan());
		viewHolder.takeLook.setText("累计带看："+dList.get(position).getStatus()+"人");
		return convertView;
	}

	static class ViewHolder {

		ImageView headImg;
		TextView counselorName;
		ImageView callPone;
		TextView takeLook;

		public ViewHolder(View convertView) {
			headImg = (ImageView)convertView.findViewById(R.id.cimg_counselor_head);
			counselorName = (TextView)convertView.findViewById(R.id.tv_counselorname);
			takeLook = (TextView)convertView.findViewById(R.id.tv_take_look);
			callPone = (ImageView)convertView.findViewById(R.id.img_call_phone);
		}

	}
}
