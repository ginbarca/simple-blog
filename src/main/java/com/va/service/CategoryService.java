package com.va.service;

import java.util.List;

import com.va.model.CategoryDTO;
import com.va.model.SearchCategoryDTO;

public interface CategoryService {
	void add(CategoryDTO categoryDTO);

	void update(CategoryDTO categoryDTO);

	void delete(Long id);

	CategoryDTO getById(Long id);

	List<CategoryDTO> find(SearchCategoryDTO searchCategoryDTO);

	Long count(SearchCategoryDTO searchCategoryDTO);

	Long countTotal(SearchCategoryDTO searchCategoryDTO);
}
