package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class BiddingUserActivityBean implements Serializable {

	private String msg;
	private int status;
	private Result result;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public class Result implements Serializable {
		private List<ListUser> list;

		public List<ListUser> getList() {
			return list;
		}

		public void setList(List<ListUser> list) {
			this.list = list;
		}

		public class ListUser implements Serializable {

			private int activityId;
			private String phone;
			private String nickName;
			private String headUrl;
			private String biddingPrice;
			private long joinTime;
			private int auctionStatus;

			
			public int getAuctionStatus() {
				return auctionStatus;
			}

			public void setAuctionStatus(int auctionStatus) {
				this.auctionStatus = auctionStatus;
			}

			public long getJoinTime() {
				return joinTime;
			}

			public void setJoinTime(long joinTime) {
				this.joinTime = joinTime;
			}

			public int getActivityId() {
				return activityId;
			}

			public void setActivityId(int activityId) {
				this.activityId = activityId;
			}

			public String getPhone() {
				return phone;
			}

			public void setPhone(String phone) {
				this.phone = phone;
			}

			public String getNickName() {
				return nickName;
			}

			public void setNickName(String nickName) {
				this.nickName = nickName;
			}

			public String getHeadUrl() {
				return headUrl;
			}

			public void setHeadUrl(String headUrl) {
				this.headUrl = headUrl;
			}

			public String getBiddingPrice() {
				return biddingPrice;
			}

			public void setBiddingPrice(String biddingPrice) {
				this.biddingPrice = biddingPrice;
			}

		}

	}

}
