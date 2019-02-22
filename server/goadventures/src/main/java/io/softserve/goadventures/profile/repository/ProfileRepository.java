package io.softserve.goadventures.profile.repository;

import io.softserve.goadventures.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    //User findByActivationCode(String code);
}