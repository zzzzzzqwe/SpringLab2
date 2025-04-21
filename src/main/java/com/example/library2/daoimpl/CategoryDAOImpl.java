package com.example.library2.daoimpl;

import com.example.library2.dao.CategoryDAO;
import com.example.library2.model.Category;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

    private final SessionFactory sessionFactory;

    public CategoryDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Category category) {
        getSession().persist(category);
    }

    @Override
    public Category findById(Long id) {
        return getSession().get(Category.class, id);
    }

    @Override
    public List<Category> findAll() {
        return getSession().createQuery("from Category", Category.class).list();
    }

    @Override
    public void update(Category category) {
        getSession().merge(category);
    }

    @Override
    public void delete(Long id) {
        Category category = getSession().get(Category.class, id);
        if (category != null) {
            getSession().remove(category);
        }
    }
}
