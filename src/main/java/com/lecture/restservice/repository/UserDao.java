package com.lecture.restservice.repository;

import com.lecture.restservice.model.User;

public interface UserDao {
    User findByUsername(String username);
}
