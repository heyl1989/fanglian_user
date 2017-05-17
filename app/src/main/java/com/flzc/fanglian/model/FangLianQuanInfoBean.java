package com.flzc.fanglian.model;

import java.io.Serializable;

public class FangLianQuanInfoBean implements Serializable {

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

		private String amount;
		private Long endDate;
		private String saleTel;
		private String memo;
		private String buildingName;
		private String unit;
		private String size;
		private String saleAddress;
		private String ticketTypeName;
		private int activityType;
		private String ticketCode;
		private Long startDate;
		private int status;
		private String successPrice;
		private String houseType;
		private String price;
		private String buildingId;
		private String sourceId;
		private String summaryId;
		private String minPrice;
		
		
		
		public String getMinPrice() {
			return minPrice;
		}

		public void setMinPrice(String minPrice) {
			this.minPrice = minPrice;
		}

		public String getBuildingId() {
			return buildingId;
		}

		public void setBuildingId(String buildingId) {
			this.buildingId = buildingId;
		}

		public String getSourceId() {
			return sourceId;
		}

		public void setSourceId(String sourceId) {
			this.sourceId = sourceId;
		}

		public String getSummaryId() {
			return summaryId;
		}

		public void setSummaryId(String summaryId) {
			this.summaryId = summaryId;
		}

		public String getSuccessPrice() {
			return successPrice;
		}

		public void setSuccessPrice(String successPrice) {
			this.successPrice = successPrice;
		}

		public String getHouseType() {
			return houseType;
		}

		public void setHouseType(String houseType) {
			this.houseType = houseType;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public Long getEndDate() {
			return endDate;
		}

		public void setEndDate(Long endDate) {
			this.endDate = endDate;
		}

		public String getSaleTel() {
			return saleTel;
		}

		public void setSaleTel(String saleTel) {
			this.saleTel = saleTel;
		}

		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}

		public String getBuildingName() {
			return buildingName;
		}

		public void setBuildingName(String buildingName) {
			this.buildingName = buildingName;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}

		public String getSaleAddress() {
			return saleAddress;
		}

		public void setSaleAddress(String saleAddress) {
			this.saleAddress = saleAddress;
		}

		public String getTicketTypeName() {
			return ticketTypeName;
		}

		public void setTicketTypeName(String ticketTypeName) {
			this.ticketTypeName = ticketTypeName;
		}

		public int getActivityType() {
			return activityType;
		}

		public void setActivityType(int activityType) {
			this.activityType = activityType;
		}

		public String getTicketCode() {
			return ticketCode;
		}

		public void setTicketCode(String ticketCode) {
			this.ticketCode = ticketCode;
		}

		public Long getStartDate() {
			return startDate;
		}

		public void setStartDate(Long startDate) {
			this.startDate = startDate;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

	}

}
