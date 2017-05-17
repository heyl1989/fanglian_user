package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class NewHouseListBean implements Serializable {

	private int status;
	private String msg;
	private List<Result> result;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}

	public class Result implements Serializable {

		private int id;//楼盘ID
		private String soldPrice;//楼盘价格
		private String address;//楼盘地址
		private String name;//楼盘名称
		private String master;//楼盘主图	
		private List<Tags> tags;//楼盘标签列表
		private List<Activity> activity;//楼盘活动列表
		
		
		
		public String getMaster() {
			return master;
		}

		public void setMaster(String master) {
			this.master = master;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getSoldPrice() {
			return soldPrice;
		}

		public void setSoldPrice(String soldPrice) {
			this.soldPrice = soldPrice;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Tags> getTags() {
			return tags;
		}

		public void setTags(List<Tags> tags) {
			this.tags = tags;
		}

		public List<Activity> getActivity() {
			return activity;
		}

		public void setActivity(List<Activity> activity) {
			this.activity = activity;
		}

		public class Tags implements Serializable {

			private int id;
			private Long createTime;
			private int brandFlag;
			private int onlineFlag;
			private int status;
			private String name;
			private int orderNum;
			private int code;
			private int parentCode;
			public int getId() {
				return id;
			}
			public void setId(int id) {
				this.id = id;
			}
			public Long getCreateTime() {
				return createTime;
			}
			public void setCreateTime(Long createTime) {
				this.createTime = createTime;
			}
			public int getBrandFlag() {
				return brandFlag;
			}
			public void setBrandFlag(int brandFlag) {
				this.brandFlag = brandFlag;
			}
			public int getOnlineFlag() {
				return onlineFlag;
			}
			public void setOnlineFlag(int onlineFlag) {
				this.onlineFlag = onlineFlag;
			}
			public int getStatus() {
				return status;
			}
			public void setStatus(int status) {
				this.status = status;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public int getOrderNum() {
				return orderNum;
			}
			public void setOrderNum(int orderNum) {
				this.orderNum = orderNum;
			}
			public int getCode() {
				return code;
			}
			public void setCode(int code) {
				this.code = code;
			}
			public int getParentCode() {
				return parentCode;
			}
			public void setParentCode(int parentCode) {
				this.parentCode = parentCode;
			}
			
			

		}

		public class Activity implements Serializable {

			private Long createTime;
			private int releaseStatus;
			private Long actEndTime;
			private Long updateTime;
			private Long actStartTime;
			private Long preheatEndTime;
			private int activityId;
			private int id;
			private int createAuthor;
			private Long releaseTime;
			private int activityType;
			private String activityName;
			private int buildingId;
			private Long preheatStartTime;
			
			public Long getCreateTime() {
				return createTime;
			}
			public void setCreateTime(Long createTime) {
				this.createTime = createTime;
			}
			public int getReleaseStatus() {
				return releaseStatus;
			}
			public void setReleaseStatus(int releaseStatus) {
				this.releaseStatus = releaseStatus;
			}
			public Long getActEndTime() {
				return actEndTime;
			}
			public void setActEndTime(Long actEndTime) {
				this.actEndTime = actEndTime;
			}
			public Long getUpdateTime() {
				return updateTime;
			}
			public void setUpdateTime(Long updateTime) {
				this.updateTime = updateTime;
			}
			public Long getActStartTime() {
				return actStartTime;
			}
			public void setActStartTime(Long actStartTime) {
				this.actStartTime = actStartTime;
			}
			public Long getPreheatEndTime() {
				return preheatEndTime;
			}
			public void setPreheatEndTime(Long preheatEndTime) {
				this.preheatEndTime = preheatEndTime;
			}
			public int getActivityId() {
				return activityId;
			}
			public void setActivityId(int activityId) {
				this.activityId = activityId;
			}
			public int getId() {
				return id;
			}
			public void setId(int id) {
				this.id = id;
			}
			public int getCreateAuthor() {
				return createAuthor;
			}
			public void setCreateAuthor(int createAuthor) {
				this.createAuthor = createAuthor;
			}
			public Long getReleaseTime() {
				return releaseTime;
			}
			public void setReleaseTime(Long releaseTime) {
				this.releaseTime = releaseTime;
			}
			public int getActivityType() {
				return activityType;
			}
			public void setActivityType(int activityType) {
				this.activityType = activityType;
			}
			public String getActivityName() {
				return activityName;
			}
			public void setActivityName(String activityName) {
				this.activityName = activityName;
			}
			public int getBuildingId() {
				return buildingId;
			}
			public void setBuildingId(int buildingId) {
				this.buildingId = buildingId;
			}
			public Long getPreheatStartTime() {
				return preheatStartTime;
			}
			public void setPreheatStartTime(Long preheatStartTime) {
				this.preheatStartTime = preheatStartTime;
			}
			
			

		}

	}
}
