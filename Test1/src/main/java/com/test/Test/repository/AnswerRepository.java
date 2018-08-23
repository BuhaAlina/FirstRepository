package com.test.Test.repository;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Answer findById(int id);
    Answer findByContent(String content);
    List<ResponseObject> findByQuestionId(int id);


}
