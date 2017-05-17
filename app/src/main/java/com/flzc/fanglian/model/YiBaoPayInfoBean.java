package com.flzc.fanglian.model;

import java.io.Serializable;

public class YiBaoPayInfoBean implements Serializable{
	private String msg;
	private int status;
	private Result result;
	
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
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
	
	public class Result implements Serializable {
		private String payUrl;

		public String getPayUrl() {
			return payUrl;
		}

		public void setPayUrl(String payUrl) {
			this.payUrl = payUrl;
		}
		
	}
}
