package com.flzc.fanglian.model;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class UserBinbCardBean implements Serializable {

	public String msg;
	public int status;
	public List<BindCardList> result;

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

	public List<BindCardList> getResult() {
		return result;
	}

	public void setResult(List<BindCardList> result) {
		this.result = result;
	}

	public class BindCardList implements Serializable {

		public String bankCode;
		public String realName;
		public int isDefault;
		public String phoneNumber;
		public Long createTime;
		public String idCard;
		public String bankCardType;
		public String bankName;
		public String cardImageUrl;
		public int id;
		public int userId;
		public int status;
		
		
		
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		public String getCardImageUrl() {
			return cardImageUrl;
		}
		public void setCardImageUrl(String cardImageUrl) {
			this.cardImageUrl = cardImageUrl;
		}
		public String getBankCode() {
			return bankCode;
		}
		public void setBankCode(String bankCode) {
			this.bankCode = bankCode;
		}
		public String getRealName() {
			return realName;
		}
		public void setRealName(String realName) {
			this.realName = realName;
		}
		public int getIsDefault() {
			return isDefault;
		}
		public void setIsDefault(int isDefault) {
			this.isDefault = isDefault;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public Long getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Long createTime) {
			this.createTime = createTime;
		}
		public String getIdCard() {
			return idCard;
		}
		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}
		public String getBankCardType() {
			return bankCardType;
		}
		public void setBankCardType(String bankCardType) {
			this.bankCardType = bankCardType;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		
		

	}

}
