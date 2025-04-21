package com.example.library2.controller;

import com.example.library2.dto.BookDTO;
import com.example.library2.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getAll() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDTO getOne(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public void create(@RequestBody BookDTO dto) {
        bookService.createBook(dto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody BookDTO dto) {
        bookService.updateBook(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
