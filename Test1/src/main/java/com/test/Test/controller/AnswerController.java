package com.test.Test.controller;

import com.test.Test.helper.InternshipResponse;
import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Answer;
import com.test.Test.model.User;
import com.test.Test.model.Question;
import com.test.Test.service.AnswerService;
import com.test.Test.service.ChapterService;
import com.test.Test.service.QuestionService;
import com.test.Test.service.UserService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    //POST -- /create/answer--create answer by admin
    @RequestMapping(value = "/create/answer", method = RequestMethod.POST)
    public ResponseEntity createAnswer (@RequestBody Answer answer, @RequestHeader("token") final String token) {
        if (userService.isAdmin(token) && userService.tokenIsValid(token)) {
            if (answerService.findAnswerByContent(answer.getContent()) == null) {
                answerService.saveAnswer(answer);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Answer created successfully!", Arrays.asList(answer)));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "This Answer exist please create another Answer!", null)); }
        }return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));

    }


    //PUT --/answer--update answer by id answer
   @RequestMapping(value = "/answer", method = RequestMethod.PUT)
    public ResponseEntity updateQuestion(@RequestParam("id") final int id, @RequestBody Answer answer, @RequestHeader("token") final String token) {
        if (userService.isAdmin(token) && userService.tokenIsValid(token)) {
            Answer dbAnswer = answerService.findAnswerById(id);
            if (dbAnswer != null) {
                dbAnswer.setContent(answer.getContent());
                //dbAnswer.setCorrect(answer.get);
                answerService.saveAnswer(dbAnswer);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Answer updated successfully!", Arrays.asList(dbAnswer)));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, " This answer not found!", null)); }
        }return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));

    }



    //GET   /answer - get all the answer by a question Id
   @RequestMapping(value = "/answers", method = RequestMethod.GET)
    public ResponseEntity getAnswers (@RequestHeader ("token") final String token,@RequestParam("id") final int id) {
        if (userService.tokenIsValid(token)) {
            Answer dbAnswer = new Answer();
            dbAnswer.setQuestion(questionService.findQuestionById(id));
            if (dbAnswer.getQuestion() != null) {

                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Found answers !",answerService.findAnswerByQuestionId(id)));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Answer not found!", null)); }

        } return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
    }

   // POST --/answer/point --complete column  point from user tabel (point=number of true answer)---in progress
    @RequestMapping(value = "/answer/point", method = RequestMethod.POST)
    public ResponseEntity answerPoint (@RequestBody List<Answer> answers, @RequestHeader("reset_token") final String token) {

        int point=0;
        List<ResponseObject> corectAnswer = new ArrayList<ResponseObject>();
        for (int i = 0; i < answers.size(); i++) {
            //answer=find answer in DB byId
            //answers.get(x);=answer object by x position
            Answer answer=answerService.findAnswerById((answers.get(i)).getId());

            if(BooleanUtils.isTrue(answer.isCorrect())&& BooleanUtils.isTrue(answers.get(i).isCorrect())){
                point=point+1;
                corectAnswer.add(answers.get(i));

            }
        }
        User user =userService.findUserByResetToken(token);
        user.setPoint(point+ user.getPoint());//user.setPoint(point+user.getPoint());
        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, " You have:"+ point +" correct answer",corectAnswer));
    }
}
