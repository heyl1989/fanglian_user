package com.flzc.fanglian.model;

import java.io.Serializable;

public class PaySucessBean implements Serializable {

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

		private int activityId;
		private String buildingName;
		private int actStatus;
		private String houseSourceId;
		private int userStatus;
		private int counts;
		private int minPrice;
		private String startTime;
		private String endTime;
		private String biddingPrice;
		private String activityPoolId;
		private int buildingId;

		public int getActivityId() {
			return activityId;
		}

		public void setActivityId(int activityId) {
			this.activityId = activityId;
		}

		public String getBuildingName() {
			return buildingName;
		}

		public void setBuildingName(String buildingName) {
			this.buildingName = buildingName;
		}

		public int getActStatus() {
			return actStatus;
		}

		public void setActStatus(int actStatus) {
			this.actStatus = actStatus;
		}

		public String getHouseSourceId() {
			return houseSourceId;
		}

		public void setHouseSourceId(String houseSourceId) {
			this.houseSourceId = houseSourceId;
		}

		public int getUserStatus() {
			return userStatus;
		}

		public void setUserStatus(int userStatus) {
			this.userStatus = userStatus;
		}

		public int getCounts() {
			return counts;
		}

		public void setCounts(int counts) {
			this.counts = counts;
		}

		public int getMinPrice() {
			return minPrice;
		}

		public void setMinPrice(int minPrice) {
			this.minPrice = minPrice;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public String getBiddingPrice() {
			return biddingPrice;
		}

		public void setBiddingPrice(String biddingPrice) {
			this.biddingPrice = biddingPrice;
		}

		public String getActivityPoolId() {
			return activityPoolId;
		}

		public void setActivityPoolId(String activityPoolId) {
			this.activityPoolId = activityPoolId;
		}

		public int getBuildingId() {
			return buildingId;
		}

		public void setBuildingId(int buildingId) {
			this.buildingId = buildingId;
		}

	}

}
