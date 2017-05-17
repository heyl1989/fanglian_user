package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class QueryHouseSourceBean implements Serializable {

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

		private String buildingName;
		private String address;
		private String size;
		private String buildingNum;
		private String price;
		private String buildingUnit;
		private String houseTypeName;
		private String floor;
		private String houseKind;
		private String houseStatus;
		private String payStatus;
		private List<Imgs> imgs;

		
		public String getHouseStatus() {
			return houseStatus;
		}

		public void setHouseStatus(String houseStatus) {
			this.houseStatus = houseStatus;
		}

		public String getBuildingName() {
			return buildingName;
		}

		public void setBuildingName(String buildingName) {
			this.buildingName = buildingName;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}

		public String getBuildingNum() {
			return buildingNum;
		}

		public void setBuildingNum(String buildingNum) {
			this.buildingNum = buildingNum;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getBuildingUnit() {
			return buildingUnit;
		}

		public void setBuildingUnit(String buildingUnit) {
			this.buildingUnit = buildingUnit;
		}

		public String getHouseTypeName() {
			return houseTypeName;
		}

		public void setHouseTypeName(String houseTypeName) {
			this.houseTypeName = houseTypeName;
		}

		public String getFloor() {
			return floor;
		}

		public void setFloor(String floor) {
			this.floor = floor;
		}

		public String getHouseKind() {
			return houseKind;
		}

		public void setHouseKind(String houseKind) {
			this.houseKind = houseKind;
		}

		public String getPayStatus() {
			return payStatus;
		}

		public void setPayStatus(String payStatus) {
			this.payStatus = payStatus;
		}

		public List<Imgs> getImgs() {
			return imgs;
		}

		public void setImgs(List<Imgs> imgs) {
			this.imgs = imgs;
		}

		public CurrActUser getCurrActUser() {
			return currActUser;
		}

		public void setCurrActUser(CurrActUser currActUser) {
			this.currActUser = currActUser;
		}

		public class Imgs implements Serializable {

			private String imgUrl;
			private Long createTime;
			private String name;
			private Long updateTime;
			private int id;
			private int houseTypeId;
			private int isCover;

			public String getImgUrl() {
				return imgUrl;
			}

			public void setImgUrl(String imgUrl) {
				this.imgUrl = imgUrl;
			}

			public Long getCreateTime() {
				return createTime;
			}

			public void setCreateTime(Long createTime) {
				this.createTime = createTime;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public Long getUpdateTime() {
				return updateTime;
			}

			public void setUpdateTime(Long updateTime) {
				this.updateTime = updateTime;
			}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public int getHouseTypeId() {
				return houseTypeId;
			}

			public void setHouseTypeId(int houseTypeId) {
				this.houseTypeId = houseTypeId;
			}

			public int getIsCover() {
				return isCover;
			}

			public void setIsCover(int isCover) {
				this.isCover = isCover;
			}

		}

		private CurrActUser currActUser;

		public class CurrActUser implements Serializable {

			private String userPrice;
			private String userName;
			private String userHeadUrl;

			public String getUserPrice() {
				return userPrice;
			}

			public void setUserPrice(String userPrice) {
				this.userPrice = userPrice;
			}

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}

			public String getUserHeadUrl() {
				return userHeadUrl;
			}

			public void setUserHeadUrl(String userHeadUrl) {
				this.userHeadUrl = userHeadUrl;
			}

		}
	}
}
