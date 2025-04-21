package com.example.library2.dao;

import com.example.library2.model.Book;

import java.util.List;

public interface BookDAO {
    void save(Book book);
    Book findById(Long id);
    List<Book> findAll();
    void update(Book book);
    void delete(Long id);
}
