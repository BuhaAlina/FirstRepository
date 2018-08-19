package com.test.Test.controller;

import com.test.Test.helper.InternshipResponse;
import com.test.Test.helper.ResponseObject;
import com.test.Test.helper.VerifyEmail;
import com.test.Test.model.User;
import com.test.Test.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;



    //user registration --/create/user *************
    @RequestMapping(value = "/create/user", method = RequestMethod.POST)
    public ResponseEntity createNewUser (@RequestBody User user){
        String email =user.getEmail();
        if(VerifyEmail.emailIsValid(email)){
            if(userService.findUserByEmail(user.getEmail()) == null) {
                user.setPassword(userService.hashPassword(user.getPassword()));
                userService.saveUser(user);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "User [" + user.getEmail() + "] created successfully!", Arrays.asList(user)));

    } else{return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The supplied email address is not valid!", null));}
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The selected email already belongs to an account. Please use a different one!", null));}
    }

    //login user --/login*********
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginUser (@RequestBody User user){
        User dbUser=userService.findUserByEmail(user.getEmail());
        String resetToken= RandomStringUtils.randomAlphabetic(6);
        if((dbUser != null)&&(BCrypt.checkpw(user.getPassword(), dbUser.getPassword()))) {
            if(dbUser.getActive()==1){
            if (dbUser.getResetToken()==null) {
                dbUser.setResetToken(resetToken);
                userService.saveUser(dbUser);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, dbUser.getResetToken(), Arrays.asList(dbUser)));
            }else{return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Login user", null));}

        }else { return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "User is not active!", null));}

        }
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided email and password  doesn't belong to any existing account", null));
    }

    //logout user --/logout*********
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(@RequestHeader("reset_token") final String token) {

        User dbUser = userService.findUserByResetToken(token);
        if( dbUser != null){
            dbUser.setResetToken(null);
            userService.saveUser(dbUser);
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Successfull Logout!", Arrays.asList(dbUser)));
        }else
        {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "No session token available!", null));}

    }

    //get all users --/users*********
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity users (@RequestHeader ("reset_token") final String token) {
        User dbUser = userService.findUserByResetToken(token);
        if (dbUser != null) {
           List<ResponseObject> listUsers= new ArrayList<ResponseObject>();
           listUsers.addAll(userService.findAllUser());
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "List of users: ", listUsers));
        }
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied", null));}

    //create Admin --/create/admin*********
    //PUT-- /create/admin--create admin account to admin
    //user delete
    //POST /reset -password
    //PUT /user--update user
    //GET /user -get one user by email
}
