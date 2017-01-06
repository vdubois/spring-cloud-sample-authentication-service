package io.github.vdubois.controller;

import io.github.vdubois.model.AuthTokenDetailsDTO;
import io.github.vdubois.model.User;
import io.github.vdubois.service.JsonWebTokenService;
import io.github.vdubois.service.UserManagementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vdubois on 06/01/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserManagementService userManagementService;

    @MockBean
    private JsonWebTokenService jsonWebTokenService;

    public User mockUser() {
        User user = new User();
        user.setEmail("johndoe@gmail.com");
        return user;
    }

    @Test
    public void should_fail_to_authenticate_if_no_authentication_data_provided() throws Exception {

        mockMvc.perform(post("/authenticate")
                .content("{}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));

    }

    @Test
    public void should_fail_to_authenticate_if_incorrect_authentication_data_provided() throws Exception {

        given(userManagementService.authenticateUser("johndoe@gmail.com", "pass")).willReturn(mockUser());

        mockMvc.perform(post("/authenticate")
                .content("{\"email\":\"johndoe@gmail.com\", \"pass\": \"pass\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));
    }

    @Test
    public void should_fail_to_authenticate_if_bad_credentials_data_provided() throws Exception {

        given(userManagementService.authenticateUser("johndoe@gmail.com", "pass")).willReturn(mockUser());

        mockMvc.perform(post("/authenticate")
                .content("{\"email\":\"johndoe@gmail.com\", \"password\": \"password\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));
    }

    @Test
    public void should_success_to_authenticate_if_correct_authentication_data_provided() throws Exception {

        given(userManagementService.authenticateUser("johndoe@gmail.com", "pass")).willReturn(mockUser());
        given(jsonWebTokenService.createJsonWebToken(Matchers.any(AuthTokenDetailsDTO.class))).willReturn("mySuperToken");

        mockMvc.perform(post("/authenticate")
                .content("{\"email\":\"johndoe@gmail.com\", \"password\": \"pass\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("token", is("mySuperToken")));
    }

}
