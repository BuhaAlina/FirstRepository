package com.test.Test.model;


import com.test.Test.helper.ResponseObject;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Data
@Entity
@Table(name="user", schema="public")
public class User implements ResponseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    long id;

    @Column(name = "email")
    String email;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")

    String lastName;

    @Column(name = "password")
    //@Size(min=5)
    String password;

    @Column(name = "active") //set default value to 1
            int active = 1;

    @Column(name = "reset_token")
    String resetToken;


    @Column(name="point")
    int point=0;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;



}
