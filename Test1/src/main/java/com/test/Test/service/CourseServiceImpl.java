package com.test.Test.service;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Course;
import com.test.Test.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
   public void saveCourse(Course course){
        courseRepository.save(course);

    }

    @Override
   public Course findCourseBySDescription(String smallDescription){
        return courseRepository.findBySmallDescription(smallDescription);
            }
    @Override
    public Course findCourseById(int id){
        return courseRepository.findById(id);
    }

    @Override
    public  List<ResponseObject> findCourseByCategoryId(int id){
        return courseRepository.findByCategoryId(id);
    }
  /*  @Override
    public List<Course> findAllcourse( ){
        List<Course> courses = courserepository.findByCategoryId();

        return course;
    }*/
}
