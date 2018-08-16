package com.test.Test.model;


import com.test.Test.helper.ResponseObject;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role", schema = "public")
public class Role implements ResponseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
     int id;

    @Column(name="name")
    String name;
}
