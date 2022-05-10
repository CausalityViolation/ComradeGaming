package com.example.comradegaming.repo;

import com.example.comradegaming.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepoTest {

    @Autowired
    private UserRepo testRepo;

    @Test
    void shouldBeAbleToFindByEmail() {
        //given

        //blir inge bcrypt här inte
        User testUser = new User("Ultraman", "123");

        testRepo.save(testUser);

        //when

        User foundUser = testRepo.findByUsername(testUser.getUsername());

        //then

        assertThat(foundUser).isNotNull();
    }

}