package io.github.vdubois.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Created by vdubois on 30/12/16.
 */
@Configuration
public class WebSecurityConfiguration extends JsonWebTokenSecurityConfiguration {

    @Override
    protected void setupAuthorization(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                // allow anonymous access to /authenticate endpoint
                .antMatchers("/authenticate").permitAll()

                // authenticate all other requests
                .anyRequest().authenticated();
    }
}
