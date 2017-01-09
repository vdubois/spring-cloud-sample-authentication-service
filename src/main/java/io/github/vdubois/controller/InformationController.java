package io.github.vdubois.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by vdubois on 08/01/17.
 */
@RestController
public class InformationController {

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/service-info")
    public ResponseEntity<String> info() throws UnknownHostException {
        return new ResponseEntity<>(appName + " running on " + InetAddress.getLocalHost().getHostAddress(), HttpStatus.OK);
    }
}
