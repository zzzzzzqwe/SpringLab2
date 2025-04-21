package com.example.library2.daoimpl;

import com.example.library2.dao.AuthorDAO;
import com.example.library2.model.Author;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class AuthorDAOImpl implements AuthorDAO {
    private final SessionFactory sessionFactory;

    public AuthorDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession(); // вместо openSession()
    }

    @Override
    public void save(Author author) {
        getSession().persist(author);
    }

    @Override
    public Author findById(Long id) {
        return getSession().get(Author.class, id);
    }

    @Override
    public List<Author> findAll() {
        return getSession().createQuery("from Author", Author.class).list();
    }

    @Override
    public void update(Author author) {
        getSession().merge(author);
    }

    @Override
    public void delete(Long id) {
        Author author = getSession().get(Author.class, id);
        if (author != null) {
            getSession().remove(author);
        }
    }
}
