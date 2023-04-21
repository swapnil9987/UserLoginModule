package com.icreon.user.web.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
/**
 * @author swapnil.mane
 *
 * This class used to transfer data between
 * two process
 */
@Data
public class UpdateUserRequestDto {
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
	@Pattern(regexp = "^\\d{0,20}+$", message = "Phone number must be a number")
	@Size(min = 0, max = 10, message = "Please enter phone number with 10 digits")
	private String phoneNumber;
	private Date updatedOn;
	private Date deletedOn;
}
