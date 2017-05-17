package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class SaveUserBidding implements Serializable {

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

		private String phone;
		private String price;
		private String headUrl;
		private String userName;
		private int biddingCount;
		private List<BiddingPriceList> biddingPriceList;

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getHeadUrl() {
			return headUrl;
		}

		public void setHeadUrl(String headUrl) {
			this.headUrl = headUrl;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public int getBiddingCount() {
			return biddingCount;
		}

		public void setBiddingCount(int biddingCount) {
			this.biddingCount = biddingCount;
		}

		public List<BiddingPriceList> getBiddingPriceList() {
			return biddingPriceList;
		}

		public void setBiddingPriceList(List<BiddingPriceList> biddingPriceList) {
			this.biddingPriceList = biddingPriceList;
		}

		public class BiddingPriceList implements Serializable {

			private int activityId;
			private Long createTime;
			private String biddingPrice;
			private int userId;

			public int getActivityId() {
				return activityId;
			}

			public void setActivityId(int activityId) {
				this.activityId = activityId;
			}

			public Long getCreateTime() {
				return createTime;
			}

			public void setCreateTime(Long createTime) {
				this.createTime = createTime;
			}

			public String getBiddingPrice() {
				return biddingPrice;
			}

			public void setBiddingPrice(String biddingPrice) {
				this.biddingPrice = biddingPrice;
			}

			public int getUserId() {
				return userId;
			}

			public void setUserId(int userId) {
				this.userId = userId;
			}

		}
	}
}
