package io.softserve.goadventures.user.service;

import io.softserve.goadventures.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.softserve.goadventures.user.model.User;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id){
        return userRepository.findUserById(id);
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        if(userRepository.existsByEmail(email)){
            return userRepository.findByEmail(email);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void addUser(User newUser) {userRepository.save(newUser);}

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
