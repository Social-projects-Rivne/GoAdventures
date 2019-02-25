package io.softserve.goadventures.profile.service;

import io.softserve.goadventures.profile.repository.ProfileRepository;
import io.softserve.goadventures.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {



    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public User findByUsername(String username) {

        User user = profileRepository.findByUsername(username);

        return user;
    }

    @Override
    public User findById(int id) {
        return profileRepository.findById(id);
    }


}
