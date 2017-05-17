package com.flzc.fanglian.model;

import java.io.Serializable;

public class OrderDetailsBean implements Serializable {

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

		private int actSumId;
		private int orderId;
		private String payTime;
		private int actId;
		private String actEndTime;
		private int orderStatus;
		private String actName;
		private int buildingId;
		private String referPrice;
		private String buildingName;
		private String actStartTime;
		private String orderAmount;
		private String orderTime;
		private int payType;
		private String builder;
		private int actType;
		private String orderFlow;
		private String  auctionPrice;
		private String balance;

		
		public String getBalance() {
			return balance;
		}

		public void setBalance(String balance) {
			this.balance = balance;
		}

		public String getAuctionPrice() {
			return auctionPrice;
		}

		public void setAuctionPrice(String auctionPrice) {
			this.auctionPrice = auctionPrice;
		}

		public int getActSumId() {
			return actSumId;
		}

		public void setActSumId(int actSumId) {
			this.actSumId = actSumId;
		}

		public int getOrderId() {
			return orderId;
		}

		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}

		public String getPayTime() {
			return payTime;
		}

		public void setPayTime(String payTime) {
			this.payTime = payTime;
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

		public String getReferPrice() {
			return referPrice;
		}

		public void setReferPrice(String referPrice) {
			this.referPrice = referPrice;
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

		public String getBuilder() {
			return builder;
		}

		public void setBuilder(String builder) {
			this.builder = builder;
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
