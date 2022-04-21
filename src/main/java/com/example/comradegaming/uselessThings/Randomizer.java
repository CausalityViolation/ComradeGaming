package com.example.comradegaming.uselessThings;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Randomizer {

    public static class AuthController {
        /*
        Man skulle kunna koda in sökning för fritext också genom att dela upp productDescription i separata ord.
        Detta skulle sedan kunna matchas mot de ord som användaren matar in med en felmarginal på typ 2 tecken.
        Resultaten rankas sedan i första hand efter antalet matchande ord och i andra hand efter vilket resultat som
        har minst ICKE matchande ord,
         */
        @GetMapping
        public String homePage() {
            return "Welcome to the home page";
        }

        @GetMapping("userportal")
        public String userPortal() {
            return "Welcome, you are now authenticated";
        }
    }
}
