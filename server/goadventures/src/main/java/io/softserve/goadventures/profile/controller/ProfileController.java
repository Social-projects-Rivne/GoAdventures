package io.softserve.goadventures.profile.controller;


import io.softserve.goadventures.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

public class ProfileController {

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("fullname", user.getFullname());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("avatar", user.getAvatar());

        return "profile";
    }
}

