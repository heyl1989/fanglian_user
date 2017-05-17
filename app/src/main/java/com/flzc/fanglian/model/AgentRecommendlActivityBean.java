package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class AgentRecommendlActivityBean implements Serializable {

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

		private Salesway salesway;
		private Building building;
		private Share share;
		private int pvCount;
		private List<SaleswayList> saleswayList;

		
		public List<SaleswayList> getSaleswayList() {
			return saleswayList;
		}

		public void setSaleswayList(List<SaleswayList> saleswayList) {
			this.saleswayList = saleswayList;
		}

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

		public Salesway getSalesway() {
			return salesway;
		}

		public void setSalesway(Salesway salesway) {
			this.salesway = salesway;
		}

		public Building getBuilding() {
			return building;
		}

		public void setBuilding(Building building) {
			this.building = building;
		}

		public class SaleswayList implements Serializable {

			private String area;
			private String houseTypeImg;
			private String houseName;
			private String amount;
			private String houseType;
			private String ticketAmount;
			private int houseSourceId;
			private String size;
			private String buildingNum;
			private String price;
			private String maxHouseTypeImg;
			private String floor;
			private String houseKind;
			
			
			public String getHouseType() {
				return houseType;
			}
			public void setHouseType(String houseType) {
				this.houseType = houseType;
			}
			public String getArea() {
				return area;
			}
			public void setArea(String area) {
				this.area = area;
			}
			public String getHouseTypeImg() {
				return houseTypeImg;
			}
			public void setHouseTypeImg(String houseTypeImg) {
				this.houseTypeImg = houseTypeImg;
			}
			public String getHouseName() {
				return houseName;
			}
			public void setHouseName(String houseName) {
				this.houseName = houseName;
			}
			public String getAmount() {
				return amount;
			}
			public void setAmount(String amount) {
				this.amount = amount;
			}
			public String getTicketAmount() {
				return ticketAmount;
			}
			public void setTicketAmount(String ticketAmount) {
				this.ticketAmount = ticketAmount;
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
			public String getMaxHouseTypeImg() {
				return maxHouseTypeImg;
			}
			public void setMaxHouseTypeImg(String maxHouseTypeImg) {
				this.maxHouseTypeImg = maxHouseTypeImg;
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

		public class Salesway implements Serializable {

			private String houseTypeImg;
			private int activityId;
			private String houseName;
			private int houseSourceId;
			private String size;
			private String topPrice;
			private String price;
			private String activityName;
			private String flvolume;
			private int houseSaleCount;
			private long startTime;
			private long endTime;
			private String floor;
			private int joinCount;

			
			public String getTopPrice() {
				return topPrice;
			}

			public void setTopPrice(String topPrice) {
				this.topPrice = topPrice;
			}

			public int getJoinCount() {
				return joinCount;
			}

			public void setJoinCount(int joinCount) {
				this.joinCount = joinCount;
			}

			public String getFlvolume() {
				return flvolume;
			}

			public void setFlvolume(String flvolume) {
				this.flvolume = flvolume;
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

			public String getPrice() {
				return price;
			}

			public void setPrice(String price) {
				this.price = price;
			}

			public String getActivityName() {
				return activityName;
			}

			public void setActivityName(String activityName) {
				this.activityName = activityName;
			}

			public int getHouseSaleCount() {
				return houseSaleCount;
			}

			public void setHouseSaleCount(int houseSaleCount) {
				this.houseSaleCount = houseSaleCount;
			}

			public long getStartTime() {
				return startTime;
			}

			public void setStartTime(long startTime) {
				this.startTime = startTime;
			}

			public long getEndTime() {
				return endTime;
			}

			public void setEndTime(long endTime) {
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

			private String soldPrice;
			private String name;
			private String priceType;
			private String mainImage;
			private int buildingId;
			private int actStatus;
			private String saleTel;
			private List<Tags> tags;
			private String address;
			private String latitude;
			private String longitude;

			
			public String getAddress() {
				return address;
			}

			public void setAddress(String address) {
				this.address = address;
			}

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

			public String getSaleTel() {
				return saleTel;
			}

			public void setSaleTel(String saleTel) {
				this.saleTel = saleTel;
			}

			public int getActStatus() {
				return actStatus;
			}

			public void setActStatus(int actStatus) {
				this.actStatus = actStatus;
			}

			public String getMainImage() {
				return mainImage;
			}

			public void setMainImage(String mainImage) {
				this.mainImage = mainImage;
			}

			public String getSoldPrice() {
				return soldPrice;
			}

			public void setSoldPrice(String soldPrice) {
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
