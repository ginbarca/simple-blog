package com.va.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.va.dao.CategoryDao;
import com.va.entity.Category;
import com.va.model.CategoryDTO;
import com.va.model.SearchCategoryDTO;
import com.va.service.CategoryService;
import com.va.utils.DateTimeUtils;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Override
	public void add(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setName(categoryDTO.getName());

		categoryDao.add(category);
		categoryDTO.setId(category.getId());
	}

	@Override
	public void update(CategoryDTO categoryDTO) {
		Category category = categoryDao.getById(categoryDTO.getId());
		if (category!=null) {
			category.setName(categoryDTO.getName());

			categoryDao.update(category);
		}
	}

	@Override
	public void delete(Long id) {
		Category category = categoryDao.getById(id);
		if (category!=null) {
			categoryDao.delete(category);
		}
	}

	@Override
	public CategoryDTO getById(Long id) {
		Category category = categoryDao.getById(id);
		if (category!=null) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(category.getId());
			categoryDTO.setName(category.getName());
			categoryDTO.setCreatedDate(DateTimeUtils.formatDate(category.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			categoryDTO.setUpdatedDate(DateTimeUtils.formatDate(category.getUpdatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			
			return categoryDTO;
		}
		return null;
	}

	@Override
	public List<CategoryDTO> find(SearchCategoryDTO searchCategoryDTO) {
		List<Category> categories = categoryDao.find(searchCategoryDTO);
		List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();
		categories.forEach(category->{
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(category.getId());
			categoryDTO.setName(category.getName());
			categoryDTO.setCreatedDate(DateTimeUtils.formatDate(category.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			categoryDTO.setUpdatedDate(DateTimeUtils.formatDate(category.getUpdatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			
			categoryDTOs.add(categoryDTO);
		});
		return categoryDTOs;
	}

	@Override
	public Long count(SearchCategoryDTO searchCategoryDTO) {
		return categoryDao.count(searchCategoryDTO);
	}

	@Override
	public Long countTotal(SearchCategoryDTO searchCategoryDTO) {
		return categoryDao.countTotal(searchCategoryDTO);
	}

}
