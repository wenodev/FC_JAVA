package com.example.security.controller;

import com.example.security.domain.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {


    @PostMapping("/test")
    public void testPost(@RequestBody User resource){
        System.out.println("name : " + resource.getUid());
        System.out.println("name : " + resource.getName());
    }

  

}
