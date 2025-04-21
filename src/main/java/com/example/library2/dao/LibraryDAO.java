package com.example.library2.dao;

import com.example.library2.model.Library;

import java.util.List;

public interface LibraryDAO {
    void save(Library library);
    Library findById(Long id);
    List<Library> findAll();
    void update(Library library);
    void delete(Long id);
}
