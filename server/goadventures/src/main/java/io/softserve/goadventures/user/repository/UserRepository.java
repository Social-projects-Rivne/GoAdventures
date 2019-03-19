package io.softserve.goadventures.user.repository;
import io.softserve.goadventures.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
    User findByUsername(String userName);
    boolean existsByEmail(String email);
    Long countByFullname(String name);
}
