package com.va.service;

import java.util.List;

import com.va.model.UserInfoDTO;

public interface UserService {
	UserInfoDTO getByUsername(String username);

	List<UserInfoDTO> findUserInfo();
}
