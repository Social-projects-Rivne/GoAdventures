package io.softserve.goadventures.controllers;

import io.softserve.goadventures.entities.User;
import io.softserve.goadventures.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepository;

	@GetMapping("/auth")
	public String greeting(
			@RequestParam(name = "name", required = false) String name,
			Map<String, Object> model) {

		return "auth";
	}

	@PostMapping
	public void auth(@RequestParam String email,
					@RequestParam String password) {
		logger.info("add: Entered data = name = "  + "; email = " + email + "; password = " + password);

		User user = userRepository.findByEmail(email);
		if (user != null) {
			logger.info("checkEmail: " + user.toString());
			if(user.getPassword()==password)
				logger.info("checkPassword: " + user.toString());
			else
				logger.info("checkPassword: password is wrong" );

		} else {
			logger.info("checkEmail: can't find this user");

		}

	}


}