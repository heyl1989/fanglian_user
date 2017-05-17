package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class AgentHouseTypeBean implements Serializable {

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

		private String ticketAmount;
		private String size;
		private String priceTotal;
		private String houseTypeName;
		private String floor;
		private String saleswaySourceId;
		private String sourceId;
		private List<Imgs> imgs;

		
		public String getSourceId() {
			return sourceId;
		}

		public void setSourceId(String sourceId) {
			this.sourceId = sourceId;
		}

		public String getTicketAmount() {
			return ticketAmount;
		}

		public void setTicketAmount(String ticketAmount) {
			this.ticketAmount = ticketAmount;
		}

		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}

		public String getPriceTotal() {
			return priceTotal;
		}

		public void setPriceTotal(String priceTotal) {
			this.priceTotal = priceTotal;
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

		public String getSaleswaySourceId() {
			return saleswaySourceId;
		}

		public void setSaleswaySourceId(String saleswaySourceId) {
			this.saleswaySourceId = saleswaySourceId;
		}

		public List<Imgs> getImgs() {
			return imgs;
		}

		public void setImgs(List<Imgs> imgs) {
			this.imgs = imgs;
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

	}

}