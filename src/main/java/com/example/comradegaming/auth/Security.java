package com.example.comradegaming.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    private final Details details;

    public Security(Details details) {
        this.details = details;
    }

    @Bean
    public BCryptPasswordEncoder pwEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider auth() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(details);
        provider.setPasswordEncoder(pwEncoder());
        return provider;
    }


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

}
