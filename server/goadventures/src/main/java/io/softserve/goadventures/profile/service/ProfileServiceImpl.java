package io.softserve.goadventures.profile.service;

import io.softserve.goadventures.profile.repository.ProfileRepository;
import io.softserve.goadventures.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfileServiceImpl implements ProfileService {



    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public User findByUsername(String username) {


        User user = profileRepository.findByUsername(username);


        return user;
    }
}
