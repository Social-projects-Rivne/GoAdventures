package io.softserve.goadventures.auth.service;

import io.softserve.goadventures.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

  final
  UserService userService;

  @Autowired
  public VerificationService(UserService userService) {
    this.userService = userService;
  }

  public boolean checkUserStatus() {
    return  true;
  }

}