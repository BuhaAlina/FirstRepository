package com.test.Test.service;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Answer;
import com.test.Test.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }
    @Override
    public List<ResponseObject> findAnswerByQuestionId(int id)
    {
        return answerRepository.findByQuestionId(id);
    }

    @Override
    public Answer findAnswerById(int id)
    {
        return answerRepository.findById(id);
    }

    @Override
    public Answer findAnswerByContent(String content)
    {
        return answerRepository.findByContent(content);
    }

}
