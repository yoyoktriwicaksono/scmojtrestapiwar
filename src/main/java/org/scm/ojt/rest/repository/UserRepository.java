package org.scm.ojt.rest.repository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.scm.ojt.rest.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoyok_T on 17/12/2018.
 */
public class UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);

    private Session session;
    private SessionFactory sessionFactory;

    @Inject
    public UserRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public List<User> findAll() {
        List<User> userList = new ArrayList<User>();
        try {
            // Getting Session Object From SessionFactory
            session = sessionFactory.openSession();
            // Getting Transaction Object From Session Object
            session.beginTransaction();

            userList = session.createQuery("FROM User").list();
        } catch(Exception sqlException) {
            if(null != session.getTransaction()) {
                LOG.info("\n.......Transaction Is Being Rolled Back.......\n");
                session.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return userList;
    }

    public User findById(Integer id) {
        User user = null;
        try {
            // Getting Session Object From SessionFactory
            session = sessionFactory.openSession();
            // Getting Transaction Object From Session Object
            session.beginTransaction();

            user = (User) session.load(User.class, id);
        } catch(Exception sqlException) {
            if(null != session.getTransaction()) {
                LOG.info("\n.......Transaction Is Being Rolled Back.......\n");
                session.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        }
        return user;
    }

}