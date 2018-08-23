package com.test.Test.service;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Answer;

import java.util.List;

public interface AnswerService {

    public void saveAnswer(Answer answer);
    public Answer findAnswerById(int id);
    Answer findAnswerByContent(String content);
    List<ResponseObject> findAnswerByQuestionId(int id);
}
