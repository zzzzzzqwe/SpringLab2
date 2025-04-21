package com.example.library2.service;

import com.example.library2.dao.CategoryDAO;
import com.example.library2.dto.CategoryDTO;
import com.example.library2.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryDAO.findAll().stream()
                .map(c -> new CategoryDTO(c.getId(), c.getName()))
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Category c = categoryDAO.findById(id);
        return new CategoryDTO(c.getId(), c.getName());
    }

    public void createCategory(CategoryDTO dto) {
        Category c = new Category();
        c.setName(dto.getName());
        categoryDAO.save(c);
    }

    public void updateCategory(Long id, CategoryDTO dto) {
        Category existing = categoryDAO.findById(id);
        if (existing != null) {
            existing.setName(dto.getName());
            categoryDAO.update(existing);
        }
    }

    public void deleteCategory(Long id) {
        categoryDAO.delete(id);
    }
}
