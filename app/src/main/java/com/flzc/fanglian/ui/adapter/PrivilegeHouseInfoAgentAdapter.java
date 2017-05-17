package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
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
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.model.GoodBrokeBean.Result.BrokeList;
import com.flzc.fanglian.ui.agent_activity.QuestionActivity;
import com.flzc.fanglian.ui.login_register.LoginActivity;
import com.flzc.fanglian.view.DateAndTimePickerPopWindow;
import com.flzc.fanglian.view.dialog.CustomDialog;

/**
 * 
 * @ClassName: PrivilegeAgentAdapter
 * @Description: 经纪人推荐活动 房源详情下 的经纪人列表 适配器
 * @author: Tien.
 * @date: 2016年3月5日 下午5:44:18
 */
public class PrivilegeHouseInfoAgentAdapter extends MyBaseAdapter<BrokeList> {
	
	private View popView;
	private String activityPoolId;
	private String buildingId;

	public PrivilegeHouseInfoAgentAdapter(Context context, List<BrokeList> list,View view,String activityPoolId,String buildingId) {
		super(context, list);
		this.popView  = view;
		this.activityPoolId = activityPoolId;
		this.buildingId = buildingId;
	}

	public PrivilegeHouseInfoAgentAdapter(Context context, List<BrokeList> list) {
		super(context, list);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_privilege_houseinfo_agentlist, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(dList.get(position).getUrl(), viewHolder.headImg, options);
		viewHolder.name.setText(dList.get(position).getName());
		viewHolder.takeLook.setText("累计带看："+dList.get(position).getVisitNum()+"人");
		viewHolder.phone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 售楼热线
				if(null != dList.get(position).getPhone()){
					Intent tellIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
							+ dList.get(position).getPhone()));
					context.startActivity(tellIntent);
				}
			}
		});
		if(dList.get(position).getIsOrder() == 1){
			viewHolder.tv_yuyue.setText("已预约");
		}else{
			viewHolder.tv_yuyue.setText("预约");
		}
		//预约时间
		viewHolder.orderedTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!UserInfoData.isSingIn()){
					goToOthers(LoginActivity.class);
				}else{
					String brokerId = dList.get(position).getId();
					showDialog(context,brokerId,viewHolder.tv_yuyue);
				}
			}
		});
		return convertView;
	}

	static class ViewHolder {

		TextView name;
		ImageView headImg;
		TextView takeLook;
		ImageView phone;
		ImageView orderedTime;
		TextView tv_yuyue;

		public ViewHolder(View convertView) {
			name = (TextView) convertView
					.findViewById(R.id.tv_name_item_privilege_houseInfo_agentList);
			headImg = (ImageView) convertView
					.findViewById(R.id.img_item_privilege_houseInfo_agentList);
			takeLook = (TextView) convertView
					.findViewById(R.id.tv_peopleCount_item_privilege_houseInfo_agentList);
			phone = (ImageView) convertView
					.findViewById(R.id.img_phone_item_privilege_houseInfo_agentList);
			orderedTime = (ImageView) convertView
					.findViewById(R.id.img_time_ordered);
			tv_yuyue = (TextView) convertView
					.findViewById(R.id.tv_yuyue);

		}

	}
	
	public void showDialog(final Context context,final String brokerId,final TextView tv){
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setTitle("预约经纪人");
		builder.setContent("提交预约后经纪人将获得您的信息，联系您并向您确认预约看房时间。");
		builder.setNegativeButton("先填写购房意向", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(context,QuestionActivity.class);
				intent.putExtra("buildingId", buildingId);
				context.startActivity(intent);
				new DateAndTimePickerPopWindow(context,popView,activityPoolId,brokerId,tv);
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("直接预约", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				new DateAndTimePickerPopWindow(context,popView,activityPoolId,brokerId,tv);
				dialog.dismiss();
			}

		});
		builder.create().show();
	}

}
