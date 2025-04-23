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
