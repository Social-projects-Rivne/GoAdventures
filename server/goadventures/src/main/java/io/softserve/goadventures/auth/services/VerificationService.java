package io.softserve.goadventures.auth.services;

import io.softserve.goadventures.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

  @Autowired
  UserService userService;

  public void checkUserStatus() {

  }

}
