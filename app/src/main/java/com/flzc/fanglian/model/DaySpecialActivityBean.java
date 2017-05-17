package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class DaySpecialActivityBean implements Serializable {

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

		private DaySpecial daySpecial;
		private Building building;
		private Share share;
		private int pvCount;

		
		public int getPvCount() {
			return pvCount;
		}

		public void setPvCount(int pvCount) {
			this.pvCount = pvCount;
		}

		public Share getShare() {
			return share;
		}

		public void setShare(Share share) {
			this.share = share;
		}

		public DaySpecial getDaySpecial() {
			return daySpecial;
		}

		public void setDaySpecial(DaySpecial daySpecial) {
			this.daySpecial = daySpecial;
		}

		public Building getBuilding() {
			return building;
		}

		public void setBuilding(Building building) {
			this.building = building;
		}

		public class Share implements Serializable {
			private String shareUrl;
			private int status;

			public String getShareUrl() {
				return shareUrl;
			}

			public void setShareUrl(String shareUrl) {
				this.shareUrl = shareUrl;
			}

			public int getStatus() {
				return status;
			}

			public void setStatus(int status) {
				this.status = status;
			}

		}

		public class DaySpecial implements Serializable {

			private String houseTypeImg;
			private int activityId;
			private String houseName;
			private int actStatus;
			private String activityPrice;
			private int houseSourceId;
			private String size;
			private String activityName;
			private int count;
			private String deposit;
			private long endTime;
			private long startTime;
			private String floor;
			private int userStatus;
			private int houseStatus;// 0 === 正常 1==被秒
			private String balance;// 账户的余额
			private int daySpecialStatus;
			private String headUrl;
			private String nickName;
			private String houseKind;
			private String houseType;
			private String area;
			private String referPrice;
			private String price;
			private String buildingNum;
			private String city;
			
			
			public String getCity() {
				return city;
			}

			public void setCity(String city) {
				this.city = city;
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

			public String getReferPrice() {
				return referPrice;
			}

			public void setReferPrice(String referPrice) {
				this.referPrice = referPrice;
			}

			public String getArea() {
				return area;
			}

			public void setArea(String area) {
				this.area = area;
			}

			public String getHouseType() {
				return houseType;
			}

			public void setHouseType(String houseType) {
				this.houseType = houseType;
			}

			public String getHouseKind() {
				return houseKind;
			}

			public void setHouseKind(String houseKind) {
				this.houseKind = houseKind;
			}

			public int getDaySpecialStatus() {
				return daySpecialStatus;
			}

			public void setDaySpecialStatus(int daySpecialStatus) {
				this.daySpecialStatus = daySpecialStatus;
			}

			public String getHeadUrl() {
				return headUrl;
			}

			public void setHeadUrl(String headUrl) {
				this.headUrl = headUrl;
			}

			public String getNickName() {
				return nickName;
			}

			public void setNickName(String nickName) {
				this.nickName = nickName;
			}

			public void setEndTime(long endTime) {
				this.endTime = endTime;
			}

			public String getBalance() {
				return balance;
			}

			public void setBalance(String balance) {
				this.balance = balance;
			}

			public int getHouseStatus() {
				return houseStatus;
			}

			public void setHouseStatus(int houseStatus) {
				this.houseStatus = houseStatus;
			}

			public int getUserStatus() {
				return userStatus;
			}

			public void setUserStatus(int userStatus) {
				this.userStatus = userStatus;
			}

			public long getStartTime() {
				return startTime;
			}

			public void setStartTime(long startTime) {
				this.startTime = startTime;
			}

			public String getHouseTypeImg() {
				return houseTypeImg;
			}

			public void setHouseTypeImg(String houseTypeImg) {
				this.houseTypeImg = houseTypeImg;
			}

			public int getActivityId() {
				return activityId;
			}

			public void setActivityId(int activityId) {
				this.activityId = activityId;
			}

			public String getHouseName() {
				return houseName;
			}

			public void setHouseName(String houseName) {
				this.houseName = houseName;
			}

			public int getActStatus() {
				return actStatus;
			}

			public void setActStatus(int actStatus) {
				this.actStatus = actStatus;
			}

			public String getActivityPrice() {
				return activityPrice;
			}

			public void setActivityPrice(String activityPrice) {
				this.activityPrice = activityPrice;
			}

			public int getHouseSourceId() {
				return houseSourceId;
			}

			public void setHouseSourceId(int houseSourceId) {
				this.houseSourceId = houseSourceId;
			}

			public String getSize() {
				return size;
			}

			public void setSize(String size) {
				this.size = size;
			}

			public String getActivityName() {
				return activityName;
			}

			public void setActivityName(String activityName) {
				this.activityName = activityName;
			}

			public int getCount() {
				return count;
			}

			public void setCount(int count) {
				this.count = count;
			}

			public String getDeposit() {
				return deposit;
			}

			public void setDeposit(String deposit) {
				this.deposit = deposit;
			}

			public Long getEndTime() {
				return endTime;
			}

			public void setEndTime(Long endTime) {
				this.endTime = endTime;
			}

			public String getFloor() {
				return floor;
			}

			public void setFloor(String floor) {
				this.floor = floor;
			}

		}

		public class Building implements Serializable {

			private int soldPrice;
			private String name;
			private String priceType;
			private String master;
			private int buildingId;
			private String remaind;
			private String saleTel;
			private String remaindId;
			private List<Tags> tags;
			private String latitude;
			private String longitude;
			private String address;

			
			public String getLatitude() {
				return latitude;
			}

			public void setLatitude(String latitude) {
				this.latitude = latitude;
			}

			public String getLongitude() {
				return longitude;
			}

			public void setLongitude(String longitude) {
				this.longitude = longitude;
			}

			public String getAddress() {
				return address;
			}

			public void setAddress(String address) {
				this.address = address;
			}

			public String getRemaind() {
				return remaind;
			}

			public void setRemaind(String remaind) {
				this.remaind = remaind;
			}

			public String getSaleTel() {
				return saleTel;
			}

			public void setSaleTel(String saleTel) {
				this.saleTel = saleTel;
			}

			public String getRemaindId() {
				return remaindId;
			}

			public void setRemaindId(String remaindId) {
				this.remaindId = remaindId;
			}

			public String getMaster() {
				return master;
			}

			public void setMaster(String master) {
				this.master = master;
			}

			public int getSoldPrice() {
				return soldPrice;
			}

			public void setSoldPrice(int soldPrice) {
				this.soldPrice = soldPrice;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getPriceType() {
				return priceType;
			}

			public void setPriceType(String priceType) {
				this.priceType = priceType;
			}

			public int getBuildingId() {
				return buildingId;
			}

			public void setBuildingId(int buildingId) {
				this.buildingId = buildingId;
			}

			public List<Tags> getTags() {
				return tags;
			}

			public void setTags(List<Tags> tags) {
				this.tags = tags;
			}

			public class Tags implements Serializable {

				private int code;
				private Long createTime;
				private int parentCode;
				private String name;
				private int orderNum;
				private int id;
				private int onlineFlag;
				private int brandFlag;
				private int status;

				public int getCode() {
					return code;
				}

				public void setCode(int code) {
					this.code = code;
				}

				public Long getCreateTime() {
					return createTime;
				}

				public void setCreateTime(Long createTime) {
					this.createTime = createTime;
				}

				public int getParentCode() {
					return parentCode;
				}

				public void setParentCode(int parentCode) {
					this.parentCode = parentCode;
				}

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public int getOrderNum() {
					return orderNum;
				}

				public void setOrderNum(int orderNum) {
					this.orderNum = orderNum;
				}

				public int getId() {
					return id;
				}

				public void setId(int id) {
					this.id = id;
				}

				public int getOnlineFlag() {
					return onlineFlag;
				}

				public void setOnlineFlag(int onlineFlag) {
					this.onlineFlag = onlineFlag;
				}

				public int getBrandFlag() {
					return brandFlag;
				}

				public void setBrandFlag(int brandFlag) {
					this.brandFlag = brandFlag;
				}

				public int getStatus() {
					return status;
				}

				public void setStatus(int status) {
					this.status = status;
				}

			}

		}

	}

}
