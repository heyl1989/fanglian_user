package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class FangLianQuanBean implements Serializable {

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
		public List<FangLianQuanList> list;

		public class FangLianQuanList implements Serializable {

			private String sourceId;
			private String amount;
			private Long endDate;
			private String memo;
			private String ticketTypeId;
			private String userId;
			private String buildingName;
			private String unit;
			private Long createTime;
			private String id;
			private String sourceName;
			private String ticketTypeName;
			private String activityPoolId;
			private String ticketCode;
			private Long startDate;
			private String status;

			public String getSourceId() {
				return sourceId;
			}

			public void setSourceId(String sourceId) {
				this.sourceId = sourceId;
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

			public String getMemo() {
				return memo;
			}

			public void setMemo(String memo) {
				this.memo = memo;
			}

			public String getTicketTypeId() {
				return ticketTypeId;
			}

			public void setTicketTypeId(String ticketTypeId) {
				this.ticketTypeId = ticketTypeId;
			}

			public String getUserId() {
				return userId;
			}

			public void setUserId(String userId) {
				this.userId = userId;
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

			public Long getCreateTime() {
				return createTime;
			}

			public void setCreateTime(Long createTime) {
				this.createTime = createTime;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getSourceName() {
				return sourceName;
			}

			public void setSourceName(String sourceName) {
				this.sourceName = sourceName;
			}

			public String getTicketTypeName() {
				return ticketTypeName;
			}

			public void setTicketTypeName(String ticketTypeName) {
				this.ticketTypeName = ticketTypeName;
			}

			public String getActivityPoolId() {
				return activityPoolId;
			}

			public void setActivityPoolId(String activityPoolId) {
				this.activityPoolId = activityPoolId;
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

			public String getStatus() {
				return status;
			}

			public void setStatus(String status) {
				this.status = status;
			}
		}

	}

}
