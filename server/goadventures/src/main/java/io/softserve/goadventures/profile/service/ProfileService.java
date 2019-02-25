package io.softserve.goadventures.profile.service;

import io.softserve.goadventures.user.model.User;


public interface ProfileService {

    User findByUsername(String username);

    User findById(int id);
}
