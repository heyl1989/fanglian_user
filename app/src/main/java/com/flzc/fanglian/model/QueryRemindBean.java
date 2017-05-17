package com.flzc.fanglian.model;

import java.io.Serializable;

public class QueryRemindBean implements Serializable {

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

		public ActivityReminding activityReminding;

		public ActivityReminding getActivityReminding() {
			return activityReminding;
		}

		public void setActivityReminding(ActivityReminding activityReminding) {
			this.activityReminding = activityReminding;
		}

		public class ActivityReminding implements Serializable {

			public Long createTime;
			public int id;
			public int userType;
			public int activityPoolId;
			public int userId;
			public int status;

			public Long getCreateTime() {
				return createTime;
			}

			public void setCreateTime(Long createTime) {
				this.createTime = createTime;
			}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public int getUserType() {
				return userType;
			}

			public void setUserType(int userType) {
				this.userType = userType;
			}

			public int getActivityPoolId() {
				return activityPoolId;
			}

			public void setActivityPoolId(int activityPoolId) {
				this.activityPoolId = activityPoolId;
			}

			public int getUserId() {
				return userId;
			}

			public void setUserId(int userId) {
				this.userId = userId;
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
