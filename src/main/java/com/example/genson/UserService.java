package com.example.genson;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<UserDto> users = new ArrayList<>();

    public UserDto getUser(Integer id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElseThrow(RuntimeException::new);
    }

    public List<UserDto> getAllUsers() {
        return users;
    }

    public void addUser(UserDto userDto) {
        users.add(userDto);
    }
}
