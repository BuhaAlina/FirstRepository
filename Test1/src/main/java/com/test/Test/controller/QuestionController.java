package com.test.Test.controller;

import com.test.Test.helper.InternshipResponse;
import com.test.Test.model.Question;
import com.test.Test.service.ChapterService;
import com.test.Test.service.QuestionService;
import com.test.Test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class QuestionController {



    @Autowired
    private ChapterService chapterService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    //POST -- /create/question--create question by admin
    @RequestMapping(value = "/create/question", method = RequestMethod.POST)
    public ResponseEntity createQuestion (@RequestBody Question question, @RequestHeader("token") final String token) {
        if (userService.isAdmin(token) && userService.tokenIsValid(token)) {
            if (questionService.findQuestionByName(question.getName()) == null) {
                questionService.saveQuestion(question);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Question created successfully!", Arrays.asList(question)));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "This question exist please create another question!", null)); }
        }return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));

    }


    //PUT --/question--update question by id question
    @RequestMapping(value = "/question", method = RequestMethod.PUT)
    public ResponseEntity updateQuestion(@RequestParam("id") final int id, @RequestBody Question question, @RequestHeader("token") final String token) {
        if (userService.isAdmin(token) && userService.tokenIsValid(token)) {
            Question dbQuestion = questionService.findQuestionById(id);
            if (dbQuestion != null) {
                dbQuestion.setContent(question.getContent());
                dbQuestion.setName(question.getName());
                questionService.saveQuestion(dbQuestion);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Question updated successfully!", Arrays.asList(dbQuestion)));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, " This Question not found!", null)); }
        }return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));

    }



    //GET   /questions - get all the question by a chapter Id
    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public ResponseEntity getQuestions (@RequestHeader ("token") final String token,@RequestParam("id") final int id) {
        if (userService.tokenIsValid(token)) {
            Question dbQuestion = new Question();
            dbQuestion.setChapter(chapterService.findChapterById(id));//chapter
            if (dbQuestion.getChapter() != null) {

                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Found questions !",questionService.findQuestionByChapterId(id)));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Question not found!", null)); }

        } return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
    }
}
