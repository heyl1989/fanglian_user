package com.flzc.fanglian.model;

import java.util.List;

public class SortTypeBean {
	
	private List<SortList> list;
	
	public class SortList{
		private String sortName;
		private boolean isSelect = false;
		
		public String getSortName() {
			return sortName;
		}
		public void setSortName(String sortName) {
			this.sortName = sortName;
		}
		public boolean isSelect() {
			return isSelect;
		}
		public void setSelect(boolean isSelect) {
			this.isSelect = isSelect;
		}
		
	}

}
