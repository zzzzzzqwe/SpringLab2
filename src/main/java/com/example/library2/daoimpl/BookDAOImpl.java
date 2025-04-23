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
                        "join fetch b.author " +
                        "join fetch b.category " +
                        "join fetch b.publisher " +
                        "join fetch b.library", Book.class
        ).list();
    }


    @Override
    public void update(Book book) {
        getSession().merge(book);
    }

    @Override
    public void delete(Long id) {
        Book book = getSession().get(Book.class, id);
        if (book != null) {
            getSession().remove(book);
        }
    }
}
