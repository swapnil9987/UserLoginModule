package com.icreon.user.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icreon.user.service.UserService;
import com.icreon.user.web.dto.UserRegistrationDto;

/**
 * @author swapnil.mane
 * This is controller class 
 * when client send request it comes 
 * to controller first
 */
@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * This is used to send
	 * object to frontend
	 */
	@ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
	
	/**
	 * New USER_REGISTRATION.
	 */
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}
	
	/**
	 * User can fill form and data is stored to db
	 * it display err if user try to register with same email id
	 * already present in db
	 * @throws UsernamePresentException 
	 */
	@PostMapping
	public String registerUserAccount(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto, BindingResult bindingResult,
			HttpSession session) {
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		boolean checkEmail = this.userService.checkEmail(registrationDto.getEmail());
		if(checkEmail) {
			session.setAttribute("message", registrationDto.getEmail() + " is already exists");
		}else {
			UserRegistrationDto newUserRegistration = this.userService.newUserRegistration(registrationDto);
			if(newUserRegistration != null) {
				session.setAttribute("message", " You have been Registered Successfully!!");
			}else
				session.setAttribute("message", " Something went wrong on server");
			
		}
		return "redirect:/registration";
	}
	
}
