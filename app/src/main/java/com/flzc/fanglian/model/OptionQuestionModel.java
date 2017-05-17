package com.flzc.fanglian.model;

import java.io.Serializable;

public class OptionQuestionModel implements Serializable {

	private String name;
	private boolean check;
	private int code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
