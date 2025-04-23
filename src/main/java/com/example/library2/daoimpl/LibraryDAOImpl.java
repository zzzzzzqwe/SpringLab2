package com.example.library2.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.example.library2.dao.LibraryDAO;
import com.example.library2.model.Library;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public class LibraryDAOImpl implements LibraryDAO {

    private final SessionFactory sessionFactory;

    public LibraryDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Library library) {
        getSession().persist(library);
    }

    @Override
    public Library findById(Long id) {
        return getSession().get(Library.class, id);
    }

    @Override
    public List<Library> findAll() {
        return getSession().createQuery("from Library", Library.class).list();
    }

    @Override
    public void update(Library library) {
        getSession().merge(library);
    }

    @Override
    public void delete(Long id) {
        Library library = getSession().get(Library.class, id);
        if (library != null) {
            getSession().remove(library);
        }
    }
}