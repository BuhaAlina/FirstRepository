package com.test.Test.controller;

import com.test.Test.helper.InternshipResponse;
import com.test.Test.model.Category;
import com.test.Test.model.Chapter;
import com.test.Test.model.Course;
import com.test.Test.repository.ChapterRepository;
import com.test.Test.service.ChapterService;
import com.test.Test.service.CourseService;
import com.test.Test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class ChapterController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private UserService userService;

    //POST -- /create/chapter--create chapter by admin
    @RequestMapping(value = "/create/chapter", method = RequestMethod.POST)
    public ResponseEntity createChapter(@RequestBody Chapter chapter, @RequestHeader("token") final String token) {
        if (userService.isAdmin(token) && userService.tokenIsValid(token)) {
            if (chapterService.findChapterByTitle(chapter.getTitle()) == null) {
                chapterService.saveChapter(chapter);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Chapter created successfully!", Arrays.asList(chapter)));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "This chapter exist please create another chapter!", null)); }
        }return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));

    }


    //PUT --/chapter--update chapter by id chapter
    @RequestMapping(value = "/chapter", method = RequestMethod.PUT)
    public ResponseEntity updateChapter(@RequestParam("id") final int id, @RequestBody Chapter chapter, @RequestHeader("token") final String token) {
        if (userService.isAdmin(token) && userService.tokenIsValid(token)) {
            Chapter dbChapter = chapterService.findChapterById(id);
            if (dbChapter != null) {
                dbChapter.setContent(chapter.getContent());
                dbChapter.setTitle(chapter.getTitle());
                chapterService.saveChapter(dbChapter);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Chapter updated successfully!", Arrays.asList(dbChapter)));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, " This chapter not found!", null)); }
        }return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));

    }



    //GET   /chapters - get all the chapter by a course Id
    @RequestMapping(value = "/chapters", method = RequestMethod.GET)
    public ResponseEntity getchapters (@RequestHeader ("token") final String token,@RequestParam("id") final int id) {
        if (userService.tokenIsValid(token)) {
            Chapter dbChapter = new Chapter();
            dbChapter.setCourse(courseService.findCourseById(id));//chapter
            if (dbChapter.getCourse() != null) {

                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Found chapters !", chapterService.findChapterByCourseId(id)));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Chapter not found!", null)); }

            } return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
    }

}




