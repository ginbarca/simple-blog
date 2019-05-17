package com.va.service;

import java.util.List;

import com.va.model.PostDTO;
import com.va.model.SearchPostDTO;

public interface PostService {
	void add(PostDTO postDTO);

	void update(PostDTO postDTO);

	void delete(Long id);

	PostDTO getById(Long id);

	List<PostDTO> find(SearchPostDTO searchPostDTO);

	Long count(SearchPostDTO searchPostDTO);

	Long countTotal(SearchPostDTO searchPostDTO);
}
