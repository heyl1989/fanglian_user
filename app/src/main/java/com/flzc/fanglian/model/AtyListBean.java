package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class AtyListBean implements Serializable {

	public String msg;
	public int status;
	public List<Result> result;

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

		private String address;
		private String soldPrice;
		private String name;
		private int id;
		private String master;
		private String remaind;
		private String remaindId;
		private String priceType;
		private String count;
		private String floor;
		private String houseName;
		private String size;
		private String sumSouce;
		private BuildActivity activity;
		private List<Tags> tags;

		
		public String getSumSouce() {
			return sumSouce;
		}

		public void setSumSouce(String sumSouce) {
			this.sumSouce = sumSouce;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public String getFloor() {
			return floor;
		}

		public void setFloor(String floor) {
			this.floor = floor;
		}

		public String getHouseName() {
			return houseName;
		}

		public void setHouseName(String houseName) {
			this.houseName = houseName;
		}

		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}

		public String getPriceType() {
			return priceType;
		}

		public void setPriceType(String priceType) {
			this.priceType = priceType;
		}

		public String getRemaindId() {
			return remaindId;
		}

		public void setRemaindId(String remaindId) {
			this.remaindId = remaindId;
		}

		public String getRemaind() {
			return remaind;
		}

		public void setRemaind(String remaind) {
			this.remaind = remaind;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getSoldPrice() {
			return soldPrice;
		}

		public void setSoldPrice(String soldPrice) {
			this.soldPrice = soldPrice;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getMaster() {
			return master;
		}

		public void setMaster(String master) {
			this.master = master;
		}

		public BuildActivity getActivity() {
			return activity;
		}

		public void setActivity(BuildActivity activity) {
			this.activity = activity;
		}

		public List<Tags> getTags() {
			return tags;
		}

		public void setTags(List<Tags> tags) {
			this.tags = tags;
		}

		public class BuildActivity implements Serializable {

			private Long releaseTime;
			private int createAuthor;
			private String activityName;
			private Long actEndTime;
			private Long updateTime;
			private Long preheatEndTime;
			private int buildingId;
			private int activityId;
			private Long actStartTime;
			private Long preheatStartTime;
			private Long createTime;
			private int releaseStatus;
			private int id;
			private int activityType;
			
			public Long getReleaseTime() {
				return releaseTime;
			}

			public void setReleaseTime(Long releaseTime) {
				this.releaseTime = releaseTime;
			}

			public int getCreateAuthor() {
				return createAuthor;
			}

			public void setCreateAuthor(int createAuthor) {
				this.createAuthor = createAuthor;
			}

			public String getActivityName() {
				return activityName;
			}

			public void setActivityName(String activityName) {
				this.activityName = activityName;
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

			public Long getPreheatEndTime() {
				return preheatEndTime;
			}

			public void setPreheatEndTime(Long preheatEndTime) {
				this.preheatEndTime = preheatEndTime;
			}

			public int getBuildingId() {
				return buildingId;
			}

			public void setBuildingId(int buildingId) {
				this.buildingId = buildingId;
			}

			public int getActivityId() {
				return activityId;
			}

			public void setActivityId(int activityId) {
				this.activityId = activityId;
			}

			public Long getActStartTime() {
				return actStartTime;
			}

			public void setActStartTime(Long actStartTime) {
				this.actStartTime = actStartTime;
			}

			public Long getPreheatStartTime() {
				return preheatStartTime;
			}

			public void setPreheatStartTime(Long preheatStartTime) {
				this.preheatStartTime = preheatStartTime;
			}

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

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public int getActivityType() {
				return activityType;
			}

			public void setActivityType(int activityType) {
				this.activityType = activityType;
			}

		}

		public class Tags implements Serializable {

			private int code;
			private Long createTime;
			private int parentCode;
			private String name;
			private int orderNum;
			private int id;
			private int type;
			private int onlineFlag;
			private int version;
			private int brandFlag;
			private int status;

			public int getCode() {
				return code;
			}

			public void setCode(int code) {
				this.code = code;
			}

			public Long getCreateTime() {
				return createTime;
			}

			public void setCreateTime(Long createTime) {
				this.createTime = createTime;
			}

			public int getParentCode() {
				return parentCode;
			}

			public void setParentCode(int parentCode) {
				this.parentCode = parentCode;
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

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public int getType() {
				return type;
			}

			public void setType(int type) {
				this.type = type;
			}

			public int getOnlineFlag() {
				return onlineFlag;
			}

			public void setOnlineFlag(int onlineFlag) {
				this.onlineFlag = onlineFlag;
			}

			public int getVersion() {
				return version;
			}

			public void setVersion(int version) {
				this.version = version;
			}

			public int getBrandFlag() {
				return brandFlag;
			}

			public void setBrandFlag(int brandFlag) {
				this.brandFlag = brandFlag;
			}

			public int getStatus() {
				return status;
			}

			public void setStatus(int status) {
				this.status = status;
			}

		}

	}

}
