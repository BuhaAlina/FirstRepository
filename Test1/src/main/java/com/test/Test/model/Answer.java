package com.test.Test.model;


import com.test.Test.helper.ResponseObject;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "answer", schema = "public")
public class Answer implements ResponseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;

    @Column(name = "content")
    String content;

    @Column(name = "is_correct")
    boolean isCorrect;


   /*public boolean isIs_correct() {
        return isCorrect;
   }*/

    /*public void setIs_correct(boolean is_correct) {
        this.is_correct = is_correct;
    }*/

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", referencedColumnName="id")
    Question question;
}
