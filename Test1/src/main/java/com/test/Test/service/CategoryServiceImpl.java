package com.test.Test.service;

import com.test.Test.model.Category;
import com.test.Test.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

   /* @Autowired
    private CategoryService categoryService;*/

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void saveCategory (Category category){
        categoryRepository.save(category);
    }

    @Override
    public Category findCategoryByName(String name){
        return categoryRepository.findByName(name);
    }

    @Override
    public Category findCourseByCategoryId(int id){

        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAllCategory( ){
        List<Category> categories = categoryRepository.findAll();

        return categories;
    }
}
