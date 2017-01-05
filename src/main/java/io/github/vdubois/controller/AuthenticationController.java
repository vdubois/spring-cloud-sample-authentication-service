package io.github.vdubois.controller;

import io.github.vdubois.model.AuthTokenDTO;
import io.github.vdubois.model.AuthTokenDetailsDTO;
import io.github.vdubois.model.AuthenticationDTO;
import io.github.vdubois.model.Role;
import io.github.vdubois.model.User;
import io.github.vdubois.service.JsonWebTokenService;
import io.github.vdubois.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
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
    public ResponseEntity<AuthTokenDTO> authenticate(@RequestBody AuthenticationDTO authenticationDTO) {
        User user = userManagementService.authenticateUser(
                authenticationDTO.getEmail(), authenticationDTO.getPassword());
        if (user != null) {
            return new ResponseEntity<>(buildAuthenticationTokenFromUser(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private AuthTokenDTO buildAuthenticationTokenFromUser(User user) {
        AuthTokenDTO authToken;List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        // Build the AuthTokenDetailsDTO
        AuthTokenDetailsDTO authTokenDetailsDTO = new AuthTokenDetailsDTO();
        authTokenDetailsDTO.setUserId("" + user.getId());
        authTokenDetailsDTO.setEmail(user.getEmail());
        authTokenDetailsDTO.setRoleNames(roleNames);
        authTokenDetailsDTO.setExpirationDate(buildExpirationDate());

        // Create auth token
        String jwt = jsonWebTokenService.createJsonWebToken(authTokenDetailsDTO);
        authToken = new AuthTokenDTO();
        authToken.setToken(jwt);
        return authToken;
    }

    private Date buildExpirationDate() {
        return Date.from(Instant.now().plusSeconds(3600));
    }
}
