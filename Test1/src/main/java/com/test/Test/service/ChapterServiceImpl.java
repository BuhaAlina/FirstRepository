package com.test.Test.service;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Chapter;
import com.test.Test.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Override
    public Chapter findChapterByTitle(String title){
        return  chapterRepository.findByTitle(title);
    }


    @Override
    public void saveChapter(Chapter chapter){
         chapterRepository.save(chapter);
    }

    @Override
    public Chapter findChapterById(int id){
        return  chapterRepository.findById(id);
    }

    @Override
    public List<ResponseObject> findChapterByCourseId(int id){
        return  chapterRepository.findByCourseId(id);
    }
}
