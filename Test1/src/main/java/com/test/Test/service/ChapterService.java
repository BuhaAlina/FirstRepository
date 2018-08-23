package com.test.Test.service;

import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Chapter;

import java.util.List;

public interface ChapterService {
    Chapter findChapterByTitle(String title);
    void saveChapter (Chapter chapter);
    Chapter findChapterById(int id);
    List<ResponseObject> findChapterByCourseId(int id);
}
