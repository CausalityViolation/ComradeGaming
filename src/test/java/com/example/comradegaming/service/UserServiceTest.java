package com.example.comradegaming.service;

import com.example.comradegaming.entities.User;
import com.example.comradegaming.repo.ProductRepo;
import com.example.comradegaming.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo mockUserRepo;
    @Mock
    private ProductRepo mockProductRepo;
    @Mock
    private BCryptPasswordEncoder pwEncoder;
    @Mock
    private ProductService productService;

    private UserService testService;


    @BeforeEach
    void setUp() {
        testService = new UserService(mockUserRepo, mockProductRepo, productService, pwEncoder);
    }

    @Test
    void shouldFindAllUsers() {
        //when
        testService.findAll();
        //then

        Mockito.verify(mockUserRepo).findAll();
    }

    @Test
    void shouldAddUser() {

        //given
        User uffe = new User(
                "Uffe", "123");

        //when
        testService.add(uffe);

        //then

        //Fångar värdena och sparar dom innan dom skickas in till servicen
        ArgumentCaptor<User> userArgCapture = ArgumentCaptor.forClass(User.class);
        Mockito.verify(mockUserRepo).save(userArgCapture.capture());

        //Sparar ned de fångade värdena
        User capturedUser = userArgCapture.getValue();

        assertThat(capturedUser).isEqualTo(uffe);

    }

}
