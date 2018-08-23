package com.test.Test.service;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Category;
import com.test.Test.model.Course;

import java.util.List;

public interface CourseService {

    void saveCourse(Course course);
    Course findCourseBySDescription(String smallDescription);
    Course findCourseById(int id);
    List<ResponseObject> findCourseByCategoryId(int id);
}
