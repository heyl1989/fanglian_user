package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class GoodBrokeBean implements Serializable {

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
		private List<BrokeList> list;

		public List<BrokeList> getList() {
			return list;
		}

		public void setList(List<BrokeList> list) {
			this.list = list;
		}

		public class BrokeList implements Serializable {

			private String phone;
			private String name;
			private String url;
			private int visitNum;
			private String id;
			private int isOrder;

			
			public int getIsOrder() {
				return isOrder;
			}

			public void setIsOrder(int isOrder) {
				this.isOrder = isOrder;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getPhone() {
				return phone;
			}

			public void setPhone(String phone) {
				this.phone = phone;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public int getVisitNum() {
				return visitNum;
			}

			public void setVisitNum(int visitNum) {
				this.visitNum = visitNum;
			}

		}
	}
}
