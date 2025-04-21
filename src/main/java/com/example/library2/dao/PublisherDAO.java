package com.example.library2.dao;

import com.example.library2.model.Publisher;

import java.util.List;

public interface PublisherDAO {
    void save(Publisher publisher);
    Publisher findById(Long id);
    List<Publisher> findAll();
    void update(Publisher publisher);
    void delete(Long id);
}
