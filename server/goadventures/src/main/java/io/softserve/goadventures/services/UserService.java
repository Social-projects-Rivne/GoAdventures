package io.softserve.goadventures.services;

import io.softserve.goadventures.dto.UserAuthDto;
import io.softserve.goadventures.enums.UserStatus;
import io.softserve.goadventures.errors.UserNotFoundException;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

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

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User confirmUser(String email) {
        User user = userRepository.findByEmail(email);
        user.setStatusId(UserStatus.ACTIVE.getUserStatus());
        userRepository.save(user);

        return user;
    }

    public User addUser(UserAuthDto userAuthDto) {
        User user = new User();
        BeanUtils.copyProperties(userAuthDto, user);

        if (checkingEmail(user.getEmail())) {
            user.setStatusId(UserStatus.PENDING.getUserStatus());
            user.setUsername(user.getEmail().split("@")[0]);
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            userRepository.save(user);
        }
        return user;
    }

    public UserStatus singIn(String email, String password) {

        if (checkingEmail(email)) {
            return null;
        } else {
            User user = userRepository.findByEmail(email);

            if (BCrypt.checkpw(password, user.getPassword())) {
                if (user.getStatusId() == UserStatus.PENDING.getUserStatus()) {
                    return UserStatus.PENDING;
                }
                if (user.getStatusId() == UserStatus.BANNED.getUserStatus()) {
                    return UserStatus.BANNED;
                }
                if (user.getStatusId() == UserStatus.DELETED.getUserStatus()) {
                    return UserStatus.DELETED;
                }
                user.setStatusId(UserStatus.ACTIVE.getUserStatus());
                userRepository.save(user);

                return UserStatus.LOGGING;
            } else {
                return UserStatus.WRONGPASSWORD;
            }
        }
    }

    public void singOut(String email) {
        User user = userRepository.findByEmail(email);
        user.setStatusId(UserStatus.UNACTIVE.getUserStatus());
        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean checkingEmail(String email){
        return userRepository.findByEmail(email) == null;
    }
}
