package com.example.security.controller;

import com.example.security.domain.User;
import com.example.security.domain.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

    @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회한다")
    @GetMapping("/user")
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
    @PostMapping("/user")
    public User save(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
                     @ApiParam(value = "회원이름", required = true) @RequestParam String name) {


        System.out.println("uid : " + uid);
        System.out.println("name : " + name);

        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return userRepository.save(user);
    }
}