package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class UserOrderListBean implements Serializable {

	private String msg;
	private int status;
	private List<Result> result;

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

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}

	public class Result implements Serializable {

		private int actSumId;
		private String actStatus;
		private int orderId;
		private int actId;
		private String actEndTime;
		private int orderStatus;
		private String actName;
		private int buildingId;
		private String buildingName;
		private String actStartTime;
		private String orderAmount;
		private String orderTime;
		private int payType;
		private int actType;
		private String orderFlow;
		private String master;
		
		public String getMaster() {
			return master;
		}

		public void setMaster(String master) {
			this.master = master;
		}

		public int getActSumId() {
			return actSumId;
		}

		public void setActSumId(int actSumId) {
			this.actSumId = actSumId;
		}

		public String getActStatus() {
			return actStatus;
		}

		public void setActStatus(String actStatus) {
			this.actStatus = actStatus;
		}

		public int getOrderId() {
			return orderId;
		}

		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}

		public int getActId() {
			return actId;
		}

		public void setActId(int actId) {
			this.actId = actId;
		}

		public String getActEndTime() {
			return actEndTime;
		}

		public void setActEndTime(String actEndTime) {
			this.actEndTime = actEndTime;
		}

		public int getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(int orderStatus) {
			this.orderStatus = orderStatus;
		}

		public String getActName() {
			return actName;
		}

		public void setActName(String actName) {
			this.actName = actName;
		}

		public int getBuildingId() {
			return buildingId;
		}

		public void setBuildingId(int buildingId) {
			this.buildingId = buildingId;
		}

		public String getBuildingName() {
			return buildingName;
		}

		public void setBuildingName(String buildingName) {
			this.buildingName = buildingName;
		}

		public String getActStartTime() {
			return actStartTime;
		}

		public void setActStartTime(String actStartTime) {
			this.actStartTime = actStartTime;
		}

		public String getOrderAmount() {
			return orderAmount;
		}

		public void setOrderAmount(String orderAmount) {
			this.orderAmount = orderAmount;
		}

		public String getOrderTime() {
			return orderTime;
		}

		public void setOrderTime(String orderTime) {
			this.orderTime = orderTime;
		}

		public int getPayType() {
			return payType;
		}

		public void setPayType(int payType) {
			this.payType = payType;
		}

		public int getActType() {
			return actType;
		}

		public void setActType(int actType) {
			this.actType = actType;
		}

		public String getOrderFlow() {
			return orderFlow;
		}

		public void setOrderFlow(String orderFlow) {
			this.orderFlow = orderFlow;
		}

	}
}
