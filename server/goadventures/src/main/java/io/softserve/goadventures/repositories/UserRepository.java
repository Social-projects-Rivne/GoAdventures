package io.softserve.goadventures.repositories;

import io.softserve.goadventures.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);

    User findUserById(Integer id);
}
