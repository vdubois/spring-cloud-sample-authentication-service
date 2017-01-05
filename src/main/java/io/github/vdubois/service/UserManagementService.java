package io.github.vdubois.service;

import io.github.vdubois.model.User;
import io.github.vdubois.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Created by vdubois on 30/12/16.
 */
@Service
public class UserManagementService {

    private UserRepository userRepository;

    public UserManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticateUser(String email, String password) {
        return userRepository.findOneByEmailAndPassword(email, password);
    }
}
