package com.va.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.va.model.CategoryDTO;
import com.va.model.ResponseDTO;
import com.va.model.SearchCategoryDTO;
import com.va.service.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/admin/category/list")
	public String list() {
		return "admin/category/listCategories";
	}

	@PostMapping("/admin/category/list")
	public ResponseEntity<ResponseDTO<CategoryDTO>> list(@RequestBody SearchCategoryDTO searchCategoryDTO) {
		ResponseDTO<CategoryDTO> responseDTO = new ResponseDTO<CategoryDTO>();
		responseDTO.setRecordsTotal(categoryService.countTotal(searchCategoryDTO));
		responseDTO.setRecordsFiltered(categoryService.count(searchCategoryDTO));
		responseDTO.setData(categoryService.find(searchCategoryDTO));

		return new ResponseEntity<ResponseDTO<CategoryDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/admin/category/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
		categoryService.delete(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/admin/category/delete-multi/{ids}")
	public ResponseEntity<String> delete(@PathVariable(name = "ids") List<Long> ids) {
		for (long id : ids) {
			try {
				categoryService.delete(id);
			} catch (Exception e) {
			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@PostMapping("/admin/category/add")
	public @ResponseBody CategoryDTO add(@ModelAttribute CategoryDTO categoryDTO) {
		// save database
		categoryService.add(categoryDTO);

		return categoryDTO;
	}

	@PostMapping("/admin/category/update")
	public @ResponseBody CategoryDTO update(@ModelAttribute CategoryDTO categoryDTO) {
		// save database
		categoryService.update(categoryDTO);

		return categoryDTO;
	}
}
