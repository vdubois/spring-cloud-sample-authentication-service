package io.github.vdubois.jwt;

import io.github.vdubois.model.AuthTokenDetailsDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * Created by vdubois on 30/12/16.
 */
@Log
@Service
public class JSONWebTokenProducerService {

    private SignatureAlgorithm signatureAlgorithm;
    private Key secretKey;

    public JSONWebTokenProducerService() {

        // THIS IS NOT A SECURE PRACTICE!
        // For simplicity, we are storing a static key here.
        // Ideally, in a microservices environment, this key would kept on a
        // config server.
        signatureAlgorithm = SignatureAlgorithm.HS512;
        String encodedKey = "L7A/6zARSkK1j7Vd5SDD9pSSqZlqF7mAhiOgRbgv9Smce6tf4cJnvKOjtKPxNNnWQj+2lQEScm3XIUjhW+YVZg==";
        secretKey = deserializeKey(encodedKey);
    }

    public String createJsonWebToken(AuthTokenDetailsDTO authTokenDetailsDTO) {
        String token = Jwts.builder().setSubject(authTokenDetailsDTO.getUserId())
                .claim("email", authTokenDetailsDTO.getEmail())
                .claim("roles", authTokenDetailsDTO.getRoleNames())
                .setExpiration(authTokenDetailsDTO.getExpirationDate())
                .signWith(getSignatureAlgorithm(), getSecretKey())
                .compact();
        return token;
    }

    private Key deserializeKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, getSignatureAlgorithm().getJcaName());
    }

    private Key getSecretKey() {
        return secretKey;
    }

    public SignatureAlgorithm getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public AuthTokenDetailsDTO parseAndValidate(String token) {
        AuthTokenDetailsDTO authTokenDetailsDTO = null;
        try {
            Claims claims = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
            String userId = claims.getSubject();
            String email = (String) claims.get("email");
            List roleNames = (List) claims.get("roles");
            Date expirationDate = claims.getExpiration();

            authTokenDetailsDTO = new AuthTokenDetailsDTO();
            authTokenDetailsDTO.setUserId(userId);
            authTokenDetailsDTO.setEmail(email);
            authTokenDetailsDTO.setRoleNames(roleNames);
            authTokenDetailsDTO.setExpirationDate(expirationDate);
        } catch (JwtException jwtException) {
            log.severe(jwtException.getMessage());
            jwtException.printStackTrace();
        }
        return authTokenDetailsDTO;
    }

    private String serializeKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}