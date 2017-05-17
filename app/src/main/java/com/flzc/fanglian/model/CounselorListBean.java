package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class CounselorListBean implements Serializable {

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
		private List<CounselorList> list;
		private String count;
		
		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public List<CounselorList> getList() {
			return list;
		}

		public void setList(List<CounselorList> list) {
			this.list = list;
		}

		public class CounselorList implements Serializable {

			private String position;
			private int id;
			private Long createTime;
			private String phone;
			private String userCode;
			private Long updateTime;
			private String linkMan;
			private String idCode;
			private int state;
			private int status;
			private String userName;
			private int workState;
			private String password;
			private String headUrl;

			
			public int getStatus() {
				return status;
			}

			public void setStatus(int status) {
				this.status = status;
			}

			public String getHeadUrl() {
				return headUrl;
			}

			public void setHeadUrl(String headUrl) {
				this.headUrl = headUrl;
			}

			public String getPosition() {
				return position;
			}

			public void setPosition(String position) {
				this.position = position;
			}

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

			public String getPhone() {
				return phone;
			}

			public void setPhone(String phone) {
				this.phone = phone;
			}

			public String getUserCode() {
				return userCode;
			}

			public void setUserCode(String userCode) {
				this.userCode = userCode;
			}

			public Long getUpdateTime() {
				return updateTime;
			}

			public void setUpdateTime(Long updateTime) {
				this.updateTime = updateTime;
			}

			public String getLinkMan() {
				return linkMan;
			}

			public void setLinkMan(String linkMan) {
				this.linkMan = linkMan;
			}

			public String getIdCode() {
				return idCode;
			}

			public void setIdCode(String idCode) {
				this.idCode = idCode;
			}

			public int getState() {
				return state;
			}

			public void setState(int state) {
				this.state = state;
			}

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}

			public int getWorkState() {
				return workState;
			}

			public void setWorkState(int workState) {
				this.workState = workState;
			}

			public String getPassword() {
				return password;
			}

			public void setPassword(String password) {
				this.password = password;
			}

		}

	}

}
