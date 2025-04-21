package com.example.library2.dao;

import com.example.library2.model.Category;

import java.util.List;

public interface CategoryDAO {
    void save(Category category);
    Category findById(Long id);
    List<Category> findAll();
    void update(Category category);
    void delete(Long id);
}
