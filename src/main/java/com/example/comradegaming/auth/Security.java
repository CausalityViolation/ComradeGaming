package com.example.comradegaming.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Security(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/login", "/users/signup", "/refresh/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/products/**", "/users/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/products/information/**", "/products", "/products/{\\\\d+}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");

        http.authorizeRequests().antMatchers(GET, "/users/forsale/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PATCH, "/users/update/**").hasAnyAuthority("ROLE_ADMIN");

        //Buy och sell fixas i front-end där varje user bara kan köpa till sig själv
        http.authorizeRequests().antMatchers(PATCH, "/users/buy/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PATCH, "/users/forsale/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PATCH, "/users/admin/**").hasAnyAuthority("ROLE_ADMIN");

        http.authorizeRequests().antMatchers(POST, "/products/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PATCH, "/products/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/products/**", "/users/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




/*
    private final Details details;


    public Security(Details details, UserDetailsService userDetailsService) {
        this.details = details;
        this.userDetailsService = userDetailsService;
    }



    @Bean
    public DaoAuthenticationProvider auth() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(details);
        provider.setPasswordEncoder(pwEncoder());
        return provider;
    }



    /*
    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                //tror verkligen inte att denna borde vara disabled EGENTLIGEN LOL NEJ
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/users/signup").permitAll()
                .antMatchers("/commissar/", "/stats","/users", "/users/{\\\\d+}", "/users/admin/create/{\\\\d+}", "/users/delete/{\\\\d+}"
                        , "/users/update/{\\\\d+}/{\\\\d+}", "/products/add/digitalgame", "/products/add/clothing"
                        , "/products/add/movie", "/products/add/product", "/products/delete/{\\\\d+}").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                //Endast aktiverad så vi kan testa skit via Insomnia! Annars kör vi formLogin tillsvidare
                .httpBasic();
        //.formLogin();

    }

 */

}
