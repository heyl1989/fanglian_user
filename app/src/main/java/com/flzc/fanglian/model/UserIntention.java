package com.flzc.fanglian.model;

import java.io.Serializable;

public class UserIntention implements Serializable {

	private String buildingId;
	private Integer buyHouseAim;
	private Integer houseKind;
	private Integer houseType;
	private Integer houseSize;
	private Integer houseFloor;
	private Integer payWay;

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public Integer getBuyHouseAim() {
		return buyHouseAim;
	}

	public void setBuyHouseAim(Integer buyHouseAim) {
		this.buyHouseAim = buyHouseAim;
	}

	public Integer getHouseKind() {
		return houseKind;
	}

	public void setHouseKind(Integer houseKind) {
		this.houseKind = houseKind;
	}

	public Integer getHouseType() {
		return houseType;
	}

	public void setHouseType(Integer houseType) {
		this.houseType = houseType;
	}

	public Integer getHouseSize() {
		return houseSize;
	}

	public void setHouseSize(Integer houseSize) {
		this.houseSize = houseSize;
	}

	public Integer getHouseFloor() {
		return houseFloor;
	}

	public void setHouseFloor(Integer houseFloor) {
		this.houseFloor = houseFloor;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	@Override
	public String toString() {
		return "UserIntention [buildingId=" + buildingId + ", buyHouseAim="
				+ buyHouseAim + ", houseKind=" + houseKind + ", houseType="
				+ houseType + ", houseSize=" + houseSize + ", hosueFloor="
				+ houseFloor + ", payWay=" + payWay + "]";
	}

}
