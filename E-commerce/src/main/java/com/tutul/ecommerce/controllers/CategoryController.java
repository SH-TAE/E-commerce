package com.tutul.ecommerce.controllers;

import com.tutul.ecommerce.entities.Category;
import com.tutul.ecommerce.repositories.CategoryRepository;
import com.tutul.ecommerce.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
    }
    @PostMapping("/category")
    public ResponseEntity<Category>addCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok(category);
    }
    @GetMapping("/category")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
    @PutMapping("category/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Deleted Category Successfully");
    }


}
