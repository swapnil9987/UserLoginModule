package com.icreon.user.service;


import com.icreon.user.model.User;
import com.icreon.user.web.dto.UpdateUserRequestDto;
import com.icreon.user.web.dto.UserRegistrationDto;
/**
 * Service definition which accesses the {@link com.icreon.user.model.User} entity.
 * This is the recommended way to access the entities through an interface
 * rather than using the corresponding repository directly. This allows for
 * separation into repository code and the service layer.
 * 
 * @author swapnil.mane
 * 
 */
public interface UserService {
	UserRegistrationDto newUserRegistration(UserRegistrationDto usersDto);
	boolean checkEmail(String email);
	User getById(Long id);
	UpdateUserRequestDto updateUser(UpdateUserRequestDto updateUserRequest);
	User removeUser(Long id);
}
