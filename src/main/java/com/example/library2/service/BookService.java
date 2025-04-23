package com.example.library2.service;

import com.example.library2.dao.*;
import com.example.library2.dto.BookDTO;
import com.example.library2.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class BookService {

    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final CategoryDAO categoryDAO;
    private final PublisherDAO publisherDAO;
    private final LibraryDAO libraryDAO;

    public BookService(BookDAO bookDAO, AuthorDAO authorDAO,
                       CategoryDAO categoryDAO, PublisherDAO publisherDAO,
                       LibraryDAO libraryDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.categoryDAO = categoryDAO;
        this.publisherDAO = publisherDAO;
        this.libraryDAO = libraryDAO;
    }

    public List<BookDTO> getAllBooks() {
        return bookDAO.findAll().stream()
                .map(b -> new BookDTO(
                        b.getId(),
                        b.getTitle(),
                        b.getAuthor() != null ? b.getAuthor().getId() : null,
                        b.getCategory() != null ? b.getCategory().getId() : null,
                        b.getPublisher() != null ? b.getPublisher().getId() : null,
                        b.getLibrary() != null ? b.getLibrary().getId() : null
                ))
                .collect(Collectors.toList());


    }



    public BookDTO getBookById(Long id) {
        Book b = bookDAO.findById(id);
        return new BookDTO(
                b.getId(),
                b.getTitle(),
                b.getAuthor().getId(),
                b.getCategory().getId(),
                b.getPublisher().getId(),
                b.getLibrary().getId());
    }

    public void createBook(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(authorDAO.findById(dto.getAuthorId()));
        book.setCategory(categoryDAO.findById(dto.getCategoryId()));
        book.setPublisher(publisherDAO.findById(dto.getPublisherId()));
        book.setLibrary(libraryDAO.findById(dto.getLibraryId()));
        bookDAO.save(book);
    }

    public void updateBook(Long id, BookDTO dto) {
        Book existing = bookDAO.findById(id);
        if (existing != null) {
            existing.setTitle(dto.getTitle());
            existing.setAuthor(authorDAO.findById(dto.getAuthorId()));
            existing.setCategory(categoryDAO.findById(dto.getCategoryId()));
            existing.setPublisher(publisherDAO.findById(dto.getPublisherId()));
            existing.setLibrary(libraryDAO.findById(dto.getLibraryId()));
            bookDAO.update(existing);
        }
    }

    public void deleteBook(Long id) {
        bookDAO.delete(id);
    }
}
