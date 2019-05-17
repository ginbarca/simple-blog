package com.va.dao;

import java.util.List;

import com.va.entity.UserInfo;

public interface UserDao {
	UserInfo getByUsername(String username);
	List<UserInfo> findUsers();
}
