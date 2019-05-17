package com.va.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.va.dao.UserDao;
import com.va.entity.UserInfo;
import com.va.model.UserInfoDTO;
import com.va.model.UserPrincipal;
import com.va.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo userInfo = userDao.getByUsername(username);
		if (userInfo == null) {
			throw new UsernameNotFoundException("User not found");
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(userInfo.getRole().getName()));
		UserPrincipal userPrincipal = new UserPrincipal(username, userInfo.getPassword(), true, true, true, true, authorities);
		userPrincipal.setId(userInfo.getId());
		userPrincipal.setRoleId(userInfo.getRole().getId());
		return userPrincipal;
	}

	@Override
	public UserInfoDTO getByUsername(String username) {
		UserInfo userInfo = userDao.getByUsername(username);
		if (userInfo != null) {
			UserInfoDTO userInfoDTO = new UserInfoDTO();
			userInfoDTO.setId(userInfo.getId());
			userInfoDTO.setUsername(userInfo.getUsername());
			userInfoDTO.setRoleId(userInfo.getRole().getId());

			return userInfoDTO;
		}
		return null;
	}

	@Override
	public List<UserInfoDTO> findUserInfo() {
		return null;
	}

}
