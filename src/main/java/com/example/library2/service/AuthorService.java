package com.example.library2.service;

import com.example.library2.dao.AuthorDAO;
import com.example.library2.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorDAO authorDAO;

    public AuthorService(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    public List<Author> getAllAuthors() {
        return authorDAO.findAll();
    }

    public Author getAuthorById(Long id) {
        return authorDAO.findById(id);
    }

    public void createAuthor(Author author) {
        authorDAO.save(author);
    }

    public void updateAuthor(Long id, Author updated) {
        Author existing = authorDAO.findById(id);
        if (existing != null) {
            existing.setName(updated.getName());
            authorDAO.update(existing);
        }
    }

    public void deleteAuthor(Long id) {
        authorDAO.delete(id);
    }
}
