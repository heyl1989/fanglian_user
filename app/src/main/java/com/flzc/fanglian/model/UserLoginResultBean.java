package com.flzc.fanglian.model;

public class UserLoginResultBean {

	private String myInviteCode;
	private String nickName;
	private String tokenId;
	private String headUrl;
	private String phone;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getMyInviteCode() {
		return myInviteCode;
	}
	public void setMyInviteCode(String myInviteCode) {
		this.myInviteCode = myInviteCode;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	@Override
	public String toString() {
		return "UserLoginResult [myInviteCode=" + myInviteCode + ", nickName="
				+ nickName + ", tokenId=" + tokenId + "]";
	}
}
