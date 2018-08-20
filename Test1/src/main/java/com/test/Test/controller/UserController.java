package com.test.Test.controller;

import com.test.Test.helper.InternshipResponse;
import com.test.Test.helper.ResponseObject;

import com.test.Test.helper.VerifyEmail;
import com.test.Test.model.Role;
import com.test.Test.model.User;
import com.test.Test.repository.RoleRepository;
import com.test.Test.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;





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
        //User dbUser = userService.findUserByResetToken(token);
        //if(dbUser!= null)
        if (userService.tokenIsValid(token)){
           List<ResponseObject> listUsers= new ArrayList<ResponseObject>();
           listUsers.addAll(userService.findAllUser());
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "List of users: ", listUsers));
        }
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied", null));}

    //GET /user -get one user by email
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity user (@RequestParam("email") final String email, @RequestHeader ("reset_token") final String token) {
        if (userService.tokenIsValid(token)) {
         User user =userService.findUserByEmail(email);
            if (user != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Success", Arrays.asList(user)));
            } else {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InternshipResponse(false, "Please provide a valid id.", null));}
        }
        return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
    }

    //PUT --/create/admin --create Admin account by admin *********
    @RequestMapping(value="/create/admin",method = RequestMethod.PUT)
    public ResponseEntity createAdmin(@RequestHeader("reset_token") final String token,@RequestBody User admin) {

        User userAdmin = userService.findUserByResetToken(token);
        if (userService. isAdmin(userAdmin)) {
            String email = admin.getEmail();
            if (VerifyEmail.emailIsValid(email)) {
                if (userService.findUserByEmail(email) == null) {
                    admin.setPassword(userService.hashPassword(admin.getPassword()));
                    Role userRole = roleRepository.findByName("ADMIN");
                    admin.setRoles(new HashSet<>(Collections.singletonList(userRole)));
                    userService.saveUser(admin);
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Admin [" + admin.getEmail() + "] created successfully!", Arrays.asList(admin)));

                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The selected email already belongs to an account. Please use a different one!", null)); }

                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The supplied email address is not valid!", null)); }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
    }

    //POST /reset -password
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ResponseEntity resetPassword (@RequestBody User user) {
      User dbUser =userService.findUserByEmail(user.getEmail());
      if(dbUser!=null) {
          dbUser.setPassword(userService.hashPassword(user.getPassword()));
          userService.saveUser(dbUser);
          return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Password Changed!", Arrays.asList(dbUser)));
      } else {
          return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided email address doesn't belong to any existing account", null));
      }
    }
    //PUT /user--update user
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity userUpdate (@RequestHeader("reset_token") final String token,@RequestBody User user) {
        User dbUser = userService.findUserByResetToken(token);
        if( dbUser != null){
            dbUser.setLastName(user.getLastName());
            dbUser.setFirstName(user.getFirstName());
            dbUser.setEmail(user.getEmail());
            userService.saveUser(dbUser);
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Successfull update!", Arrays.asList(dbUser)));
        }else
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "No session token available!", null));}


    }
    //DELETE --/delete --user soft-delete
    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public ResponseEntity userDelete (@RequestHeader("reset_token") final String token,@RequestBody User user) {
    User userAdmin = userService.findUserByResetToken(token);
        if (userService. isAdmin(userAdmin)) {
            User dbUser = userService.findUserByEmail(user.getEmail());
            if(dbUser!=null){
                dbUser.setActive(0);
                dbUser.setResetToken(null);
                userService.saveUser(dbUser);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "User  was deleted successfully!", Arrays.asList(dbUser)));
            }else{ return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided email doesn't belong to any existing user account.", null));}

        } return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
    }


}
