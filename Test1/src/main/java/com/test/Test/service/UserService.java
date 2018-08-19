package com.test.Test.service;

import com.test.Test.model.User;

import java.util.List;

public interface UserService {

     void saveUser(User user);
    String hashPassword(String password);
    User findUserByEmail(String email);
    User  findUserByResetToken(String token);
   List<User> findAllUser();
}
