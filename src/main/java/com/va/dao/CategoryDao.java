package com.va.dao;

import java.util.List;

import com.va.entity.Category;
import com.va.model.SearchCategoryDTO;

public interface CategoryDao {
	void add(Category category);

	void update(Category category);

	void delete(Category category);

	Category getById(Long id);

	List<Category> find(SearchCategoryDTO searchCategoryDTO);

	Long count(SearchCategoryDTO searchCategoryDTO);

	Long countTotal(SearchCategoryDTO searchCategoryDTO);
}
