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

import com.va.model.PostDTO;
import com.va.model.ResponseDTO;
import com.va.model.SearchPostDTO;
import com.va.service.PostService;
import com.va.utils.FileStore;

@Controller
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping("/admin/post/list")
	public String list(Model model) {
		return "admin/post/listPosts";
	}

	@PostMapping("/admin/post/list")
	public ResponseEntity<ResponseDTO<PostDTO>> list(@RequestBody SearchPostDTO searchPostDTO) {
		ResponseDTO<PostDTO> responseDTO = new ResponseDTO<PostDTO>();
		responseDTO.setRecordsTotal(postService.countTotal(searchPostDTO));
		responseDTO.setRecordsFiltered(postService.count(searchPostDTO));
		responseDTO.setData(postService.find(searchPostDTO));

		return new ResponseEntity<ResponseDTO<PostDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/admin/post/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
		postService.delete(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/admin/post/delete-multi/{ids}")
	public ResponseEntity<String> delete(@PathVariable(name = "ids") List<Long> ids) {
		for (long id : ids) {
			try {
				postService.delete(id);
			} catch (Exception e) {
			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@PostMapping("/admin/post/add")
	public @ResponseBody PostDTO add(@ModelAttribute PostDTO postDTO) {
		try {
			// save image
			final String UPLOAD_FOLDER = FileStore.UPLOAD_FOLDER;
			if (postDTO.getImageFile() != null && !postDTO.getImageFile().isEmpty()) {
				String image = System.currentTimeMillis() + "-post.jpg";
				Path pathImage = Paths.get(UPLOAD_FOLDER + File.separator + image);
				Files.write(pathImage, postDTO.getImageFile().getBytes());
				postDTO.setImage(image);
			}
		} catch (IOException e) {
		}
		// save database
		postService.add(postDTO);

		return postDTO;
	}

	@PostMapping("/admin/post/update")
	public @ResponseBody PostDTO update(@ModelAttribute PostDTO postDTO) {
		try {
			// save image
			final String UPLOAD_FOLDER = FileStore.UPLOAD_FOLDER;
			if (postDTO.getImageFile() != null && !postDTO.getImageFile().isEmpty()) {
				String image = System.currentTimeMillis() + "-post.jpg";
				Path pathImage = Paths.get(UPLOAD_FOLDER + File.separator + image);
				Files.write(pathImage, postDTO.getImageFile().getBytes());
				postDTO.setImage(image);
			}
		} catch (IOException e) {
		}
		// save database
		postService.update(postDTO);

		return postDTO;
	}

}
