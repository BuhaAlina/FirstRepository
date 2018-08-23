package com.test.Test.controller;

import com.test.Test.helper.InternshipResponse;
import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Category;
import com.test.Test.model.Course;
import com.test.Test.service.CategoryService;
import com.test.Test.service.CourseService;
import com.test.Test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;


 //POST --/create/course --create course by admin
    @RequestMapping(value = "/create/course", method = RequestMethod.POST)
    public ResponseEntity createCourse(@RequestBody Course course, @RequestHeader("token") final String token) {
        if (userService.isAdmin(token) && userService.tokenIsValid(token)) {

            if (courseService.findCourseBySDescription(course.getSmallDescription()) == null) {
                courseService.saveCourse(course);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Course created successfully!", Arrays.asList(course)));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "This course exist please create another course!", null));
            }

        }
        return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
    }

    //PUT --/course--update course by admin
    @RequestMapping(value = "/course",method = RequestMethod.PUT)
    public ResponseEntity updateCategory (@RequestParam("id") final int id, @RequestBody Course course, @RequestHeader ("token") final String token)
    {if(userService.isAdmin(token)&&userService.tokenIsValid(token)){
        Course dbCourse=courseService.findCourseById(id);
        if(dbCourse !=null){
            dbCourse.setSmallDescription(course.getSmallDescription());
            dbCourse.setLongDescription(course.getLongDescription());
            dbCourse.setTags(course.getTags());
            dbCourse.setImages(course.getImages());
            courseService.saveCourse(dbCourse);
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Course updated successfully!", Arrays.asList(dbCourse)));
        }else{ return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, " This course not found!", null));}


    }return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));

    }

    //GET   /courses - get all the course by a category Id
    @RequestMapping(value = "/courses",method = RequestMethod.GET)
    public ResponseEntity getcourses (@RequestHeader ("token") final String token,@RequestParam("id") final int id) {
        if(userService.tokenIsValid(token)){
            Course oldCourse = new Course();
            oldCourse.setCategory(categoryService.findCourseByCategoryId(id));
            if(oldCourse.getCategory() != null)
            {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Found!", courseService.findCourseByCategoryId(id)));
            }
            else
            {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Category not found!", null));
            }

        }return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
    }
}
