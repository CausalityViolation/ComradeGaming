package com.example.comradegaming.auth;

import com.example.comradegaming.entities.User;
import com.example.comradegaming.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class Details implements UserDetailsService {


    private final UserRepo userRepo;

    public Details(UserRepo repository) {
        this.userRepo = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Unable to find user with username: " + username);
        }
        return new UserPrincipal(user);
    }

}
