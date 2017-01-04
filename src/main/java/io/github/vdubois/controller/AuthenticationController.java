package io.github.vdubois.controller;

import io.github.vdubois.model.AuthTokenDTO;
import io.github.vdubois.model.AuthTokenDetailsDTO;
import io.github.vdubois.model.AuthenticationDTO;
import io.github.vdubois.model.RoleDTO;
import io.github.vdubois.model.UserDTO;
import io.github.vdubois.service.JsonWebTokenService;
import io.github.vdubois.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vdubois on 30/12/16.
 */
@RestController
public class AuthenticationController {

    private UserManagementService userManagementService;

    private JsonWebTokenService jsonWebTokenService;

    @Autowired
    public AuthenticationController(UserManagementService userManagementService, JsonWebTokenService jsonWebTokenService) {
        this.userManagementService = userManagementService;
        this.jsonWebTokenService = jsonWebTokenService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public AuthTokenDTO authenticate(@RequestBody AuthenticationDTO authenticationDTO) {
        AuthTokenDTO authToken = null;

        // Authenticate the user
        UserDTO userDTO = userManagementService.authenticateUser(
                authenticationDTO.getEmail(), authenticationDTO.getPassword());
        // TODO If authentication fails, return an unauthorized error code

        if (userDTO != null) {

            Collection<RoleDTO> roles = userManagementService.findAllRolesForUser(userDTO.getId());
            List<String> roleNames = roles.stream()
                    .map(RoleDTO::getName)
                    .collect(Collectors.toList());

            // Build the AuthTokenDetailsDTO
            AuthTokenDetailsDTO authTokenDetailsDTO = new AuthTokenDetailsDTO();
            authTokenDetailsDTO.setUserId("" + userDTO.getId());
            authTokenDetailsDTO.setEmail(userDTO.getEmail());
            authTokenDetailsDTO.setRoleNames(roleNames);
            authTokenDetailsDTO.setExpirationDate(buildExpirationDate());

            // Create auth token
            String jwt = jsonWebTokenService.createJsonWebToken(authTokenDetailsDTO);
            authToken = new AuthTokenDTO();
            authToken.setToken(jwt);
        }

        return authToken;
    }

    private Date buildExpirationDate() {
        return Date.from(Instant.now().plusSeconds(3600));
    }
}
