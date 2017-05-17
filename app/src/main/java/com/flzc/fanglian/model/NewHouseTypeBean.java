package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class NewHouseTypeBean implements Serializable {

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
		private List<HouseTypes> houseTypes;

		public List<HouseTypes> getHouseTypes() {
			return houseTypes;
		}

		public void setHouseTypes(List<HouseTypes> houseTypes) {
			this.houseTypes = houseTypes;
		}

		public class HouseTypes implements Serializable {

			private String img;
			private String size;
			private String price;
			private String type;

			public String getImg() {
				return img;
			}

			public void setImg(String img) {
				this.img = img;
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

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

		}
	}

}
