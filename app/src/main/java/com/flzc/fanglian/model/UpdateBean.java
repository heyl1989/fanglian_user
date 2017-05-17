package com.flzc.fanglian.model;

import java.io.Serializable;

public class UpdateBean implements Serializable {

	private int status;
	private Result result;

	
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	

	public class Result implements Serializable {

		private String versionLast;
		private String versionUpdate;
		private String url;
		private String lastMemo;

		public String getVersionLast() {
			return versionLast;
		}

		public void setVersionLast(String versionLast) {
			this.versionLast = versionLast;
		}

		public String getVersionUpdate() {
			return versionUpdate;
		}

		public void setVersionUpdate(String versionUpdate) {
			this.versionUpdate = versionUpdate;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getLastMemo() {
			return lastMemo;
		}

		public void setLastMemo(String lastMemo) {
			this.lastMemo = lastMemo;
		}

	}
}
