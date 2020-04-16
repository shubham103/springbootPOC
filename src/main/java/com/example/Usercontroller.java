package com.example;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/")
public class Usercontroller {
	
	Logger logger = LoggerFactory.getLogger(StudentController.class);


	private final UserRepository userRepository;

	@Autowired
	public Usercontroller(UserRepository  userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("signup")
	public String showSignUpForm(Users user) {
		logger.info("signup form returning");
		return "add-user";
	}

	@GetMapping("list")
	public String showUpdateForm(Model model) {
		model.addAttribute("users", userRepository.findAll());
		logger.info("showing all the existing users");
		return "allusers";
	}

	@PostMapping("add")
	public String addUser(@Valid Users user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			logger.error("error in mapping the received object");
			return "add-user";
		}

		userRepository.save(user);
		logger.info("successfully saved the recieved object");
		return "redirect:list";
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Users user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("user", user);
		logger.info("successfully updated the user details");
		return "update-user";
	}

	@PostMapping("update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid Users user, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			user.setId(id);
			logger.error("unable to upadate the user details");
			return "update-user";
		}

		userRepository.save(user);
		model.addAttribute("users", userRepository.findAll());
		logger.info("successfully updated the user details ");
		return "allusers";
	}

	@GetMapping("delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		Users user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userRepository.delete(user);
		model.addAttribute("users", userRepository.findAll());
		logger.info("successfully deleted the used details");
		return "allusers";
	}
}
