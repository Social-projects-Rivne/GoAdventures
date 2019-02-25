
package io.softserve.goadventures.auth.controllers;


import io.softserve.goadventures.auth.repositories.UserRepository;
import io.softserve.goadventures.auth.entities.User;
import io.softserve.goadventures.auth.entities.UserAuthDto;
import io.softserve.goadventures.auth.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("signin")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);


	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/list")
	public Iterable<User> list() {
		return userRepository.findAll();
	}
	@PostMapping
	public void auth(@RequestBody UserAuthDto userAuthDto) {
		logger.info("add: Entered data = name = "  + "; email = " + userAuthDto.getEmail() + "; password = " + userAuthDto.getPassword());

		User user = userRepository.findByEmail(userAuthDto.getEmail());
		if (user != null) {
			logger.info("checkEmail: " + user.toString());
			if(user.getPassword()==userAuthDto.getPassword())
				logger.info("checkPassword: " + user.toString());
			else
				logger.info("checkPassword: password is wrong" );

		} else {
			logger.info("checkEmail: can't find this userAuthDto");

		}

	}


}