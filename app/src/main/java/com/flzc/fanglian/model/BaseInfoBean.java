package com.flzc.fanglian.model;

public class BaseInfoBean {

	private String msg;
	private int status;
	
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
	
	@Override
	public String toString() {
		return "BaseInfo [msg=" + msg + ", status=" + status + "]";
	}
	
}
