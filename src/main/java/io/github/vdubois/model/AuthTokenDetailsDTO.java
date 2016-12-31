package io.github.vdubois.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by vdubois on 30/12/16.
 */
@Data
public class AuthTokenDetailsDTO {

    private String userId;

    private String email;

    private List<String> roleNames;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date expirationDate;
}
