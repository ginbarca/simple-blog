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

import com.va.dao.PostDao;
import com.va.entity.Post;
import com.va.model.SearchPostDTO;

@Repository
@Transactional
public class PostDaoImpl implements PostDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void add(Post post) {
		entityManager.persist(post);
	}

	@Override
	public void update(Post post) {
		entityManager.merge(post);
	}

	@Override
	public void delete(Post post) {
		entityManager.remove(post);
	}

	@Override
	public Post getById(Long id) {
		return entityManager.find(Post.class, id);
	}

	@Override
	public List<Post> find(SearchPostDTO searchPostDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> query = builder.createQuery(Post.class);
		Root<Post> root = query.from(Post.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchPostDTO.getKeyword())) {
			Predicate predicate1 = builder.like(builder.lower(root.get("title")), "%" + searchPostDTO.getKeyword().toLowerCase() + "%");
			predicates.add(builder.or(predicate1));
		}
		if (searchPostDTO.getTitle() != null) {
			Predicate predicate = builder.like(builder.lower(root.get("title")), "%" + searchPostDTO.getTitle().toLowerCase() + "%");
			predicates.add(predicate);
		}
		query.where(predicates.toArray(new Predicate[] {}));

		if (StringUtils.equals(searchPostDTO.getSortBy().getData(), "title")) {
			if (searchPostDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("title")));
			} else {
				query.orderBy(builder.desc(root.get("title")));
			}
		} else if (StringUtils.equals(searchPostDTO.getSortBy().getData(), "id")) {
			if (searchPostDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("id")));
			} else {
				query.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchPostDTO.getSortBy().getData(), "createdDate")) {
			if (searchPostDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("createdDate")));
			} else {
				query.orderBy(builder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchPostDTO.getSortBy().getData(), "updatedDate")) {
			if (searchPostDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("updatedDate")));
			} else {
				query.orderBy(builder.desc(root.get("updatedDate")));
			}
		}

		TypedQuery<Post> typedQuery = entityManager.createQuery(query.select(root));
		if (searchPostDTO.getStart() != null) {
			typedQuery.setFirstResult(searchPostDTO.getStart());
			typedQuery.setMaxResults(searchPostDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long count(SearchPostDTO searchPostDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Post> root = query.from(Post.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isEmpty(searchPostDTO.getKeyword())) {
			Predicate predicate = builder.like(builder.lower(root.get("title")), "%" + searchPostDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate);
		}
		query.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotal(SearchPostDTO searchPostDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Post> root = query.from(Post.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

}
