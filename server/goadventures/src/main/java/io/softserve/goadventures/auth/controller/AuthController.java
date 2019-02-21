package io.softserve.goadventures.auth.controller;

import io.softserve.goadventures.auth.services.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
public class AuthController {
  @Autowired
  private VerificationService verificationService;


  @GetMapping("/verify")
  private String verifyUser() {
    if (verificationService.checkUserStatus()) {
      // if true
      return "bearer token";
    } else {
      return "Error message";
    }

  }

}
