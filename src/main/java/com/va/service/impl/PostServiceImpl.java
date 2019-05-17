package com.va.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.va.dao.PostDao;
import com.va.entity.Post;
import com.va.model.PostDTO;
import com.va.model.SearchPostDTO;
import com.va.service.PostService;
import com.va.utils.DateTimeUtils;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDao postDao;

	@Override
	public void add(PostDTO postDTO) {
		Post post = new Post();
		post.setTitle(postDTO.getTitle());
		post.setImage(postDTO.getImage());
		post.setContent(postDTO.getContent());

		postDao.add(post);
		postDTO.setId(post.getId());
	}

	@Override
	public void update(PostDTO postDTO) {
		Post post = postDao.getById(postDTO.getId());
		if (post != null) {
			post.setTitle(postDTO.getTitle());
			post.setImage(postDTO.getImage());
			post.setContent(postDTO.getContent());

			postDao.update(post);
		}
	}

	@Override
	public void delete(Long id) {
		Post post = postDao.getById(id);
		if (post != null) {
			postDao.delete(post);
		}
	}

	@Override
	public PostDTO getById(Long id) {
		Post post = postDao.getById(id);
		if (post != null) {
			PostDTO postDTO = new PostDTO();
			postDTO.setId(post.getId());
			postDTO.setTitle(post.getTitle());
			postDTO.setImage(post.getImage());
			postDTO.setContent(post.getContent());
			postDTO.setCreatedDate(DateTimeUtils.formatDate(post.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			postDTO.setUpdatedDate(DateTimeUtils.formatDate(post.getUpdatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			postDTO.setCreatedById(post.getCreatedBy().getId());
			postDTO.setCreatedByName(post.getCreatedBy().getUsername());
			postDTO.setUpdatedById(post.getUpdatedBy().getId());
			postDTO.setUpdatedByName(post.getUpdatedBy().getUsername());

			return postDTO;
		}
		return null;
	}

	@Override
	public List<PostDTO> find(SearchPostDTO searchPostDTO) {
		List<Post> posts = postDao.find(searchPostDTO);
		List<PostDTO> postDTOs = new ArrayList<PostDTO>();
		posts.forEach(post -> {
			PostDTO postDTO = new PostDTO();
			postDTO.setId(post.getId());
			postDTO.setTitle(post.getTitle());
			postDTO.setImage(post.getImage());
			postDTO.setContent(post.getContent());
			postDTO.setCreatedDate(DateTimeUtils.formatDate(post.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			postDTO.setUpdatedDate(DateTimeUtils.formatDate(post.getUpdatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			postDTO.setCreatedById(post.getCreatedBy().getId());
			postDTO.setCreatedByName(post.getCreatedBy().getUsername());
			postDTO.setUpdatedById(post.getUpdatedBy().getId());
			postDTO.setUpdatedByName(post.getUpdatedBy().getUsername());

			postDTOs.add(postDTO);
		});
		return postDTOs;
	}

	@Override
	public Long count(SearchPostDTO searchPostDTO) {
		return postDao.count(searchPostDTO);
	}

	@Override
	public Long countTotal(SearchPostDTO searchPostDTO) {
		return postDao.countTotal(searchPostDTO);
	}

}
