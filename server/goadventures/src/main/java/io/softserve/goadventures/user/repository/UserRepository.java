package io.softserve.goadventures.user.repository;

import org.springframework.data.repository.CrudRepository;
import io.softserve.goadventures.user.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmailIgnoreCase(String email);
}
