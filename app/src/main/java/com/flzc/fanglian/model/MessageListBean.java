package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class MessageListBean implements Serializable {

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

		private int messageType;
		private Long createTime;
		private int messageCode;
		private int messageRead;
		private int id;
		private String title;
		private Long sendCreateTime;
		private String content;
		private String userCode;
		private int status;

		public int getMessageType() {
			return messageType;
		}

		public void setMessageType(int messageType) {
			this.messageType = messageType;
		}

		public Long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Long createTime) {
			this.createTime = createTime;
		}

		public int getMessageCode() {
			return messageCode;
		}

		public void setMessageCode(int messageCode) {
			this.messageCode = messageCode;
		}

		public int getMessageRead() {
			return messageRead;
		}

		public void setMessageRead(int messageRead) {
			this.messageRead = messageRead;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Long getSendCreateTime() {
			return sendCreateTime;
		}

		public void setSendCreateTime(Long sendCreateTime) {
			this.sendCreateTime = sendCreateTime;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getUserCode() {
			return userCode;
		}

		public void setUserCode(String userCode) {
			this.userCode = userCode;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

	}
}