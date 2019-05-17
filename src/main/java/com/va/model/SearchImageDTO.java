package com.va.model;

public class SearchImageDTO extends SearchDTO {

	private static final long serialVersionUID = 1L;

	private Long categoryId;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}
