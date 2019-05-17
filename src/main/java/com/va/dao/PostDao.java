package com.va.dao;

import java.util.List;

import com.va.entity.Post;
import com.va.model.SearchPostDTO;

public interface PostDao {
	void add(Post post);

	void update(Post post);

	void delete(Post post);

	Post getById(Long id);

	List<Post> find(SearchPostDTO searchPostDTO);

	Long count(SearchPostDTO searchPostDTO);

	Long countTotal(SearchPostDTO searchPostDTO);
}
