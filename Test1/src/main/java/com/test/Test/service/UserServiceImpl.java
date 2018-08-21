package com.test.Test.service;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Role;
import com.test.Test.model.User;
import com.test.Test.repository.RoleRepository;
import com.test.Test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.*;

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

        Role userRole = roleRepository.findByName("USER");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);

    }
    @Override
    public void saveAdmin(User user) {
        //user.setPassword(hashPassword(user.getPassword()));
        Role userRole = roleRepository.findByName("ADMIN");
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

      return users;
    }

    public boolean tokenIsValid(String token) {
        if(token==null){
            return false;
        }else{
        User dbUser = findUserByResetToken(token);
        if (dbUser == null)
            return false;}
        return true;
    }

    public  boolean isAdmin(String token){

   User user= findUserByResetToken(token);
        boolean control = false;

   if (user==null){return false;}
       else {

       for (Role role : user.getRoles()) {
           control = Objects.equals(role.getName(), "ADMIN");
       }
   } return  control;

    }

}
