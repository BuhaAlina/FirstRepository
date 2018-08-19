package com.test.Test.service;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Role;
import com.test.Test.model.User;
import com.test.Test.repository.RoleRepository;
import com.test.Test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    @Override
    public void saveUser(User user) {
        //user.setPassword(hashPassword(user.getPassword()));
        Role userRole = roleRepository.findByName("USER");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);

    }

    @Override
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public User  findUserByResetToken(String token){return  userRepository.findByResetToken(token);}

    @Override
    public List<User> findAllUser( ){
        List<User> users =  userRepository.findAll();


       /* List<ResponseObject> listUsers= new ArrayList<ResponseObject>( );
        listUsers.add(users);*/
        //userRepository.findAll();

      return users;
    }
}
