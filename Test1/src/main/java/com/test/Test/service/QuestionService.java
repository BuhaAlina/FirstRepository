package com.test.Test.service;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Question;

import java.util.List;

public interface QuestionService {

    void  saveQuestion(Question question);
    Question findQuestionByName(String name);
    Question findQuestionById(int id);
    List<ResponseObject> findQuestionByChapterId(int id);

}
