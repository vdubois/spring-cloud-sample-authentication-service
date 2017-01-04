package io.github.vdubois.service;

import io.github.vdubois.model.RoleDTO;
import io.github.vdubois.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by vdubois on 30/12/16.
 */
@Service
public class UserManagementService {

    private static final Collection<Integer> SHUNT_ROLES = Arrays.asList(1, 2);

    public UserDTO authenticateUser(String email, String password) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        userDTO.setRoleIds(SHUNT_ROLES); // FIXME implement
        return userDTO;
    }

    public Collection<RoleDTO> findAllRolesForUser(int userId) {
        Collection<RoleDTO> roleDTOs = new HashSet<>();
        //User user = userRepository.findOne(id);
        //if (user != null) {
//            roles = user.getRoles();
//        }
//        Collection<RoleDTO> roleDTOs = convertRolesToRoleDTOs(roles);
        return roleDTOs;
    }
}
