package com.example.library2.service;

import com.example.library2.dao.AuthorDAO;
import com.example.library2.dto.AuthorDTO;
import com.example.library2.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorDAO authorDAO;

    public AuthorService(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorDAO.findAll().stream()
                .map(author -> new AuthorDTO(author.getId(), author.getName()))
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        Author author = authorDAO.findById(id);
        return author != null ? new AuthorDTO(author.getId(), author.getName()) : null;
    }

    public void createAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        authorDAO.save(author);
    }

    public void updateAuthor(Long id, AuthorDTO authorDTO) {
        Author existing = authorDAO.findById(id);
        if (existing != null) {
            existing.setName(authorDTO.getName());
            authorDAO.update(existing);
        }
    }

    public void deleteAuthor(Long id) {
        authorDAO.delete(id);
    }
}
