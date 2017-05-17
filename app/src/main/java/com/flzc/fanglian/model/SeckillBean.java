package com.flzc.fanglian.model;

import java.io.Serializable;

public class SeckillBean implements Serializable {

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

		private String houseStatus;

		public String getHouseStatus() {
			return houseStatus;
		}

		public void setHouseStatus(String houseStatus) {
			this.houseStatus = houseStatus;
		}

	}

}
