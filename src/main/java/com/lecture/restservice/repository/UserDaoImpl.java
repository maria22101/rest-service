package com.lecture.restservice.repository;

import com.lecture.restservice.model.Role;
import com.lecture.restservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private List<User> users = new ArrayList<User>() {{
        add(new User(1, "user", "pass1", Role.USER));
        add(new User(2, "admin", "pass2", Role.ADMIN));
    }};

    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findAny()
                .orElseThrow(() -> new RuntimeException("No such user"));
    }
}
