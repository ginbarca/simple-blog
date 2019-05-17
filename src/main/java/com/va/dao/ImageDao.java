package com.va.dao;

import java.util.List;

import com.va.entity.Image;
import com.va.model.SearchImageDTO;

public interface ImageDao {
	void add(Image image);

	void update(Image image);

	void delete(Image image);

	Image getById(Long id);

	List<Image> find(SearchImageDTO searchImageDTO);

	Long count(SearchImageDTO searchImageDTO);

	Long countTotal(SearchImageDTO searchImageDTO);
}
