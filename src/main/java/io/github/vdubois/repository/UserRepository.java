package io.github.vdubois.repository;

import io.github.vdubois.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vdubois on 04/01/17.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByEmailAndPassword(String email, String password);
}
