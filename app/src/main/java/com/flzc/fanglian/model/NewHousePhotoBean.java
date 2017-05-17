package com.flzc.fanglian.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public class NewHousePhotoBean implements Serializable {

	private int status;
	private String msg;
	private Result result;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public class Result implements Serializable {
		private List<Imgs> imgs;

		public List<Imgs> getImgs() {
			return imgs;
		}

		public void setImgs(List<Imgs> imgs) {
			this.imgs = imgs;
		}

		public class Imgs implements Serializable {

			private int id;
			private Long createTime;
			private Long updateTime;
			private String name;// 图片名称
			private int state;
			private int isCover;// 是否封面（0-不是，1-是）
			private int type;// 图片类型
			private String url;// 图片URL
			private int buildingId;// 楼盘ID

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

			public Long getUpdateTime() {
				return updateTime;
			}

			public void setUpdateTime(Long updateTime) {
				this.updateTime = updateTime;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public int getState() {
				return state;
			}

			public void setState(int state) {
				this.state = state;
			}

			public int getIsCover() {
				return isCover;
			}

			public void setIsCover(int isCover) {
				this.isCover = isCover;
			}

			public int getType() {
				return type;
			}

			public void setType(int type) {
				this.type = type;
			}

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public int getBuildingId() {
				return buildingId;
			}

			public void setBuildingId(int buildingId) {
				this.buildingId = buildingId;
			}

		}

	}

}