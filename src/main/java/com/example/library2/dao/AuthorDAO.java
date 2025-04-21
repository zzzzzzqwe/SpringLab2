package com.example.library2.dao;

import com.example.library2.model.Author;

import java.util.List;

public interface AuthorDAO {
    void save(Author author);
    Author findById(Long id);
    List<Author> findAll();
    void update(Author author);
    void delete(Long id);
}
