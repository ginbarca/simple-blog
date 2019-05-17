package com.va.model;

import java.io.Serializable;

public class CommentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String email;
	private String comment;
	private String createdDate;
	private String updatedDate;
	private Long postId;

	public CommentDTO() {
		super();
	}

	public CommentDTO(Long id, String name, String email, String comment, String createdDate, String updatedDate,
			Long postId) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.comment = comment;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.postId = postId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

}
