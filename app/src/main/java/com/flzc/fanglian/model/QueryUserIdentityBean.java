package com.flzc.fanglian.model;

import java.io.Serializable;

public class QueryUserIdentityBean implements Serializable {

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

		private UserIdentity userIdentity;

		public UserIdentity getUserIdentity() {
			return userIdentity;
		}

		public void setUserIdentity(UserIdentity userIdentity) {
			this.userIdentity = userIdentity;
		}

		public class UserIdentity implements Serializable {

			private String realName;
			private Long createTime;
			private String phone;
			private int id;
			private int activityPoolId;
			private int userId;

			public String getRealName() {
				return realName;
			}

			public void setRealName(String realName) {
				this.realName = realName;
			}

			public Long getCreateTime() {
				return createTime;
			}

			public void setCreateTime(Long createTime) {
				this.createTime = createTime;
			}

			public String getPhone() {
				return phone;
			}

			public void setPhone(String phone) {
				this.phone = phone;
			}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
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
		}
	}
}
