package com.va.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.va.model.ImageDTO;
import com.va.model.ResponseDTO;
import com.va.model.SearchCategoryDTO;
import com.va.model.SearchImageDTO;
import com.va.service.CategoryService;
import com.va.service.ImageService;
import com.va.utils.FileStore;

@Controller
public class ImageController {

	@Autowired
	private ImageService imageService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/admin/image/list")
	public String list(Model model) {
		model.addAttribute("listCategory", categoryService.find(new SearchCategoryDTO()));
		return "admin/images/listImages";
	}

	@PostMapping("/admin/image/list")
	public ResponseEntity<ResponseDTO<ImageDTO>> list(@RequestBody SearchImageDTO searchImageDTO) {
		ResponseDTO<ImageDTO> responseDTO = new ResponseDTO<ImageDTO>();
		responseDTO.setRecordsTotal(imageService.countTotal(searchImageDTO));
		responseDTO.setRecordsFiltered(imageService.count(searchImageDTO));
		responseDTO.setData(imageService.find(searchImageDTO));

		return new ResponseEntity<ResponseDTO<ImageDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/admin/image/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
		imageService.delete(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/admin/image/delete-multi/{ids}")
	public ResponseEntity<String> delete(@PathVariable(name = "ids") List<Long> ids) {
		for (long id : ids) {
			try {
				imageService.delete(id);
			} catch (Exception e) {
			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@PostMapping("/admin/image/add")
	public @ResponseBody ImageDTO add(@ModelAttribute ImageDTO imageDTO) {
		try {
			// save image
			final String UPLOAD_FOLDER = FileStore.UPLOAD_FOLDER;
			if (imageDTO.getImageFile() != null && !imageDTO.getImageFile().isEmpty()) {
				String image = System.currentTimeMillis() + "-image.jpg";
				Path pathImage = Paths.get(UPLOAD_FOLDER + File.separator + image);
				Files.write(pathImage, imageDTO.getImageFile().getBytes());
				imageDTO.setImage(image);
			}
		} catch (IOException e) {
		}
		// save database
		imageService.add(imageDTO);

		return imageDTO;
	}

	@PostMapping("/admin/image/update")
	public @ResponseBody ImageDTO update(@ModelAttribute ImageDTO imageDTO) {
		try {
			// save image
			final String UPLOAD_FOLDER = FileStore.UPLOAD_FOLDER;
			if (imageDTO.getImageFile() != null && !imageDTO.getImageFile().isEmpty()) {
				String image = System.currentTimeMillis() + "-image.jpg";
				Path pathImage = Paths.get(UPLOAD_FOLDER + File.separator + image);
				Files.write(pathImage, imageDTO.getImageFile().getBytes());
				imageDTO.setImage(image);
			}
		} catch (IOException e) {
		}
		// save database
		imageService.update(imageDTO);

		return imageDTO;
	}
}
