package com.example.security.domain;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenFindByUid_thenReturnUser(){

        String uid = "juwon@gmail.com";
        String name = "juwon";

        //given
        userRepository.save(User.builder()
                .uid(uid)
                .name(name)
                .password(passwordEncoder.encode("1234"))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        //when
        Optional<User> user = userRepository.findByUid(uid);

        assertNotNull(user);
        assertTrue(user.isPresent());

        assertEquals(user.get().getName(), name);
        assertThat(user.get().getName(), is(name));

    }

}