package com.test.Test.repository;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    //void save(Chapter chapter);
    Chapter findByTitle(String title);
    Chapter findById(int id);
    List<ResponseObject> findByCourseId(int id);

}
