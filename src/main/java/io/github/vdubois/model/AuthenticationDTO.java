package io.github.vdubois.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created by vdubois on 30/12/16.
 */
@Data
public class AuthenticationDTO {

    private String email;

    @JsonIgnore
    private String password;
}
