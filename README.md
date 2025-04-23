# Лабораторная работа 2
Выполнено студентом: Gachayev Dmitrii I2302

Работа представляет собой Spring Boot приложение для управления библиотекой. Приложение поддерживает CRUD-операции с использованием REST API и взаимодействует с базой данных через `Hibernate`. API работает с DTO-объектами, а не напрямую с entities.

---

## Описание приложения

Приложение реализовано согласно следующим условиям и содержит:

- **3 контроллера**
- **3 сервиса**
- **5 взаимосвязанных сущностей** (Author, Publisher, Book, Category, Library)

---

## Структура и взаимосвязи сущностей:

- **Author**
    - Один автор может написать несколько книг (One-to-Many с Book).

- **Publisher**
    - Один издатель может издать несколько книг (One-to-Many с Book).

- **Book**
    - Каждая книга принадлежит одному автору (Many-to-One с Author).
    - Каждая книга имеет одного издателя (Many-to-One с Publisher).
    - Каждая книга может принадлежать нескольким категориям (Many-to-Many с Category).

- **Category**
    - Одна категория может содержать несколько книг (Many-to-Many с Book).

- **Library (Библиотека)**
    - Содержит коллекцию книг (ElementCollection).

---

## CRUD:

Приложение предоставляет следующий REST API:

- **Authors**:
    - Создание автора
    - Получение списка авторов
    - Обновление информации об авторе
    - Удаление автора

- **Books**:
    - Создание книги
    - Получение списка книг
    - Получение детальной информации о книге
    - Обновление информации о книге
    - Удаление книги

- **Categories**:
    - Создание категории
    - Получение списка категорий
    - Обновление информации о категории
    - Удаление категории


---
## Описание структуры:

1. **Сущности**:
- Это классы, которые представляют структуру таблиц в базе данных. Entity-классы описывают поля, типы данных, ограничения и связи между таблицами.
- С помощью аннотаций JPA (`@Entity`, `@Table`, `@Column`, `@Id`, и т.д.) Java-классы связываются с таблицами базы данных. JPA автоматически генерирует SQL-запросы и управляет сохранением и получением данных.

Пример:
```java
package com.example.library2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "library_id")
    private Library library;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
```

2. **DTO (Data Transfer Object)**:
- Это объекты для передачи данных между клиентом и сервером. Они обеспечивают слой абстракции, скрывая внутреннюю структуру сущностей.
- Они используются для того, чтобы клиент не взаимодействовал напрямую с сущностями. Сервисный слой преобразует entity в DTO и наоборот

Пример:
```java
package com.example.library2.dto;

public class BookDTO {
    private Long id;
    private String title;
    private Long authorId;
    private Long categoryId;
    private Long publisherId;
    private Long libraryId;

    public BookDTO() {}

    public BookDTO(Long id, String title, Long authorId, Long categoryId, Long publisherId, Long libraryId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.publisherId = publisherId;
        this.libraryId = libraryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public Long getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Long libraryId) {
        this.libraryId = libraryId;
    }
}

```
3. **DAO (Data Access Object)**:
- Это паттерн проектирования, который изолирует работу с базой данных от остального кода, инкапсулирует все SQL-запросы и операции с БД, а также предоставляет интерфейс для сохранения, поиска, обновления, удаления данных

Пример:
```java
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
```

4. **DAOImpl (реализация DAO)**:
- Это класс, который реализует интерфейс DAO
- Внутри методов используется Hibernate для работы с базой

Пример:
```java
package com.example.library2.daoimpl;

import com.example.library2.dao.BookDAO;
import com.example.library2.model.Book;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class BookDAOImpl implements BookDAO {

    private final SessionFactory sessionFactory;

    public BookDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Book book) {
        getSession().persist(book);
    }

    @Override
    public Book findById(Long id) {
        return getSession().get(Book.class, id);
    }

    @Override
    public List<Book> findAll() {
        return getSession().createQuery(
                "select b from Book b " +
                        "left join fetch b.author " +
                        "left join fetch b.category " +
                        "left join fetch b.publisher " +
                        "left join fetch b.library", Book.class
        ).list();
    }


    @Override
    public void update(Book book) {
        getSession().merge(book);
    }

    @Override
    public void delete(Long id) {
        Session session = getSession();
        Book book = session.get(Book.class, id);

        if (book != null) {
            if (book.getAuthor() != null) {
                book.getAuthor().getBooks().remove(book);
            }
            if (book.getCategory() != null) {
                book.getCategory().getBooks().remove(book);
            }
            if (book.getPublisher() != null) {
                book.getPublisher().getBooks().remove(book);
            }
            if (book.getLibrary() != null) {
                book.getLibrary().getBooks().remove(book);
            }
            book.setAuthor(null);
            book.setCategory(null);
            book.setPublisher(null);
            book.setLibrary(null);

            session.merge(book);
            session.flush();
            session.remove(book);
        }
    }

}
```

5. **Сервисы**
- Классы, которые содержат бизнес-логику приложения. Сервисы обрабатывают данные и управляют транзакциями.
- Сервисы принимают запросы от контроллеров, взаимодействуют с репозиториями (базой данных), выполняют бизнес-логику и возвращают результаты обратно контроллерам.

Пример:
```java
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
```

6. **Контроллеры**
- Компоненты, которые принимают HTTP-запросы от клиента, вызывают нужную бизнес-логику (через сервисы), возвращают ответ клиенту

Пример:
```java
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
```

## Тестирование
1. Запускаю приложение через `LibraryApplication` и перехожу на `localhost:8080`
2. Для отправки HTTP запросов использую `vs-code` расширение `REST Client`
3. После отправки http-запросов проверяю `localhost` и также выполняю sql-запросы через `pgAdmin` для проверки корректности взаимодействия с бд 
### Проверяю CRUD операции сервиса `AuthorService`:
1. `CREATE:`
```
POST http://localhost:8080/authors
Content-Type: application/json

{
  "name": "TESTAUTHOR",
}
```

2. `READ:`
```
GET http://localhost:8080/authors
Accept: application/json
```

3. `UPDATE:`
```
PUT http://localhost:8080/authors/1
Content-Type: application/json

{
  "name": "put method test"
}
```

4. `DELETE:`
```
DELETE http://localhost:8080/authors/1
```

Ожидаемый результат совпадает с фактическим. Изменения можно наблюдать как на `http://localhost:8080/authors` так и в базе данных

### Проверяю CRUD операции сервиса `CategoryService`:
1. `CREATE`:
```
POST http://localhost:8080/categories
Content-Type: application/json

{
  "name": "TestCategory"
}
```

2. `READ`:
```
GET http://localhost:8080/categories
Accept: application/json
```

3. `UPDATE`:
```
PUT http://localhost:8080/categories/1
Content-Type: application/json

{
  "name": "CategoryAfterPut"
}
```

4. `DELETE`:
```
DELETE http://localhost:8080/categories/1
```
Ожидаемый результат совпадает с фактическим. Изменения можно наблюдать как на `http://localhost:8080/categories` так и в базе данных

### Проверяю CRUD операции сервиса `BookService`:
В этот раз сделал возможныи добавление книги даже если некоторых полей нет в бд. Будет объект с некоторыми null полями.

```
POST http://localhost:8080/books
Content-Type: application/json

{
  "title": "testBook",
  "authorId": 1,
  "categoryId": 1,
  "publisherId": 871,
  "libraryId": 1
}
```
В данном случае publisherId будет null, т.к такого значения нет в бд
2. `READ`:
```
GET http://localhost:8080/books
Accept: application/json
```
3. `UPDATE`:
```
PUT http://localhost:8080/books/2
Content-Type: application/json

{
  "title": "anotherTestbook",
  "authorId": 1,
  "categoryId": 1,
  "publisherId": 1,
  "libraryId": 1
}
```
4. `DELETE`:
```
DELETE http://localhost:8080/books/1
```

Ожидаемый результат совпадает с фактическим. Изменения можно наблюдать как на `http://localhost:8080/books` так и в базе данных