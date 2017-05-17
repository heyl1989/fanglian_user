package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class SearchBean implements Serializable {

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

		private String soldPrice;
		private String name;
		private int id;
		private String master;
		private List<SearchActivity> activity;

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

		public List<SearchActivity> getActivity() {
			return activity;
		}

		public void setActivity(List<SearchActivity> activity) {
			this.activity = activity;
		}

		public List<Tags> getTags() {
			return tags;
		}

		public void setTags(List<Tags> tags) {
			this.tags = tags;
		}

		public class SearchActivity implements Serializable {

			private Long releaseTime;
			private String createAuthor;
			private String activityName;
			private Long actEndTime;
			private Long updateTime;
			private Long preheatEndTime;
			private String buildingId;
			private String activityId;
			private Long actStartTime;
			private Long preheatStartTime;
			private Long createTime;
			private String releaseStatus;
			private String id;
			private String activityType;

			public Long getReleaseTime() {
				return releaseTime;
			}

			public void setReleaseTime(Long releaseTime) {
				this.releaseTime = releaseTime;
			}

			public String getCreateAuthor() {
				return createAuthor;
			}

			public void setCreateAuthor(String createAuthor) {
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

			public String getBuildingId() {
				return buildingId;
			}

			public void setBuildingId(String buildingId) {
				this.buildingId = buildingId;
			}

			public String getActivityId() {
				return activityId;
			}

			public void setActivityId(String activityId) {
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

			public String getReleaseStatus() {
				return releaseStatus;
			}

			public void setReleaseStatus(String releaseStatus) {
				this.releaseStatus = releaseStatus;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getActivityType() {
				return activityType;
			}

			public void setActivityType(String activityType) {
				this.activityType = activityType;
			}

		}

		public List<Tags> tags;

		public class Tags implements Serializable {

			private String code;
			private Long createTime;
			private String parentCode;
			private String name;
			private String orderNum;
			private String id;
			private String type;
			private String onlineFlag;
			private String version;
			private String brandFlag;
			private String status;

			public String getCode() {
				return code;
			}

			public void setCode(String code) {
				this.code = code;
			}

			public Long getCreateTime() {
				return createTime;
			}

			public void setCreateTime(Long createTime) {
				this.createTime = createTime;
			}

			public String getParentCode() {
				return parentCode;
			}

			public void setParentCode(String parentCode) {
				this.parentCode = parentCode;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getOrderNum() {
				return orderNum;
			}

			public void setOrderNum(String orderNum) {
				this.orderNum = orderNum;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getOnlineFlag() {
				return onlineFlag;
			}

			public void setOnlineFlag(String onlineFlag) {
				this.onlineFlag = onlineFlag;
			}

			public String getVersion() {
				return version;
			}

			public void setVersion(String version) {
				this.version = version;
			}

			public String getBrandFlag() {
				return brandFlag;
			}

			public void setBrandFlag(String brandFlag) {
				this.brandFlag = brandFlag;
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
