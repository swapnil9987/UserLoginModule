package com.icreon.user.web.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/**
 * @author swapnil.mane
 *
 * This class used to transfer data between
 * two process
 */
public class UserRegistrationDto {
	private Long id;
	@NotBlank(message = "Please enter a first name")
	@Size(min = 3, max = 20, message = "Please enter first name with minimum 3 and maximum 20 characters")
	private String firstName;
	@NotBlank(message = "Please enter a last name")
	@Size(min = 3, max = 20, message = "Please enter last name with minimum 3 and maximum 20 characters")
	private String lastName;
	@NotBlank(message = "Please enter a Email Address")
	@Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"+ "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "Please Enter a valid email address")
	@Size(min = 3, max = 50, message = "Please enter email with minimum 3 and maximum 50 characters")
	private String email;
	@NotBlank(message = "Please enter a password")
	@Size(min = 3, max = 20, message = "Please enter password with minimum 3 and maximum 20 characters")
	private String password;
	@Pattern(regexp = "^\\d{0,20}+$", message = "Phone number must be a number")
	@Size(min = 0, max = 10, message = "Please enter phone number with 10 digits and without space")
	private String phoneNumber;
	private String status;
	
	public UserRegistrationDto(){
		
	}
	
	public UserRegistrationDto(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	public UserRegistrationDto(Long id,
			@NotBlank(message = "Please enter a first name") @Size(min = 3, max = 20, message = "Please enter first name with minimum 3 and maximum 20 letters length") String firstName,
			@NotBlank(message = "Please enter a last name") @Size(min = 3, max = 20, message = "Please enter last name with minimum 3 and maximum 20 letters length") String lastName,
			@NotBlank(message = "Please enter a Email Address") @Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "Please Enter a valid email address") @Size(min = 3, max = 50, message = "Please enter email with minimum 3 and maximum 50 letters length") String email,
			@NotBlank(message = "Please enter a password") @Size(min = 3, max = 20, message = "Please enter password with minimum 3 and maximum 20 letters length") String password,
			@Pattern(regexp = "^\\d{0,20}+$", message = "Phone number must be a number") @Size(min = 10, message = "Please enter phone number with 10 digits length") @Pattern(regexp = "^\\d{0,20}+$", message = "Phone number must be a number") @Size(min = 10, message = "Please enter phone number with 10 digits length") String phoneNumber,
			String status) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.status = status;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
