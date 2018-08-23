package com.test.Test.repository;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findBySmallDescription (String smallDescription);
    Course findById(int id);
    List<ResponseObject> findByCategoryId(int id);
}
