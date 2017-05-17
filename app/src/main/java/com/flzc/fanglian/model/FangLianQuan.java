package com.flzc.fanglian.model;

public class FangLianQuan {

	private String gainxbao;
	private String numdata;
	private String numunit;
	private String type;
	private String state;
	private String company;
	private String deadline;

	public String getGainxbao() {
		return gainxbao;
	}

	public void setGainxbao(String gainxbao) {
		this.gainxbao = gainxbao;
	}

	public String getNumdata() {
		return numdata;
	}

	public void setNumdata(String numdata) {
		this.numdata = numdata;
	}

	public String getNumunit() {
		return numunit;
	}

	public void setNumunit(String numunit) {
		this.numunit = numunit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		return "FangLianQuan [gainxbao=" + gainxbao + ", numdata=" + numdata
				+ ", numunit=" + numunit + ", type=" + type + ", state="
				+ state + ", company=" + company + ", deadline=" + deadline
				+ "]";
	}

}
