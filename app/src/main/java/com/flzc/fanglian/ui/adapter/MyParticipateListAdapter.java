package com.flzc.fanglian.ui.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.MyBaseAdapter;
import com.flzc.fanglian.model.UserJoinBean.Result;
import com.flzc.fanglian.ui.agent_activity.AgentRecommendActivity;
import com.flzc.fanglian.ui.bidding_activity.BiddingActivity;
import com.flzc.fanglian.ui.everyday_special_activity.EveryDaySpecialActivity;
import com.flzc.fanglian.util.DateUtils;
import com.flzc.fanglian.util.StringUtils;

/**
 * 
 * @ClassName: MyParticipateListAdapter
 * @Description: 我参与的活动 列表适配器
 * @author: Tien.
 * @date: 2016年3月3日 下午8:13:38
 */
public class MyParticipateListAdapter extends MyBaseAdapter<Result> {

	private DecimalFormat df;

	public MyParticipateListAdapter(Context context, List<Result> list) {
		super(context, list);
		df = new DecimalFormat("###.00");  
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_myparticipate, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Result result = dList.get(position);
		if (result != null) {
			// 0全部 2进行中 3已结束
			String actStatus = result.getActStatus();
			if (StringUtils.isNotBlank(actStatus)) {
				if ("2".equals(actStatus)) {
					viewHolder.tv_timeType_item_participate.setText("进行中");
				} else if ("3".equals(actStatus)) {
					viewHolder.tv_timeType_item_participate.setText("已结束");
				} else if ("1".equals(actStatus)) {
					viewHolder.tv_timeType_item_participate.setText("即将开始");
				}
			} else {
				viewHolder.tv_timeType_item_participate
						.setVisibility(View.GONE);
			}

			String activityType = result.getActivityType();
			if (StringUtils.isNotBlank(activityType)) {
				if ("3901".equals(activityType)) {
					viewHolder.tv_actType_item_participate.setText("竞拍");
					viewHolder.img_actType_item_participate
							.setImageResource(R.drawable.act_type_pai);
				} else if ("3902".equals(activityType)) {
					viewHolder.tv_actType_item_participate.setText("天天特价");
					viewHolder.img_actType_item_participate
							.setImageResource(R.drawable.act_type_te);
				} else if ("3903".equals(activityType)) {
					viewHolder.tv_actType_item_participate.setText("优惠房");
					viewHolder.img_actType_item_participate
							.setImageResource(R.drawable.act_type_bighui);
				}
			} else {
				viewHolder.tv_actType_item_participate.setVisibility(View.GONE);
				viewHolder.img_actType_item_participate
						.setVisibility(View.GONE);
			}

			viewHolder.tv_buildingName_item_participate.setText(result
					.getActivityName());
			viewHolder.tv_priceHint_item_participate.setText("参考价：");
			viewHolder.tv_price_item_participate.setText("¥12345/㎡");
			// 竞拍结果
			viewHolder.tv_actStatus_item_participate.setVisibility(View.GONE);
			// 竞拍成功没有给字段
			viewHolder.tv_hintInfo_item_participate.setVisibility(View.GONE);
			if (activityType.equals("3901")) {// 竞拍
				if("1".equals(actStatus)){
					viewHolder.tv_actStatus_item_participate.setVisibility(View.GONE);
				}else{
					int auctionResult = result.getAuctionResult();
					if (StringUtils.isNotBlank(auctionResult + "")) {
						viewHolder.tv_actStatus_item_participate
								.setVisibility(View.VISIBLE);
						if (auctionResult == 0) {
							viewHolder.tv_actStatus_item_participate
									.setText("竞拍失败");
							viewHolder.tv_hintInfo_item_participate
									.setVisibility(View.VISIBLE);
							viewHolder.tv_rewardInfo_item_participate
									.setVisibility(View.GONE);
						} else if (auctionResult == 1) {
							viewHolder.tv_actStatus_item_participate
									.setText("竞拍成功");
							viewHolder.tv_hintInfo_item_participate
									.setVisibility(View.GONE);
							viewHolder.tv_rewardInfo_item_participate
									.setVisibility(View.VISIBLE);
						}
					} else {
						viewHolder.tv_actStatus_item_participate
								.setVisibility(View.GONE);
					}
				}
			}
			if(activityType.equals("3902")){//特价
				if("3".equals(actStatus)){
					viewHolder.tv_actStatus_item_participate
					.setVisibility(View.VISIBLE);
					if(result.getTicketCount() == 0){
						viewHolder.tv_actStatus_item_participate
						.setText("很遗憾，抢购失败");
						viewHolder.tv_hintInfo_item_participate
						.setVisibility(View.VISIBLE);
						viewHolder.tv_rewardInfo_item_participate
						.setVisibility(View.GONE);
					}else{
						viewHolder.tv_actStatus_item_participate
						.setText("抢购成功");
						viewHolder.tv_hintInfo_item_participate
						.setVisibility(View.GONE);
						viewHolder.tv_rewardInfo_item_participate
						.setVisibility(View.VISIBLE);
					}
				}else{
					viewHolder.tv_actStatus_item_participate
					.setVisibility(View.GONE);
				}
			}
			String sAmount = StringUtils.intMoney(result.getAmount());
			viewHolder.tv_actInfo_item_participate.setVisibility(View.GONE);
			if (activityType.equals("3901")) {// 竞拍
				if (!TextUtils.isEmpty(actStatus)) {
					if (actStatus.equals("2")) {// 进行中
						viewHolder.tv_actStatus_item_participate
						.setVisibility(View.GONE);
						viewHolder.tv_hintInfo_item_participate
						.setVisibility(View.GONE);
						
						if (result.getBiddingList().size() == 0) {
							viewHolder.tv_actInfo_item_participate
									.setVisibility(View.GONE);
						} else if (result.getBiddingList().size() == 1) {
							viewHolder.tv_actInfo_item_participate.setVisibility(View.VISIBLE);
							viewHolder.tv_actInfo_item_participate
									.setText("竞拍第一次出价："
											+ result.getBiddingList().get(0)
													.getBiddingPrice() + "元");
						} else if (result.getBiddingList().size() == 2) {
							viewHolder.tv_actInfo_item_participate.setVisibility(View.VISIBLE);
							viewHolder.tv_actInfo_item_participate
									.setText("竞拍第一次出价："
											+ result.getBiddingList().get(1)
													.getBiddingPrice()
											+ "元\n"
											+ "竞拍第二次出价："
											+ result.getBiddingList().get(0)
													.getBiddingPrice() + "元");
						}else if (result.getBiddingList().size() == 3) {
							viewHolder.tv_actInfo_item_participate.setVisibility(View.VISIBLE);
							viewHolder.tv_actInfo_item_participate
									.setText("竞拍第一次出价："
											+ result.getBiddingList().get(2)
													.getBiddingPrice()
											+ "元\n"
											+ "竞拍第二次出价："
											+ result.getBiddingList().get(1)
													.getBiddingPrice()
											+ "元\n"
											+ "竞拍第三次出价："
											+ result.getBiddingList().get(0)
													.getBiddingPrice() + "元");
						}

					} 
					if(actStatus.equals("3")){
						viewHolder.tv_actStatus_item_participate
						.setVisibility(View.VISIBLE);
						int auctionResult = result.getAuctionResult();
						if(auctionResult == 1){//成功
							viewHolder.tv_actInfo_item_participate.setVisibility(View.VISIBLE);
							if(result.getBiddingList().size() > 0){
								viewHolder.tv_actInfo_item_participate
								.setText("竞拍出价："+result.getBiddingList().get(0)
										.getBiddingPrice() + "元");
							}
							
						}
						if(auctionResult == 0){//失败
							viewHolder.tv_actInfo_item_participate.setVisibility(View.VISIBLE);
							if(result.getBiddingList().size() > 0){
								viewHolder.tv_actInfo_item_participate
								.setText("竞拍出价："
										+ result.getBiddingList().get(0)
										.getBiddingPrice()
								+ "元\n"
								+ "最高竞拍价："
								+ result.getTopPrice()
								+ "元");
							}else{
								viewHolder.tv_actInfo_item_participate
								.setText("最高竞拍价："
								+ result.getTopPrice()
								+ "元");
							}
						}
					}
//					else if (actStatus.equals("3")) {// 已结束
//						viewHolder.tv_actInfo_item_participate.setVisibility(View.VISIBLE);
//						viewHolder.tv_actInfo_item_participate.setText("竞拍出价："
//								+ sAmount + "元");
//					}
				}

			}
//			else {
//				viewHolder.tv_actInfo_item_participate.setVisibility(View.VISIBLE);
//				viewHolder.tv_actInfo_item_participate.setText(result
//						.getHouseType()
//						+ "  "
//						+ result.getArea()
//						+ "  "
//						+ result.getFloor());
//			}
			// 如果获得优惠券是0隐藏

			viewHolder.tv_rewardInfo_item_participate.setVisibility(View.GONE);
			//获得。。张房连券
			int ticketCount = result.getTicketCount();
			if(ticketCount == 0){
				viewHolder.tv_rewardInfo_item_participate
				.setVisibility(View.GONE);
			}else{
				viewHolder.tv_rewardInfo_item_participate
				.setText("获得：房链券" +ticketCount + "张");
				viewHolder.tv_rewardInfo_item_participate.setVisibility(View.VISIBLE);
			}
//			if (!TextUtils.isEmpty(sAmount + "")) {
//				viewHolder.tv_rewardInfo_item_participate
//						.setVisibility(View.GONE);
//				if (activityType.equals("3901")) {
//					if (TextUtils.equals("0", sAmount)) {
//						viewHolder.tv_rewardInfo_item_participate
//								.setVisibility(View.GONE);
//					} else {
//						viewHolder.tv_rewardInfo_item_participate
//								.setText("获得：单价优惠券（" + sAmount
//										+ "元/㎡）");
//						viewHolder.tv_rewardInfo_item_participate
//								.setVisibility(View.VISIBLE);
//					}
//				}
//				if (activityType.equals("3902")) {
//					if (TextUtils.equals("0", sAmount)) {
//						viewHolder.tv_rewardInfo_item_participate
//								.setVisibility(View.GONE);
//					} else {
//						try {
//							double amount = Double.parseDouble(sAmount);
//							viewHolder.tv_rewardInfo_item_participate
//							.setText("获得：特价优惠券（" + StringUtils.intMoney(df.format(amount/10000))+ "万）");
//						} catch (Exception e) {
//							showToast("数据错误");
//						}
//						viewHolder.tv_rewardInfo_item_participate
//								.setVisibility(View.VISIBLE);
//					}
//				}
//				if (activityType.equals("3903")) {
//					if (TextUtils.equals("0",sAmount)) {
//						viewHolder.tv_rewardInfo_item_participate
//								.setVisibility(View.GONE);
//					} else {
//						viewHolder.tv_rewardInfo_item_participate
//								.setText("获得：特价优惠券（" + sAmount
//										+ "元）");
//						viewHolder.tv_rewardInfo_item_participate
//								.setVisibility(View.VISIBLE);
//					}
//				}
//
//			} else {
//				viewHolder.tv_rewardInfo_item_participate
//						.setVisibility(View.GONE);
//			}
			viewHolder.tv_time_item_participate.setText(DateUtils
					.formatDate(result.getInvolvementDate()));
		}

		viewHolder.img_checkAct_item_participate
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						int actType = Integer.parseInt(dList.get(position)
								.getActivityType());
						Intent intent = new Intent();
						String activityPoolId = dList.get(position)
								.getActivityPoolId();
						String buildingId = dList.get(position).getBuildingId();
						String activityId = dList.get(position).getActivityId();
						String remind = dList.get(position).getRemind();
						String remindId = dList.get(position).getRemindId();

						switch (actType) {
						case 3901:
							// goOthers(BiddingActivity.class);
							intent.setClass(context, BiddingActivity.class);
							intent.putExtra("activityPoolId", activityPoolId);
							intent.putExtra("buildingId", buildingId);
							intent.putExtra("activityId", activityId);
							intent.putExtra("remind", remind);
							intent.putExtra("remindId", remindId);
							context.startActivity(intent);
							break;
						case 3902:
							intent.setClass(context,
									EveryDaySpecialActivity.class);
							intent.putExtra("activityPoolId", activityPoolId);
							intent.putExtra("buildingId", buildingId);
							intent.putExtra("activityId", activityId);
							intent.putExtra("remind", remind);
							intent.putExtra("remindId", remindId);
							context.startActivity(intent);
							break;
						case 3903:
							intent.setClass(context,
									AgentRecommendActivity.class);
							intent.putExtra("activityPoolId", activityPoolId);
							intent.putExtra("buildingId", buildingId);
							intent.putExtra("activityId", activityId);
							intent.putExtra("remind", remind);
							intent.putExtra("remindId", remindId);
							context.startActivity(intent);
							break;
						}
					}
				});

		return convertView;
	}

	static class ViewHolder {

		TextView tv_actType_item_participate, tv_timeType_item_participate,
				tv_buildingName_item_participate,
				tv_priceHint_item_participate, tv_price_item_participate,
				tv_actInfo_item_participate, tv_actStatus_item_participate,
				tv_rewardInfo_item_participate, tv_time_item_participate,
				tv_hintInfo_item_participate;
		ImageView img_checkAct_item_participate, img_actType_item_participate;

		public ViewHolder(View convertView) {
			tv_actType_item_participate = (TextView) convertView
					.findViewById(R.id.tv_actType_item_participate);
			tv_timeType_item_participate = (TextView) convertView
					.findViewById(R.id.tv_timeType_item_participate);
			tv_buildingName_item_participate = (TextView) convertView
					.findViewById(R.id.tv_buildingName_item_participate);
			tv_priceHint_item_participate = (TextView) convertView
					.findViewById(R.id.tv_priceHint_item_participate);
			tv_price_item_participate = (TextView) convertView
					.findViewById(R.id.tv_price_item_participate);
			tv_actInfo_item_participate = (TextView) convertView
					.findViewById(R.id.tv_actInfo_item_participate);
			tv_actStatus_item_participate = (TextView) convertView
					.findViewById(R.id.tv_actStatus_item_participate);
			tv_rewardInfo_item_participate = (TextView) convertView
					.findViewById(R.id.tv_rewardInfo_item_participate);
			tv_time_item_participate = (TextView) convertView
					.findViewById(R.id.tv_time_item_participate);

			img_checkAct_item_participate = (ImageView) convertView
					.findViewById(R.id.img_checkAct_item_participate);
			img_actType_item_participate = (ImageView) convertView
					.findViewById(R.id.img_actType_item_participate);
			tv_hintInfo_item_participate = (TextView) convertView
					.findViewById(R.id.tv_hintInfo_item_participate);
		}

	}

}
