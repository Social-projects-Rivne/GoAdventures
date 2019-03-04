package io.softserve.goadventures.user.service;

import io.softserve.goadventures.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import io.softserve.goadventures.user.model.User;

@Service
public class UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> getUserById(int id){
     return userRepository.findById(id);
  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public void updateUser(User user) {
    userRepository.save(user);
  }
}
