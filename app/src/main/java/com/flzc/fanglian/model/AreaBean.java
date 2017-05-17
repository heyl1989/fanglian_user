package com.flzc.fanglian.model;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;
/**
 * 
 * @ClassName: AreaBean 
 * @Description: 地区
 * @author: LU
 * @date: 2016-3-12 下午3:09:10
 */
public class AreaBean implements Serializable {

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
		private List<Areas> areas;
		
		

		public List<Areas> getAreas() {
			return areas;
		}



		public void setAreas(List<Areas> areas) {
			this.areas = areas;
		}



		public class Areas implements Serializable {

			private int provinceId;//省
			private int id;
			private String areaName;//区域名称
			private int cityId;//城市ID
			private String areaCode;//区域代码
			private boolean isSelected = false;
			
			
			public boolean isSelected() {
				return isSelected;
			}
			public void setSelected(boolean isSelected) {
				this.isSelected = isSelected;
			}
			public int getProvinceId() {
				return provinceId;
			}
			public void setProvinceId(int provinceId) {
				this.provinceId = provinceId;
			}
			public int getId() {
				return id;
			}
			public void setId(int id) {
				this.id = id;
			}
			public String getAreaName() {
				return areaName;
			}
			public void setAreaName(String areaName) {
				this.areaName = areaName;
			}
			public int getCityId() {
				return cityId;
			}
			public void setCityId(int cityId) {
				this.cityId = cityId;
			}
			public String getAreaCode() {
				return areaCode;
			}
			public void setAreaCode(String areaCode) {
				this.areaCode = areaCode;
			}
			
			

			
		}

		
	}

}
