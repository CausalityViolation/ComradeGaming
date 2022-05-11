package com.example.comradegaming.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.comradegaming.entities.Product;
import com.example.comradegaming.entities.User;
import com.example.comradegaming.exceptionHandling.CustomException;
import com.example.comradegaming.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping()
    public ResponseEntity<Iterable<User>> findAll() {
        Iterable<User> found = service.findAll();
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<User> findByName(@PathVariable String name) {
        User found = service.find(name);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PatchMapping("update/{userID}/{password}")
    public ResponseEntity<User> updatePassword(@PathVariable String password, @PathVariable long userID) {
        service.updatePassword(userID, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("buy/{userID}/{productID}")
    public ResponseEntity<User> purchase(@PathVariable Long userID, @PathVariable Long productID) {
        service.buyProduct(productID, userID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("forsale/{userID}/add")
    public ResponseEntity<User> sellProduct(@PathVariable Long userID, @RequestBody Product product) {
        service.addForSale(product, userID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("forsale/{userID}/update/{productID}")
    public ResponseEntity<Product> updateUsedItem(@PathVariable long userID, @PathVariable long productID
            , @RequestBody Product updatedItem) {
        return new ResponseEntity<>(service.updateProduct(userID, productID, updatedItem));
    }

    @DeleteMapping("forsale/{userID}/sold/{productID}")
    public ResponseEntity<User> forSaleSold(@PathVariable long userID, @PathVariable long productID) {
        service.sellForSale(userID, productID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("admin/create/{id}")
    public ResponseEntity<User> makeAdmin(@PathVariable long id) {
        service.makeAdmin(service.findById(id));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<User> add(@RequestBody User user) {
        service.add(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{name}")
    public String remove(@PathVariable String name) {

        User found = service.find(name);
        if (found == null) {
            throw new EntityNotFoundException("Could not find User with name " + name);
        } else {
            service.delete(name);

            return "Successfully removed user with name " + name;
        }
    }

    @GetMapping("forsale/{userID}")
    public Set<Product> deliverForSale(@PathVariable long userID) {
        return service.deliverForSale(userID);
    }

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            try {

                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();

                User user = service.find(username);
                Collection<User> userRoles = new ArrayList<>();
                userRoles.add(user);

                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", userRoles.stream().map(User::deliverRole).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


            } catch (Exception exception) {

                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", "Looks like you're trying to access part of the application with an invalid or expired token!");
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new CustomException("Refresh token is missing!", HttpStatus.NOT_FOUND);
        }
    }
}
