package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class QuestionListModel implements Serializable {

	private String msg;
	private int status;
	private List<ItemQuestionModel> result;

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

	public List<ItemQuestionModel> getResult() {
		return result;
	}

	public void setResult(List<ItemQuestionModel> result) {
		this.result = result;
	}

}
