package com.flzc.fanglian.model;

public class CreateOrderBean {

	private String msg;
	private int status;
	private CreateOrderResult result;
	
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


	public CreateOrderResult getResult() {
		return result;
	}


	public void setResult(CreateOrderResult result) {
		this.result = result;
	}


	public class CreateOrderResult{
		private String orderFlowId;

		public String getOrderFlowId() {
			return orderFlowId;
		}

		public void setOrderFlowId(String orderFlowId) {
			this.orderFlowId = orderFlowId;
		}
	}
}
