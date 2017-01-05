package io.github.vdubois.init;

import io.github.vdubois.model.Role;
import io.github.vdubois.model.User;
import io.github.vdubois.repository.RoleRepository;
import io.github.vdubois.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by vdubois on 04/01/17.
 */
public class UsersInitializer implements CommandLineRunner {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public UsersInitializer(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        Thread.currentThread().sleep((long) (Math.random() * 10000));
        Stream.of("dubois.vct@free.fr,passw0rd,ADMIN|USER", "jean.valjean@paris.fr,c0s3tte,USER").forEach(
                tuple -> {
                    String[] userCaracteristics = tuple.split(",");
                    saveUserRoles(userCaracteristics[2]);
                    User user = new User();
                    user.setEmail(userCaracteristics[0]);
                    user.setPassword(userCaracteristics[1]);
                    user.setRoles(roleRepository.findByName(userCaracteristics[2].split("\\|")));
                    userRepository.save(user);
                }
        );
    }

    private void saveUserRoles(String userRolesAsString) {
        String[] userRoles = userRolesAsString.split("\\|");
        Arrays.stream(userRoles).forEach(roleName -> {
            Role userRole = roleRepository.findOneByName(roleName);
            if (userRole == null) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        });
    }
}
