package com.icreon.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.icreon.user.model.User;
import com.icreon.user.repository.UserRepository;


@Component
public class UserInfoUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User orElseThrow = this.userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException(username +" Not Found "));
		return orElseThrow;
	}
	
}
