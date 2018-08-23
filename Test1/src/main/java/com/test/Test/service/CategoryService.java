package com.test.Test.service;

import com.test.Test.model.Category;
import com.test.Test.model.Chapter;

import java.util.List;

public interface CategoryService {

    void saveCategory(Category category);
    Category findCategoryByName(String name);
    List<Category> findAllCategory( );
    Category findCourseByCategoryId(int id);


}
