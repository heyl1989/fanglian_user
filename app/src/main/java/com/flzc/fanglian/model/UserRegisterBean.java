package com.flzc.fanglian.model;

public class UserRegisterBean extends BaseInfoBean {
	private UserRegisterResult result;

	public UserRegisterResult getResult() {
		return result;
	}

	public void setResult(UserRegisterResult result) {
		this.result = result;
	}

	public class UserRegisterResult {
		String tokenId;

		public String getTokenId() {
			return tokenId;
		}

		public void setTokenId(String tokenId) {
			this.tokenId = tokenId;
		}

	}

}
