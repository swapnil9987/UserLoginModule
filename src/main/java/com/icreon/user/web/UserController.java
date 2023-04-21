package com.icreon.user.web;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.icreon.user.model.User;
import com.icreon.user.repository.UserRepository;
import com.icreon.user.service.UserService;
import com.icreon.user.web.dto.UpdateUserRequestDto;


/**
 * This is controller class when client send request it comes 
 * to controller first and controller will communicate with remaining layers.
 * 
 * @author swapnil.mane
 *
 */
@Controller
public class UserController {
	
	private final String password1 = "user";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	SmartValidator validator;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Display_Login_page
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	/**
	 * Redirect towords home page after successful login
	 */
	
	@GetMapping("/")
	public String home(Model model, Principal principal) {
		String userName = principal.getName();
		Optional<User> findByEmail = this.userRepository.findByEmail(userName);
		User currentUser = findByEmail.get();
		System.out.println(" Current user password : " + currentUser.getPassword());

		if (this.bCryptPasswordEncoder.matches(this.password1, currentUser.getPassword())) {
			return "reset_password";
		} else {
			model.addAttribute("pagetitle", "Homepage");
			return "redirect:/homepage";
		}
	}
	
	/**
	 * Endpoint used for reset password functionality
	 * for users added through excel sheet to system
	 */
	@PostMapping("/resetpassword")
	public String resetPasswordForExcelData(@RequestParam("password") String password,
			@RequestParam("confirmpassword") String confirmPassword, HttpSession session, Principal principal) {
		String userName = principal.getName();
		Optional<User> findByEmail = this.userRepository.findByEmail(userName);
		User user = findByEmail.get();
		if(password.equals(confirmPassword)) {
			user.setPassword(this.bCryptPasswordEncoder.encode(confirmPassword));
			this.userRepository.save(user);
			session.setAttribute("message", "You have successfully changed your password! continue with login");
			return "redirect:/login";
		}else {
			session.setAttribute("message", "Password doesn't matches pls try again");
			return "reset_password";
		}
		
	}
	
	/**
	 * Display All user list on dashboard
	 */
	@GetMapping(value = "/homepage")
	public String showDashboard(Model model) {
		model.addAttribute("pagetitle", "Dashboard");
		return findPaginateAndSorting(0, "firstName", "asc", model);
	}
	
	/**
	 * Pagination
	 */
	@GetMapping("/page/{pageno}")
	public String findPaginateAndSorting(@PathVariable(value = "pageno") int pageno,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, Model model) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageno, 2000, sort);
		Page<User> page = userRepository.findAll(pageable);
		List<User> list = page.getContent();
		model.addAttribute("pageno", pageno);
		model.addAttribute("totalElements", page.getTotalElements());
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("allusers", list);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("revSortDir", sortDir.equals("asc") ? "desc" : "asc");
		return "index";
	}

	/**
	 * Endpoint used to view user profile
	 */
	@GetMapping("/viewuser/{id}")
	public String viewItPartner(@PathVariable(value = "id") Long id, Model model) {
		User user = this.userService.getById(id);
		model.addAttribute("user", user);
		model.addAttribute("pagetitle", "User Profile");
		return "view_user";
	}

	/**
	 * Endpoint used to view edit user page
	 */
	@GetMapping("/edit-user/{id}")
	public String editUser(@PathVariable(value = "id") Long id, Model model) {
		User user = this.userService.getById(id);
		model.addAttribute("user", user);
		model.addAttribute("pagetitle", "Edit User");
		return "edit_user";
	}

	/**
	 * Endpoint used to edit user
	 */
	@PostMapping("/edituser")
	public String updateUser(@ModelAttribute("user") UpdateUserRequestDto updateUser, Model model, BindingResult result,
			RedirectAttributes redirectAttributes, HttpSession session) {
		model.addAttribute("pagetitle", "Edit User");
		validator.validate(updateUser, result);
		if (result.hasErrors()) {
			return "edit_user";
		}

		try {
			this.userService.updateUser(updateUser);
			session.setAttribute("msg", updateUser.getFirstName()+ " is updated Successfully");
			redirectAttributes.addFlashAttribute("message", "User updated Successfully");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		} catch (Exception e) {
			session.setAttribute("msg", "Something went wrong pls try again!!");
			model.addAttribute("message", "Something went wrong pls try again!");
			model.addAttribute("alertClass", "alert-danger");
			return "edit_user";
		}
		return "redirect:/homepage";

	}

	/**
	 * Endpoint used to delete user
	 */
	@GetMapping("/deleteuser/{id}")
	public String deleteItPartner(@PathVariable(value = "id") Long id, Model model,
			RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			this.userService.removeUser(id);
			session.setAttribute("msgg",  "User Deleted Successfully");
			redirectAttributes.addFlashAttribute("message", "User Deleted Successfully");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				session.setAttribute("msgg",  "Problem in deleting User2");
				redirectAttributes.addFlashAttribute("message", "Problem in deleting User2");
			} else if (e.getCause() instanceof DataException) {
				session.setAttribute("msgg",  "Problem in deleting User1");
				redirectAttributes.addFlashAttribute("message", "Problem in deleting User1");
				model.addAttribute("alertClass", "alert-danger");
			} else {
				session.setAttribute("msgg",  "Problem in deleting User");
				redirectAttributes.addFlashAttribute("message", "Problem in deleting User");
				redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			}
			return "view_user";
		}
		return "redirect:/homepage";
	}
	
}
