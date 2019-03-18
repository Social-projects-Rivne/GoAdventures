package io.softserve.goadventures.user.controller;

import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/all")
  public Iterable<User> getAllUsers(){
    return userService.getAllUsers();
  }
}
