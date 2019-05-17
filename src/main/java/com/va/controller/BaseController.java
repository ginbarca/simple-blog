package com.va.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.va.model.SearchCategoryDTO;
import com.va.model.SearchImageDTO;
import com.va.model.SearchPostDTO;
import com.va.service.CategoryService;
import com.va.service.ImageService;
import com.va.service.PostService;

@Controller
public class BaseController {

	@Autowired
	private ImageService imageService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostService postService;
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("listCategories", categoryService.find(new SearchCategoryDTO()));
		model.addAttribute("listImages", imageService.find(new SearchImageDTO()));
		model.addAttribute("listPosts", postService.find(new SearchPostDTO()));
		
		return "client/index";
	}

	@RequestMapping("/admintest")
	public String homeAdmin() {
		return "admin/index";
	}

	@RequestMapping(value = "/login")
	public String login(@RequestParam(required = false, name = "e") String message, final Model model) {
		if (isLogin()) {
			return "redirect:/user/home";
		}
		if (message != null && !message.isEmpty()) {
			if (message.equals("logout")) {
				model.addAttribute("message", "Logout!");
			}
			if (message.equals("error")) {
				model.addAttribute("message", "Login Failed!");
			}
		}
		return "login";
	}

	@RequestMapping("/user/home")
	public String userHome() {
		return "redirect:/";
	}

	@RequestMapping("/admin")
	public String admin() {
		return "admin";
	}

	@RequestMapping("/403")
	public String accessDenied() {
		return "403";
	}

	protected boolean isLogin() {
		return SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
	}
}