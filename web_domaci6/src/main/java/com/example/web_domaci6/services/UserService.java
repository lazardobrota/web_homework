package com.example.web_domaci6.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.web_domaci6.entities.User;
import com.example.web_domaci6.repo.IUserRepository;

import javax.inject.Inject;
import java.util.Date;

public class UserService {

    @Inject
    IUserRepository userRepository;

    private static final Algorithm algorithm = Algorithm.HMAC256("help");

    public String login(String username, String password) {

        User user = this.userRepository.getUser(username);
        if (user == null || !user.getPassword().equals(password))
            return null;

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000);

        //subject must be unique
        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(username)
                .sign(algorithm);
    }

    public boolean isAuthorized(String token) throws Exception{
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token); //throws exception

        String username = decodedJWT.getSubject();

        return this.userRepository.getUser(username) != null;
    }
}
