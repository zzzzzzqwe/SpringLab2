package com.example.library2.daoimpl;

import com.example.library2.dao.PublisherDAO;
import com.example.library2.model.Publisher;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Transactional
public class PublisherDAOImpl implements PublisherDAO {
    private final SessionFactory sessionFactory;

    public PublisherDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Publisher publisher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(publisher);
            tx.commit();
        }
    }

    @Override
    public Publisher findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Publisher.class, id);
        }
    }

    @Override
    public List<Publisher> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Publisher", Publisher.class).list();
        }
    }

    @Override
    public void update(Publisher publisher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(publisher);
            tx.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Publisher publisher = session.get(Publisher.class, id);
            if (publisher != null) {
                session.remove(publisher);
            }
            tx.commit();
        }
    }
}
