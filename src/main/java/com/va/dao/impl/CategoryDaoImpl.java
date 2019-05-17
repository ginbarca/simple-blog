package com.va.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.va.dao.CategoryDao;
import com.va.entity.Category;
import com.va.model.SearchCategoryDTO;

@Repository
@Transactional
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void add(Category category) {
		entityManager.persist(category);
	}

	@Override
	public void update(Category category) {
		entityManager.merge(category);
	}

	@Override
	public void delete(Category category) {
		entityManager.remove(category);
	}

	@Override
	public Category getById(Long id) {
		return entityManager.find(Category.class, id);
	}

	@Override
	public List<Category> find(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Category> query = builder.createQuery(Category.class);
		Root<Category> root = query.from(Category.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchCategoryDTO.getKeyword())) {
			Predicate predicate1 = builder.like(builder.lower(root.get("name")), "%" + searchCategoryDTO.getKeyword().toLowerCase() + "%");
			predicates.add(builder.or(predicate1));
		}

		if (searchCategoryDTO.getName() != null) {
			Predicate predicate = builder.like(builder.lower(root.get("name")), "%" + searchCategoryDTO.getName().toLowerCase() + "%");
			predicates.add(predicate);
		}
		query.where(predicates.toArray(new Predicate[] {}));

		if (StringUtils.equals(searchCategoryDTO.getSortBy().getData(), "name")) {
			if (searchCategoryDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("name")));
			} else {
				query.orderBy(builder.desc(root.get("name")));
			}
		} else if (StringUtils.equals(searchCategoryDTO.getSortBy().getData(), "id")) {
			if (searchCategoryDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("id")));
			} else {
				query.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchCategoryDTO.getSortBy().getData(), "createdDate")) {
			if (searchCategoryDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("createdDate")));
			} else {
				query.orderBy(builder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchCategoryDTO.getSortBy().getData(), "updatedDate")) {
			if (searchCategoryDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("updatedDate")));
			} else {
				query.orderBy(builder.desc(root.get("updatedDate")));
			}
		}

		TypedQuery<Category> typedQuery = entityManager.createQuery(query.select(root));
		if (searchCategoryDTO.getStart() != null) {
			typedQuery.setFirstResult(searchCategoryDTO.getStart());
			typedQuery.setMaxResults(searchCategoryDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long count(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Category> root = query.from(Category.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isEmpty(searchCategoryDTO.getName())) {
			Predicate predicate = builder.like(builder.lower(root.get("name")), "%" + searchCategoryDTO.getName() + "%");
			predicates.add(predicate);
		}
		query.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotal(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Category> root = query.from(Category.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

}
