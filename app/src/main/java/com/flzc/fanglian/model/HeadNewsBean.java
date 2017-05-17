package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class HeadNewsBean implements Serializable {

	private int status;
	private String msg;
	private Result result;

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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public class Result implements Serializable {

		private List<HeadNewsList> list;

		public List<HeadNewsList> getList() {
			return list;
		}

		public void setList(List<HeadNewsList> list) {
			this.list = list;
		}

		public class HeadNewsList implements Serializable {

			private int id;
			private Long createTime;
			private int activityPoolId;//活动汇总ID
			private String memo;//描述
			private int status;//状态
			private String userName;//用户昵称
			private String activityName;//活动名称

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

			public int getActivityPoolId() {
				return activityPoolId;
			}

			public void setActivityPoolId(int activityPoolId) {
				this.activityPoolId = activityPoolId;
			}

			public String getMemo() {
				return memo;
			}

			public void setMemo(String memo) {
				this.memo = memo;
			}

			public int getStatus() {
				return status;
			}

			public void setStatus(int status) {
				this.status = status;
			}

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}

			public String getActivityName() {
				return activityName;
			}

			public void setActivityName(String activityName) {
				this.activityName = activityName;
			}

		}

	}

}