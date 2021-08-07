package com.example.genson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class GensonApplication {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(GensonApplication.class, args);
    }

    @PostConstruct
    public void addUsers() {
        // Add a few default users to check that fetching and serialization work
        UserDto userDto = new UserDto(1, "Tommy Greenfield", "tommy.greenfield@email.com");
        UserDto userDto2 = new UserDto(2, "Sally Cobweb", "sally.cobweb@email.com");

        userService.addUser(userDto);
        userService.addUser(userDto2);
    }
}
