package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class AgentRecommendUserListBean implements Serializable {

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
		private List<UserList> list;

		public List<UserList> getList() {
			return list;
		}

		public void setList(List<UserList> list) {
			this.list = list;
		}

		public class UserList implements Serializable {

			private String phone;
			private String nickName;
			private String headUrl;

			public String getPhone() {
				return phone;
			}

			public void setPhone(String phone) {
				this.phone = phone;
			}

			public String getNickName() {
				return nickName;
			}

			public void setNickName(String nickName) {
				this.nickName = nickName;
			}

			public String getHeadUrl() {
				return headUrl;
			}

			public void setHeadUrl(String headUrl) {
				this.headUrl = headUrl;
			}

		}
	}
}
