package com.va.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.va.dao.UserDao;
import com.va.entity.UserInfo;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private EntityManager em;

	@Override
	public UserInfo getByUsername(String username) {
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<UserInfo> criteriaQuery = criteriaBuilder.createQuery(UserInfo.class);
			Root<UserInfo> root = criteriaQuery.from(UserInfo.class);
			
			criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("username"), username));

			return em.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<UserInfo> findUsers() {
		return null;
	}

}
