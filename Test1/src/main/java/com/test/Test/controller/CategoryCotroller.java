package com.test.Test.controller;

import com.test.Test.helper.InternshipResponse;
import com.test.Test.helper.ResponseObject;
import com.test.Test.model.Category;
import com.test.Test.service.CategoryService;
import com.test.Test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class CategoryCotroller {

   @Autowired
   private UserService userService;

   @Autowired
   private CategoryService categoryService;

    //  POST  /create/category - create a new course category - data sent in the request body
    @RequestMapping(value = "/create/category",method = RequestMethod.POST)
    public ResponseEntity createCategory (@RequestBody Category category,@RequestHeader ("token") final String token) {
        if (userService.isAdmin(token) && userService.tokenIsValid(token)) {

            if (categoryService.findCategoryByName(category.getName()) == null) {
                categoryService.saveCategory(category);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Category created successfully!", Arrays.asList(category)));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "This category exist please create another category!", null));
            }


        }
        return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
    }

    //PUT --/category--update category
        @RequestMapping(value = "/category",method = RequestMethod.PUT)
        public ResponseEntity updateCategory (@RequestParam("name") final String name,@RequestBody Category category,@RequestHeader ("token") final String token)
        {if(userService.isAdmin(token)&&userService.tokenIsValid(token)){
            Category dbCategory=categoryService.findCategoryByName(name);
            if(dbCategory !=null){
               dbCategory.setName(category.getName());
                categoryService.saveCategory(dbCategory);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Category updated successfully!", Arrays.asList(dbCategory)));
            }else{ return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, " This category not found!", null));}


        }return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));

        }

    //GET   /categories - get all  categories
    @RequestMapping(value = "/categories",method = RequestMethod.GET)
    public ResponseEntity getcategories (@RequestHeader ("token") final String token)
    {
        if(userService.tokenIsValid(token)){
            List<ResponseObject> listCategories =new ArrayList<>();
            listCategories.addAll(categoryService.findAllCategory());
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Category list !", listCategories));
        }return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
    }
}

