package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class SearchHintBean implements Serializable {

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
		private List<SearchList> list;

		public List<SearchList> getList() {
			return list;
		}

		public void setList(List<SearchList> list) {
			this.list = list;
		}

		public class SearchList implements Serializable {

			private String buildingId;
			private String name;
			private int id;
			private String buildingName;
			
			
			public String getBuildingId() {
				return buildingId;
			}

			public void setBuildingId(String buildingId) {
				this.buildingId = buildingId;
			}

			public String getBuildingName() {
				return buildingName;
			}

			public void setBuildingName(String buildingName) {
				this.buildingName = buildingName;
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

		}

	}

}
