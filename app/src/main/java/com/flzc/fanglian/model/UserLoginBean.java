package com.flzc.fanglian.model;

public class UserLoginBean extends BaseInfoBean{

	private UserLoginResultBean result;

	public UserLoginResultBean getResult() {
		return result;
	}

	public void setResult(UserLoginResultBean result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "UserLogin [result=" + result + "]";
	}
}
