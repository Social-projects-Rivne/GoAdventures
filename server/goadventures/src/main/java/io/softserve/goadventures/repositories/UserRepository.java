package io.softserve.goadventures.repositories;

import io.softserve.goadventures.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
    User findByUsername(String userName);
    boolean existsByEmail(String email); //TODO I believe that you do not need this method
    Long countByFullname(String name);
    User findUserById(Integer id);
}
