package com.flzc.fanglian.ui.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.AtyListBean.Result;
import com.flzc.fanglian.model.AtyListBean.Result.BuildActivity;
import com.flzc.fanglian.model.MessageBean;
import com.flzc.fanglian.model.RemindMeBean;
import com.flzc.fanglian.ui.agent_activity.AgentRecommendActivity;
import com.flzc.fanglian.ui.bidding_activity.BiddingActivity;
import com.flzc.fanglian.ui.everyday_special_activity.EveryDaySpecialActivity;
import com.flzc.fanglian.ui.login_register.LoginActivity;
import com.flzc.fanglian.util.DateUtils;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.dialog.CustomDialog;

public class DiscountActivityListAdapter extends MyBaseAdapter<Result> {

	String tokenId;
	public DiscountActivityListAdapter(Context context, List<Result> list) {
		super(context, list);
		tokenId = UserInfoData.getData(Constant.TOKEN, "");
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHower = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_atylist, null);
			viewHower = new ViewHolder(convertView);
			convertView.setTag(viewHower);
		} else {
			viewHower = (ViewHolder) convertView.getTag();
		}
		Result result = dList.get(position);
		
		imageLoader.displayImage(result.getMaster(),
				viewHower.iv_atylist_mainimage_item, options);
		viewHower.buildName.setText(result.getName());
		viewHower.tv_act_join.setText(result.getCount()+"人已参加");
		int activityType = result.getActivity().getActivityType();
		switch (activityType) {
		case 3901:
			viewHower.iv_activityType
					.setImageResource(R.drawable.bg_atylist_beat);
			viewHower.tv_atylist_tags_item.setText("活动房源："+ StringUtils.change(result.getHouseName())
					+"  "+result.getSize()+"㎡  "+result.getFloor());
			break;
		case 3902:
			viewHower.iv_activityType.setImageResource(R.drawable.aty_list_te);
			viewHower.tv_atylist_tags_item.setText("活动房源："+StringUtils.change(result.getHouseName())
					+"  "+result.getSize()+"㎡  "+result.getFloor());
			break;
		case 3903:
			viewHower.iv_activityType.setImageResource(R.drawable.aty_list_hui);
			viewHower.tv_atylist_tags_item.setText("活动房源：共"+result.getSumSouce()+"套房源");
			break;
		}
		long actEndTime = result.getActivity().getActEndTime();
		long actStartTime = result.getActivity().getActStartTime();
		long currentTime = DateUtils.currentTime();

		if (currentTime > actStartTime && currentTime < actEndTime) {
			// 进行中
			viewHower.tv_act_time.setText("开始时间："+DateUtils.getTime("yyyy-MM-dd HH:mm", actStartTime));
			viewHower.iv_atyState.setImageResource(R.drawable.aty_list_on);
			viewHower.tv_remind.setVisibility(View.VISIBLE);
			viewHower.tv_remind.setText("立即参加");
			viewHower.tv_remind.setTextColor(Color.parseColor("#ffffff"));
			viewHower.tv_remind.setBackgroundColor(Color.parseColor("#EE4B4C"));
			viewHower.tv_remind.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					BuildActivity activity = dList.get(position).getActivity();
					int actType = activity.getActivityType();
					String activityPoolId = activity.getId() + "";
					String buildingId = dList.get(position).getId() + "";
					String activityId = activity.getActivityId() + "";
					switch (actType) {
					case 3901:
						Intent bidIntent = new Intent(context,
								BiddingActivity.class);
						bidIntent.putExtra("activityPoolId", activityPoolId);
						bidIntent.putExtra("buildingId", buildingId);
						bidIntent.putExtra("activityId", activityId);
						context.startActivity(bidIntent);
						break;
					case 3902:
						Intent daySpecialIntent = new Intent(context,
								EveryDaySpecialActivity.class);
						daySpecialIntent.putExtra("activityPoolId",
								activityPoolId);
						daySpecialIntent.putExtra("buildingId", buildingId);
						daySpecialIntent.putExtra("activityId", activityId);
						context.startActivity(daySpecialIntent);
						break;
					case 3903:
						Intent agentRecommendIntent = new Intent(context,
								AgentRecommendActivity.class);
						agentRecommendIntent.putExtra("activityPoolId",
								activityPoolId);
						agentRecommendIntent.putExtra("buildingId", buildingId);
						agentRecommendIntent.putExtra("activityId", activityId);
						context.startActivity(agentRecommendIntent);
						break;
					}
				}
			});
		}
		if (currentTime > actEndTime) {
			// 已结束
			viewHower.tv_act_time.setText("结束时间："+DateUtils.getTime("yyyy-MM-dd HH:mm", actEndTime));
			viewHower.iv_atyState.setImageResource(R.drawable.aty_list_end);
			viewHower.tv_remind.setVisibility(View.INVISIBLE);
		}
		if (currentTime < actStartTime) {
			// 即将开始
			viewHower.tv_act_time.setText("开始时间："+DateUtils.getTime("yyyy-MM-dd HH:mm", actStartTime));
			final int activityPoolId = result.getActivity().getId();
			final String remindId = result.getRemaindId();
			String remindState = "";
			remindState = result.getRemaind();
			viewHower.tv_remind.setVisibility(View.VISIBLE);
			viewHower.tv_remind.setTextColor(Color.parseColor("#ff9114"));
			viewHower.iv_atyState.setImageResource(R.drawable.aty_list_begin);
			if (remindState.equals("1")) {
				// 有提醒
				viewHower.tv_remind.setText("已设置提醒");
				viewHower.tv_remind.setBackgroundColor(Color.parseColor("#FFE3CB"));
				viewHower.tv_remind.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(final View v) {
						if(!UserInfoData.isSingIn()){
							showToast("您还未登录，请登录");
							return;
						}
						CustomDialog.Builder builder = new CustomDialog.Builder(
								context);
						builder.setTitle("取消提醒");
						builder.setContent("取消后您将不会收到任何消息提醒，可能会错过活动，确定取消提醒吗？");
						builder.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
	
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
	
									}
								});
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
	
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										postCancelRemind(remindId, position);
										dialog.dismiss();
									}
								});
						builder.create().show();

					}
				});
			}else if (remindState.equals("0")) {
				// 没有提醒
				viewHower.tv_remind.setText("提醒我");
				viewHower.tv_remind.setBackgroundColor(Color.parseColor("#FFE3CB"));
				viewHower.tv_remind.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(final View v) {
						if(!UserInfoData.isSingIn()){
							goToOthers(LoginActivity.class);
							return;
						}
						CustomDialog.Builder builder = new CustomDialog.Builder(
								context);
						builder.setTitle("确认提醒");
						builder.setContent("确认提醒后，活动开始您将收到1条短信提醒。");
						builder.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
	
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
	
									}
								});
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
	
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										postRemindMe(activityPoolId,position);
										dialog.dismiss();
									}
	
								});
						builder.create().show();
					}
				});
			}
		}
		return convertView;
	}

	/**
	 * 
	 * @Title: postRemindMe
	 * @Description: 提交提醒我
	 * @return: void
	 */
	protected void postRemindMe(int activityPoolId, final int position) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("activityPoolId", activityPoolId + "");
		map.put("tokenId", tokenId);
		SimpleRequest<RemindMeBean> req = new SimpleRequest<RemindMeBean>(
				API.REMIND, RemindMeBean.class, new Listener<RemindMeBean>() {
					@Override
					public void onResponse(RemindMeBean response) {
						if (null != response) {
							if(0 == response.getStatus()){
								Result result = dList.get(position);
								result.setRemaind("1");
								result.setRemaindId(response.getResult() + "");
								showToast("提醒我成功");
								notifyDataSetChanged();
							}else{
								showToast(response.getMsg());
							}
						}
					}

				}, map);
		UserApplication.getInstance().addToRequestQueue(req);
	}

	/**
	 * 
	 * @Title: postCancelRemind
	 * @Description: 取消提醒我
	 * @return: void
	 */
	protected void postCancelRemind(String remindId, final int position) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("remindId", remindId);
		map.put("tokenId", tokenId);
		SimpleRequest<MessageBean> req = new SimpleRequest<MessageBean>(
				API.CANCEL_REMIND, MessageBean.class,
				new Listener<MessageBean>() {
					@Override
					public void onResponse(MessageBean response) {
						if (null != response) {
							if(0 == response.getStatus()){
								Result result = dList.get(position);
								result.setRemaind("0");
								showToast("取消提醒我成功");
								notifyDataSetChanged();
							}else{
								showToast(response.getMsg());
							}
						}
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);

	}


	public class ViewHolder {
		TextView tv_atylist_tags_item;// 标签
		ImageView iv_atylist_mainimage_item, iv_atyState;
		TextView buildName;
		ImageView iv_activityType;
		TextView tv_remind;
		TextView tv_act_time;
		TextView tv_act_join;

		public ViewHolder(View convertView) {
			// 活动状态
			iv_atyState = (ImageView) convertView
					.findViewById(R.id.iv_atylist_activityTag);
			// 楼盘名称
			buildName = (TextView) convertView
					.findViewById(R.id.tv_atylist_name_item);
			// 楼盘主图
			iv_atylist_mainimage_item = (ImageView) convertView
					.findViewById(R.id.iv_atylist_mainimage_item);
	        int width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
	        float height = width/2.27f;//得到对应的高
	        iv_atylist_mainimage_item.getLayoutParams().height = (int)height;
	        iv_atylist_mainimage_item.requestLayout();
			//活动开始/结束时间
			tv_act_time = (TextView)convertView.findViewById(R.id.tv_act_time);
			//参与用户人数
			tv_act_join = (TextView)convertView.findViewById(R.id.tv_act_join);
			// 房源信息
			tv_atylist_tags_item = (TextView) convertView
					.findViewById(R.id.tv_atylist_tags_item);
			// 活动类型
			iv_activityType = (ImageView) convertView
					.findViewById(R.id.iv_atylist_activityTypeIcon_item);
			// 提醒我
			tv_remind = (TextView) convertView.findViewById(R.id.tv_remind);

		}

	}

}
