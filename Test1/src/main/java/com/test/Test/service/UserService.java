package com.test.Test.service;

import com.test.Test.model.User;

public interface UserService {

     void saveUser(User user);
    String hashPassword(String password);
    User findUserByEmail(String email);
}
