package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class UserJoinBean implements Serializable {

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

		private Long involvementDate;
		private String amount;
		private String unit;
		private String actStatus;
		private String activityName;
		private String ticketTypeName;
		private String activityType;
		private String activityId;
		private String activityPoolId;
		private String buildingId;
		private String remind;
		private String remindId;
		private int auctionResult;
		private String houseType;
		private String floor;
		private String area;
		private int ticketCount;
		private String topPrice;
		
		
		public String getTopPrice() {
			return topPrice;
		}

		public void setTopPrice(String topPrice) {
			this.topPrice = topPrice;
		}

		public int getTicketCount() {
			return ticketCount;
		}

		public void setTicketCount(int ticketCount) {
			this.ticketCount = ticketCount;
		}

		public String getHouseType() {
		
			return houseType;
		}
		
		public void setHouseType(String houseType) {
		
			this.houseType = houseType;
		}

		
		public String getFloor() {
		
			return floor;
		}

		
		public void setFloor(String floor) {
		
			this.floor = floor;
		}

		
		public String getArea() {
		
			return area;
		}

		
		public void setArea(String area) {
		
			this.area = area;
		}


		private List<BiddingList> biddingList;
		
		public int getAuctionResult() {
		
			return auctionResult;
		}
		
		public void setAuctionResult(int auctionResult) {
		
			this.auctionResult = auctionResult;
		}

		public List<BiddingList> getBiddingList() {
		
			return biddingList;
		}

		
		public void setBiddingList(List<BiddingList> biddingList) {
		
			this.biddingList = biddingList;
		}


		public String getActivityId() {
		
			return activityId;
		}

		
		public void setActivityId(String activityId) {
		
			this.activityId = activityId;
		}

		
		public String getActivityPoolId() {
		
			return activityPoolId;
		}

		
		public void setActivityPoolId(String activityPoolId) {
		
			this.activityPoolId = activityPoolId;
		}

		
		public String getBuildingId() {
		
			return buildingId;
		}

		
		public void setBuildingId(String buildingId) {
		
			this.buildingId = buildingId;
		}

		
		public String getRemind() {
		
			return remind;
		}

		
		public void setRemind(String remind) {
		
			this.remind = remind;
		}

		
		public String getRemindId() {
		
			return remindId;
		}

		
		public void setRemindId(String remindId) {
		
			this.remindId = remindId;
		}

		public Long getInvolvementDate() {
			return involvementDate;
		}

		public void setInvolvementDate(Long involvementDate) {
			this.involvementDate = involvementDate;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public String getActStatus() {
			return actStatus;
		}

		public void setActStatus(String actStatus) {
			this.actStatus = actStatus;
		}

		public String getActivityName() {
			return activityName;
		}

		public void setActivityName(String activityName) {
			this.activityName = activityName;
		}

		public String getTicketTypeName() {
			return ticketTypeName;
		}

		public void setTicketTypeName(String ticketTypeName) {
			this.ticketTypeName = ticketTypeName;
		}

		public String getActivityType() {
			return activityType;
		}

		public void setActivityType(String activityType) {
			this.activityType = activityType;
		}

		@Override
		public String toString() {

			return "Result [involvementDate=" + involvementDate + ", amount=" + amount + ", unit=" + unit
					+ ", actStatus=" + actStatus + ", activityName=" + activityName + ", ticketTypeName="
					+ ticketTypeName + ", activityType=" + activityType + ", activityId=" + activityId
					+ ", activityPoolId=" + activityPoolId + ", buildingId=" + buildingId + ", remind=" + remind
					+ ", remindId=" + remindId + ", auctionResult=" + auctionResult + ", houseType=" + houseType
					+ ", floor=" + floor + ", area=" + area + ", biddingList=" + biddingList + "]";
		}


		public class BiddingList implements Serializable{
			private int activityId;
			private String biddingPrice;
			private long createTime;
			private int id;
			private int userId;
			
			public int getActivityId() {
			
				return activityId;
			}
			
			public void setActivityId(int activityId) {
			
				this.activityId = activityId;
			}
			
			public String getBiddingPrice() {
			
				return biddingPrice;
			}
			
			public void setBiddingPrice(String biddingPrice) {
			
				this.biddingPrice = biddingPrice;
			}
			
			public long getCreateTime() {
			
				return createTime;
			}
			
			public void setCreateTime(long createTime) {
			
				this.createTime = createTime;
			}
			
			public int getId() {
			
				return id;
			}
			
			public void setId(int id) {
			
				this.id = id;
			}
			
			public int getUserId() {
			
				return userId;
			}
			
			public void setUserId(int userId) {
			
				this.userId = userId;
			}

			@Override
			public String toString() {

				return "BiddingList [activityId=" + activityId + ", biddingPrice=" + biddingPrice + ", createTime="
						+ createTime + ", id=" + id + ", userId=" + userId + "]";
			}
			
		}
		

	}

}
