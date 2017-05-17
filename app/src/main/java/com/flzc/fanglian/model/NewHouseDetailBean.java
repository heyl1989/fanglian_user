package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

import com.flzc.fanglian.model.NewHouseListBean.Result.Tags;

public class NewHouseDetailBean implements Serializable {

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

		private String area;
		private String licence;
		private String address;
		private int parkNum;
		private String buildingSize;
		private int saleCount;
		private int houseTypeCount;
		private String possession;// 入驻时间
		private String openTime;// 开盘时间
		private int buildingImgCount;
		private String lon;
		private String remark;
		private String master;
		private String greenRate;
		private String fieldRate;
		private String volRate;
		private String price;
		private String soldPrice;
		private String builder;
		private String name;
		private int id;
		private String lat;
		private String saleAddress;
		private String saleTel;
		private List<BuildActivity> activity;
		private List<Jztags> jztags;
		private List<Wytags> wytags;
		private List<HouseTypes> houseTypes;
		private List<Tags> tags;//楼盘标签列表
		private List<Zxtags> zxtags;//楼盘标签列表
		private Share share;

		
		public int getHouseTypeCount() {
			return houseTypeCount;
		}

		public void setHouseTypeCount(int houseTypeCount) {
			this.houseTypeCount = houseTypeCount;
		}

		public List<Zxtags> getZxtags() {
			return zxtags;
		}

		public void setZxtags(List<Zxtags> zxtags) {
			this.zxtags = zxtags;
		}

		public List<Tags> getTags() {
			return tags;
		}

		public void setTags(List<Tags> tags) {
			this.tags = tags;
		}

		public String getSaleTel() {
			return saleTel;
		}

		public void setSaleTel(String saleTel) {
			this.saleTel = saleTel;
		}

		public Share getShare() {
			return share;
		}

		public void setShare(Share share) {
			this.share = share;
		}

		public String getOpenTime() {
			return openTime;
		}

		public void setOpenTime(String openTime) {
			this.openTime = openTime;
		}

		public String getSaleAddress() {
			return saleAddress;
		}

		public void setSaleAddress(String saleAddress) {
			this.saleAddress = saleAddress;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getLicence() {
			return licence;
		}

		public void setLicence(String licence) {
			this.licence = licence;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public int getParkNum() {
			return parkNum;
		}

		public void setParkNum(int parkNum) {
			this.parkNum = parkNum;
		}

		public String getBuildingSize() {
			return buildingSize;
		}

		public void setBuildingSize(String buildingSize) {
			this.buildingSize = buildingSize;
		}

		public int getSaleCount() {
			return saleCount;
		}

		public void setSaleCount(int saleCount) {
			this.saleCount = saleCount;
		}

		public String getPossession() {
			return possession;
		}

		public void setPossession(String possession) {
			this.possession = possession;
		}

		public int getBuildingImgCount() {
			return buildingImgCount;
		}

		public void setBuildingImgCount(int buildingImgCount) {
			this.buildingImgCount = buildingImgCount;
		}

		public String getLon() {
			return lon;
		}

		public void setLon(String lon) {
			this.lon = lon;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getMaster() {
			return master;
		}

		public void setMaster(String master) {
			this.master = master;
		}

		public String getGreenRate() {
			return greenRate;
		}

		public void setGreenRate(String greenRate) {
			this.greenRate = greenRate;
		}

		public String getFieldRate() {
			return fieldRate;
		}

		public void setFieldRate(String fieldRate) {
			this.fieldRate = fieldRate;
		}

		public String getVolRate() {
			return volRate;
		}

		public void setVolRate(String volRate) {
			this.volRate = volRate;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getSoldPrice() {
			return soldPrice;
		}

		public void setSoldPrice(String soldPrice) {
			this.soldPrice = soldPrice;
		}

		public String getBuilder() {
			return builder;
		}

		public void setBuilder(String builder) {
			this.builder = builder;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getLat() {
			return lat;
		}

		public void setLat(String lat) {
			this.lat = lat;
		}

		public List<BuildActivity> getActivity() {
			return activity;
		}

		public void setActivity(List<BuildActivity> activity) {
			this.activity = activity;
		}

		public List<Jztags> getJztags() {
			return jztags;
		}

		public void setJztags(List<Jztags> jztags) {
			this.jztags = jztags;
		}

		public List<Wytags> getWytags() {
			return wytags;
		}

		public void setWytags(List<Wytags> wytags) {
			this.wytags = wytags;
		}

		public List<HouseTypes> getHouseTypes() {
			return houseTypes;
		}

		public void setHouseTypes(List<HouseTypes> houseTypes) {
			this.houseTypes = houseTypes;
		}
		
		public class Zxtags implements Serializable{
			private String brandFlag;
			private String code;
			private String createTime;
			private String id;
			private String name;
			private String onlineFlag;
			private String orderNum;
			private String parentCode;
			private String status;
			private String type;
			private String version;
			public String getBrandFlag() {
				return brandFlag;
			}
			public void setBrandFlag(String brandFlag) {
				this.brandFlag = brandFlag;
			}
			public String getCode() {
				return code;
			}
			public void setCode(String code) {
				this.code = code;
			}
			public String getCreateTime() {
				return createTime;
			}
			public void setCreateTime(String createTime) {
				this.createTime = createTime;
			}
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getOnlineFlag() {
				return onlineFlag;
			}
			public void setOnlineFlag(String onlineFlag) {
				this.onlineFlag = onlineFlag;
			}
			public String getOrderNum() {
				return orderNum;
			}
			public void setOrderNum(String orderNum) {
				this.orderNum = orderNum;
			}
			public String getParentCode() {
				return parentCode;
			}
			public void setParentCode(String parentCode) {
				this.parentCode = parentCode;
			}
			public String getStatus() {
				return status;
			}
			public void setStatus(String status) {
				this.status = status;
			}
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			public String getVersion() {
				return version;
			}
			public void setVersion(String version) {
				this.version = version;
			}
			
			
		}
		
		public class Tags implements Serializable {

			private int id;
			private Long createTime;
			private int brandFlag;
			private int onlineFlag;
			private int status;
			private String name;
			private int orderNum;
			private int code;
			private int parentCode;
			
			public int getId() {
				return id;
			}
			public void setId(int id) {
				this.id = id;
			}
			public Long getCreateTime() {
				return createTime;
			}
			public void setCreateTime(Long createTime) {
				this.createTime = createTime;
			}
			public int getBrandFlag() {
				return brandFlag;
			}
			public void setBrandFlag(int brandFlag) {
				this.brandFlag = brandFlag;
			}
			public int getOnlineFlag() {
				return onlineFlag;
			}
			public void setOnlineFlag(int onlineFlag) {
				this.onlineFlag = onlineFlag;
			}
			public int getStatus() {
				return status;
			}
			public void setStatus(int status) {
				this.status = status;
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
			public int getCode() {
				return code;
			}
			public void setCode(int code) {
				this.code = code;
			}
			public int getParentCode() {
				return parentCode;
			}
			public void setParentCode(int parentCode) {
				this.parentCode = parentCode;
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

		public class BuildActivity implements Serializable{

			private String actEndTime;
			private String actStartTime;
			private String activityId;
			private String activityName;
			private int activityType;
			private String buildingId;
			private String createAuthor;
			private String createTime;
			private String id;
			private String preheatEndTime;
			private String preheatStartTime;
			private String releaseStatus;
			private String releaseTime;
			private String updateTime;
			private String remaind;
			private String remaindId;

			
			public String getRemaind() {
				return remaind;
			}

			public void setRemaind(String remaind) {
				this.remaind = remaind;
			}

			public String getRemaindId() {
				return remaindId;
			}

			public void setRemaindId(String remaindId) {
				this.remaindId = remaindId;
			}

			public String getActEndTime() {
				return actEndTime;
			}

			public void setActEndTime(String actEndTime) {
				this.actEndTime = actEndTime;
			}

			public String getActStartTime() {
				return actStartTime;
			}

			public void setActStartTime(String actStartTime) {
				this.actStartTime = actStartTime;
			}

			public String getActivityId() {
				return activityId;
			}

			public void setActivityId(String activityId) {
				this.activityId = activityId;
			}

			public String getActivityName() {
				return activityName;
			}

			public void setActivityName(String activityName) {
				this.activityName = activityName;
			}

			public int getActivityType() {
				return activityType;
			}

			public void setActivityType(int activityType) {
				this.activityType = activityType;
			}

			public String getBuildingId() {
				return buildingId;
			}

			public void setBuildingId(String buildingId) {
				this.buildingId = buildingId;
			}

			public String getCreateAuthor() {
				return createAuthor;
			}

			public void setCreateAuthor(String createAuthor) {
				this.createAuthor = createAuthor;
			}

			public String getCreateTime() {
				return createTime;
			}

			public void setCreateTime(String createTime) {
				this.createTime = createTime;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getPreheatEndTime() {
				return preheatEndTime;
			}

			public void setPreheatEndTime(String preheatEndTime) {
				this.preheatEndTime = preheatEndTime;
			}

			public String getPreheatStartTime() {
				return preheatStartTime;
			}

			public void setPreheatStartTime(String preheatStartTime) {
				this.preheatStartTime = preheatStartTime;
			}

			public String getReleaseStatus() {
				return releaseStatus;
			}

			public void setReleaseStatus(String releaseStatus) {
				this.releaseStatus = releaseStatus;
			}

			public String getReleaseTime() {
				return releaseTime;
			}

			public void setReleaseTime(String releaseTime) {
				this.releaseTime = releaseTime;
			}

			public String getUpdateTime() {
				return updateTime;
			}

			public void setUpdateTime(String updateTime) {
				this.updateTime = updateTime;
			}

		}

		public class Jztags implements Serializable {

			private int code;
			private Long createTime;
			private int parentCode;
			private String name;
			private int orderNum;
			private int id;
			private int type;
			private int onlineFlag;
			private int version;
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

			public int getType() {
				return type;
			}

			public void setType(int type) {
				this.type = type;
			}

			public int getOnlineFlag() {
				return onlineFlag;
			}

			public void setOnlineFlag(int onlineFlag) {
				this.onlineFlag = onlineFlag;
			}

			public int getVersion() {
				return version;
			}

			public void setVersion(int version) {
				this.version = version;
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

		public class Wytags implements Serializable {

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

		public class HouseTypes implements Serializable{

			private String bathroom;
			private String hall;
			private String house_name;
			private String img;
			private String kitchen;
			private String room;
			private String size;
			private String bigImg;

			
			public String getBigImg() {
				return bigImg;
			}

			public void setBigImg(String bigImg) {
				this.bigImg = bigImg;
			}

			public String getBathroom() {
				return bathroom;
			}

			public void setBathroom(String bathroom) {
				this.bathroom = bathroom;
			}

			public String getHall() {
				return hall;
			}

			public void setHall(String hall) {
				this.hall = hall;
			}

			public String getHouse_name() {
				return house_name;
			}

			public void setHouse_name(String house_name) {
				this.house_name = house_name;
			}

			public String getImg() {
				return img;
			}

			public void setImg(String img) {
				this.img = img;
			}

			public String getKitchen() {
				return kitchen;
			}

			public void setKitchen(String kitchen) {
				this.kitchen = kitchen;
			}

			public String getRoom() {
				return room;
			}

			public void setRoom(String room) {
				this.room = room;
			}

			public String getSize() {
				return size;
			}

			public void setSize(String size) {
				this.size = size;
			}

		}

	}

}
