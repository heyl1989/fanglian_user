package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class AccountDetail implements Serializable {

	private String msg;
	private int status;
	private List<Result> result;

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

		private String date;
		private String amount;
		private int inOutType;
		private int payType;
		private int actType;
		private String actName;
		private int tradeType;

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public int getInOutType() {
			return inOutType;
		}

		public void setInOutType(int inOutType) {
			this.inOutType = inOutType;
		}

		public int getPayType() {
			return payType;
		}

		public void setPayType(int payType) {
			this.payType = payType;
		}

		public int getActType() {
			return actType;
		}

		public void setActType(int actType) {
			this.actType = actType;
		}

		public String getActName() {
			return actName;
		}

		public void setActName(String actName) {
			this.actName = actName;
		}

		public int getTradeType() {
			return tradeType;
		}

		public void setTradeType(int tradeType) {
			this.tradeType = tradeType;
		}

	}
}
