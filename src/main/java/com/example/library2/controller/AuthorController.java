package com.example.library2.controller;

import com.example.library2.model.Author;
import com.example.library2.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> getAll() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getOne(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public void create(@RequestBody Author author) {
        authorService.createAuthor(author);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Author author) {
        authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
