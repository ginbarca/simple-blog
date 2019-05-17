package com.va.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.va.dao.ImageDao;
import com.va.entity.Category;
import com.va.entity.Image;
import com.va.model.SearchImageDTO;

@Repository
@Transactional
public class ImageDaoImpl implements ImageDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void add(Image image) {
		entityManager.persist(image);
	}

	@Override
	public void update(Image image) {
		entityManager.merge(image);
	}

	@Override
	public void delete(Image image) {
		entityManager.remove(image);
	}

	@Override
	public Image getById(Long id) {
		return entityManager.find(Image.class, id);
	}

	@Override
	public List<Image> find(SearchImageDTO searchImageDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Image> query = builder.createQuery(Image.class);
		Root<Image> root = query.from(Image.class);
		Join<Image, Category> category = root.join("category");

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchImageDTO.getKeyword())) {
			Predicate predicate1 = builder.like(builder.lower(category.get("name")), "%" + searchImageDTO.getKeyword().toLowerCase() + "%");
			predicates.add(builder.or(predicate1));
		}
		if (searchImageDTO.getCategoryId() != null) {
			Predicate predicate1 = builder.equal(builder.lower(category.get("id")), searchImageDTO.getCategoryId());
			predicates.add(builder.or(predicate1));
		}
		query.where(predicates.toArray(new Predicate[] {}));

		if (StringUtils.equals(searchImageDTO.getSortBy().getData(), "id")) {
			if (searchImageDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("id")));
			} else {
				query.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchImageDTO.getSortBy().getData(), "createdDate")) {
			if (searchImageDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("createdDate")));
			} else {
				query.orderBy(builder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchImageDTO.getSortBy().getData(), "updatedDate")) {
			if (searchImageDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("updatedDate")));
			} else {
				query.orderBy(builder.desc(root.get("updatedDate")));
			}
		} else if (StringUtils.equals(searchImageDTO.getSortBy().getData(), "categoryName")) {
			if (searchImageDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(category.get("name")));
			} else {
				query.orderBy(builder.desc(category.get("name")));
			}
		}

		TypedQuery<Image> typedQuery = entityManager.createQuery(query.select(root));
		if (searchImageDTO.getStart() != null) {
			typedQuery.setFirstResult((searchImageDTO.getStart()));
			typedQuery.setMaxResults(searchImageDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long count(SearchImageDTO searchImageDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Image> root = query.from(Image.class);
		Join<Image, Category> category = root.join("category");

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchImageDTO.getKeyword())) {
			Predicate predicate1 = builder.like(builder.lower(category.get("name")), "%" + searchImageDTO.getKeyword().toLowerCase() + "%");
			predicates.add(builder.or(predicate1));
		}
		query.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotal(SearchImageDTO searchImageDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Image> root = query.from(Image.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

}
