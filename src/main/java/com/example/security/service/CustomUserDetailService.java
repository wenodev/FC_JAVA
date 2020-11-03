package com.example.security.service;

import com.example.security.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;


    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findById(Long.valueOf(userPk)).orElseThrow(Exception::new);
    }
}
