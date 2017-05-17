package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class BankBean implements Serializable {

	public String msg;
	public int status;
	public List<Result> result;
	
	

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



	public List<Result> getResult() {
		return result;
	}



	public void setResult(List<Result> result) {
		this.result = result;
	}



	public class Result implements Serializable {

		public Long createTime;
		public int cardType;
		public String bankName;
		public int id;
		public String cardImageUrl;
		public int parentId;
		
		public Long getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Long createTime) {
			this.createTime = createTime;
		}
		public int getCardType() {
			return cardType;
		}
		public void setCardType(int cardType) {
			this.cardType = cardType;
		}
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getCardImageUrl() {
			return cardImageUrl;
		}
		public void setCardImageUrl(String cardImageUrl) {
			this.cardImageUrl = cardImageUrl;
		}
		public int getParentId() {
			return parentId;
		}
		public void setParentId(int parentId) {
			this.parentId = parentId;
		}
		

	}

}
