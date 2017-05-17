package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class OpenCityBean implements Serializable {

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
		private List<Citys> citys;

		public List<Citys> getCitys() {
			return citys;
		}

		public void setCitys(List<Citys> citys) {
			this.citys = citys;
		}

		public class Citys implements Serializable {

			private int id;
			private String letter;// 字母
			private int cityId;// 城市ID
			private String cityName;// 城市名称

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getLetter() {
				return letter;
			}

			public void setLetter(String letter) {
				this.letter = letter;
			}

			public int getCityId() {
				return cityId;
			}

			public void setCityId(int cityId) {
				this.cityId = cityId;
			}

			public String getCityName() {
				return cityName;
			}

			public void setCityName(String cityName) {
				this.cityName = cityName;
			}

		}

	}

}
