package com.va.service.impl;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.va.model.UserPrincipal;
import com.va.entity.UserInfo;

public class AuditorAwareImpl implements AuditorAware<UserInfo> {
	@Override
	public Optional<UserInfo> getCurrentAuditor() {
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return Optional.of(new UserInfo(currentUser.getId()));
		}
		return Optional.ofNullable(null);
	}

}
