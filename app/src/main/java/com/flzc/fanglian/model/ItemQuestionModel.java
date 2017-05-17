package com.flzc.fanglian.model;

import java.io.Serializable;
import java.util.List;

public class ItemQuestionModel implements Serializable {

	private String title;
	private List<OptionQuestionModel> tagsList;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<OptionQuestionModel> getTagsList() {
		return tagsList;
	}

	public void setTagsList(List<OptionQuestionModel> tagsList) {
		this.tagsList = tagsList;
	}

}
