package com.icreon.user.service;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.icreon.user.model.User;
import com.icreon.user.repository.UserRepository;
import com.icreon.user.util.Constant;
import com.icreon.user.web.dto.UpdateUserRequestDto;
import com.icreon.user.web.dto.UserRegistrationDto;

import lombok.extern.slf4j.Slf4j;

/**
 * @author swapnil.mane
 *
 */
/**
 * This class used to write business logic
 *
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * This method used to register new user
	 *
	 */
	@Override
	public UserRegistrationDto newUserRegistration(UserRegistrationDto usersDto) {
		Optional<User> findByEmail = userRepository.findByEmail(usersDto.getEmail());
		try {

			if (!findByEmail.isPresent()) {
				User user = new User();
				// user.setId(usersDto.getId());
				if (StringUtils.isNotBlank(usersDto.getFirstName())) {
					user.setFirstName(usersDto.getFirstName().trim());
				}
				if (StringUtils.isNotBlank(usersDto.getLastName())) {
					user.setLastName(usersDto.getLastName().trim());
				}
				if (StringUtils.isNotBlank(usersDto.getEmail())) {
					user.setEmail(usersDto.getEmail().toLowerCase().trim());
				}
				if (StringUtils.isNotBlank(usersDto.getPassword())) {
					user.setPassword(this.passwordEncoder.encode(usersDto.getPassword().trim()));
				}
				if (StringUtils.isNotBlank(usersDto.getPhoneNumber())) {
					user.setPhoneNumber(usersDto.getPhoneNumber().trim());
				}
				user.setIsActive(true);
				user.setStatus(Constant.ACTIVATED);
				user.setCreatedOn(new Date());
				// saving to db
				this.userRepository.save(user);
			}
		} catch (Exception e) {
			log.error("Error occured in transforming ", e);
		}
		return usersDto;
	}
	
	/**
	 * This method used to check whether user is present in db or not
	 */
	@Override
	public boolean checkEmail(String email) {
		return this.userRepository.existsByEmail(email);
	}

	/**
	 * This method used to get user from db with id
	 */
	@Override
	public User getById(Long id) {
		User user = this.userRepository.findById(id);
		if (user != null);
			return user;
	}

	/**
	 * This method used to update user
	 */
	@Override
	public UpdateUserRequestDto updateUser(UpdateUserRequestDto updateUserRequest) {
		User user = this.userRepository.findById(updateUserRequest.getId());
		try {

			if (user != null) {
				if (updateUserRequest.getId() != null) {
					user.setId(updateUserRequest.getId());
				}
				if (StringUtils.isNotBlank(updateUserRequest.getFirstName())) {
					user.setFirstName(updateUserRequest.getFirstName().trim());
				}
				if (StringUtils.isNotBlank(updateUserRequest.getLastName())) {
					user.setLastName(updateUserRequest.getLastName().trim());
				}
				if (StringUtils.isNotBlank(updateUserRequest.getEmail())) {
					user.setEmail(updateUserRequest.getEmail().toLowerCase().trim());
				}
				if (StringUtils.isNotBlank(updateUserRequest.getPhoneNumber())) {
					user.setPhoneNumber(updateUserRequest.getPhoneNumber().trim());
				}
				user.setUpdatedOn(new Date());
				this.userRepository.save(user);
			}
		} catch (Exception e) {
			log.error("Error occured in transforming ", e);
		}
		return updateUserRequest;
	}

	/**
	 * This method used to delete user
	 */
	@Override
	public User removeUser(Long id) {
		User user = this.userRepository.findById(id);
		if (user != null) {
			user.setIsActive(false);
			user.setDeletedOn(new Date());
			user.setStatus(Constant.DEACTIVATED);
			userRepository.save(user);
		}
		return user;
	}

}
