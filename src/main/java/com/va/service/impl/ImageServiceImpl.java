package com.va.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.va.dao.ImageDao;
import com.va.entity.Category;
import com.va.entity.Image;
import com.va.model.ImageDTO;
import com.va.model.SearchImageDTO;
import com.va.service.ImageService;
import com.va.utils.DateTimeUtils;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageDao imageDao;
	
	@Override
	public void add(ImageDTO imageDTO) {
		Image image = new Image();
		image.setImage(imageDTO.getImage());
		image.setCategory(new Category(imageDTO.getCategoryId()));
		
		imageDao.add(image);
		imageDTO.setId(image.getId());
	}

	@Override
	public void update(ImageDTO imageDTO) {
		Image image = imageDao.getById(imageDTO.getId());
		if (image!=null) {
			image.setImage(imageDTO.getImage());
			image.setCategory(new Category(imageDTO.getCategoryId()));
			
			imageDao.update(image);
		}
	}

	@Override
	public void delete(Long id) {
		Image image = imageDao.getById(id);
		if (image!=null) {
			imageDao.delete(image);
		}
	}

	@Override
	public ImageDTO getById(Long id) {
		Image image = imageDao.getById(id);
		if (image!=null) {
			ImageDTO imageDTO = new ImageDTO();
			imageDTO.setId(image.getId());
			imageDTO.setImage(image.getImage());
			imageDTO.setCategoryId(image.getCategory().getId());
			imageDTO.setCategoryName(image.getCategory().getName());
			imageDTO.setCreatedDate(DateTimeUtils.formatDate(image.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			imageDTO.setUpdatedDate(DateTimeUtils.formatDate(image.getUpdatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			
			return imageDTO;
		}
		return null;
	}

	@Override
	public List<ImageDTO> find(SearchImageDTO searchImageDTO) {
		List<Image> images = imageDao.find(searchImageDTO);
		List<ImageDTO> imageDTOs = new ArrayList<ImageDTO>();
		images.forEach(image->{
			ImageDTO imageDTO = new ImageDTO();
			imageDTO.setId(image.getId());
			imageDTO.setImage(image.getImage());
			imageDTO.setCategoryId(image.getCategory().getId());
			imageDTO.setCategoryName(image.getCategory().getName());
			imageDTO.setCreatedDate(DateTimeUtils.formatDate(image.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			imageDTO.setUpdatedDate(DateTimeUtils.formatDate(image.getUpdatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			
			imageDTOs.add(imageDTO);
		});
		return imageDTOs;
	}

	@Override
	public Long count(SearchImageDTO searchImageDTO) {
		return imageDao.count(searchImageDTO);
	}

	@Override
	public Long countTotal(SearchImageDTO searchImageDTO) {
		return imageDao.countTotal(searchImageDTO);
	}

}
