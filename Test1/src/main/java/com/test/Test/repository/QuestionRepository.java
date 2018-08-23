package com.test.Test.repository;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    Question findByName(String name);
    Question findById(int id);
    List<ResponseObject> findByChapterId(int id);


}
