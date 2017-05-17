package com.flzc.fanglian.model;

import java.io.Serializable;


public class AccountWithDraw implements Serializable {

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

		private String amount;
		private String tokenId;
		private String bindCardId;
		private String remark;
		private String userType;
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getTokenId() {
			return tokenId;
		}
		public void setTokenId(String tokenId) {
			this.tokenId = tokenId;
		}
		public String getBindCardId() {
			return bindCardId;
		}
		public void setBindCardId(String bindCardId) {
			this.bindCardId = bindCardId;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getUserType() {
			return userType;
		}
		public void setUserType(String userType) {
			this.userType = userType;
		}
		
	}
}
