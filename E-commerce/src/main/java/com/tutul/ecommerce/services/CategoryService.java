package com.tutul.ecommerce.services;


import com.tutul.ecommerce.entities.Category;
import com.tutul.ecommerce.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }
    public Category updateCategory(Long id, Category updatedCategory) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No such Categories found with this id "+id));
        category.setName(updatedCategory.getName());
        category.setIsActive(updatedCategory.getIsActive());
        return categoryRepository.save(category);
    }
}