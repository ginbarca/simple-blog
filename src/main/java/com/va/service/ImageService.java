package com.va.service;

import java.util.List;

import com.va.model.ImageDTO;
import com.va.model.SearchImageDTO;

public interface ImageService {
	void add(ImageDTO imageDTO);

	void update(ImageDTO imageDTO);

	void delete(Long id);

	ImageDTO getById(Long id);

	List<ImageDTO> find(SearchImageDTO searchImageDTO);

	Long count(SearchImageDTO searchImageDTO);

	Long countTotal(SearchImageDTO searchImageDTO);
}
