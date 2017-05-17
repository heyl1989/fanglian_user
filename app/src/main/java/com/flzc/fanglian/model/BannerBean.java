package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class BannerBean implements Serializable {

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
		private List<BannerList> list;

		public List<BannerList> getList() {
			return list;
		}

		public void setList(List<BannerList> list) {
			this.list = list;
		}

		public class BannerList implements Serializable {

			private String imgUrl;//banner地址
			private int id;
			private int status;//状态
			private int cityId;//城市ID
			private String imgLink;//banner链接
			private int type;

			public String getImgUrl() {
				return imgUrl;
			}

			public void setImgUrl(String imgUrl) {
				this.imgUrl = imgUrl;
			}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public int getStatus() {
				return status;
			}

			public void setStatus(int status) {
				this.status = status;
			}

			public int getCityId() {
				return cityId;
			}

			public void setCityId(int cityId) {
				this.cityId = cityId;
			}

			public String getImgLink() {
				return imgLink;
			}

			public void setImgLink(String imgLink) {
				this.imgLink = imgLink;
			}

			public int getType() {
				return type;
			}

			public void setType(int type) {
				this.type = type;
			}

		}

	}

}
