package io.github.vdubois.model;

import lombok.Data;

import java.util.Collection;

/**
 * Created by vdubois on 30/12/16.
 */
@Data
public class UserDTO {

    private int id;

    private String email;

    private String password;

    private Collection<Integer> roleIds;
}
