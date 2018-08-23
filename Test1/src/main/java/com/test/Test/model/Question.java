package com.test.Test.model;


import com.test.Test.helper.ResponseObject;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "question", schema = "public")
public class Question implements ResponseObject
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
     int id;

    @Column(name = "name")
    String name;

    @Column(name = "content")
    String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chapter_id", referencedColumnName="id")
     Chapter chapter;

 }
