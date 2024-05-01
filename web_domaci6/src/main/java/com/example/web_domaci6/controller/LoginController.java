package com.example.web_domaci6.controller;

import com.example.web_domaci6.entities.User;
import com.example.web_domaci6.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/login")
public class LoginController {

    @Inject
    private UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid User user) {
        Map<String, String> response = new HashMap<>();
        String jwt = userService.login(user.getUsername(), user.getPassword());

        if (jwt == null) {
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);
        return Response.ok(response).build();
    }
}
