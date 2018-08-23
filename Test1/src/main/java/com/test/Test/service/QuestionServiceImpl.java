package com.test.Test.service;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Course;
import com.test.Test.model.Question;
import com.test.Test.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void  saveQuestion(Question question){
        questionRepository.save(question);
    }

    @Override
    public Question findQuestionByName(String name){
        return questionRepository.findByName(name);
    }

    @Override
    public Question findQuestionById(int id){
        return questionRepository.findById(id);
    }
    @Override
    public List<ResponseObject> findQuestionByChapterId(int id){
        return  questionRepository.findByChapterId(id);
    }

}
