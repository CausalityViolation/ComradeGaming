package com.example.comradegaming.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Finns fördefinerade Logout och Login endpoints på localhost:8080/logout och /login

@RestController
public class AuthController {

    //När man är inloggad får man denna fina i ansiktet
    @GetMapping()
    public String homePage() {
        return "Welcome, COMRADE!";
    }

    @GetMapping("commissar")
    public String approved() {
        return "The commissars deem you worthy comrade";
    }

}
