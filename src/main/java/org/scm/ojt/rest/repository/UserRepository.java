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

            user = (User) session.get(User.class, id);
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
        return user;
    }

    public User create(User newUser){
        try {
            // Getting Session Object From SessionFactory
            session = sessionFactory.openSession();
            // Getting Transaction Object From Session Object
            session.beginTransaction();
            Integer id = (Integer) session.save(newUser);
            newUser.setId(id);
            session.getTransaction().commit();
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
        return newUser;
    }

    public boolean delete(Integer id){
        boolean result = false;
        try {
            // Getting Session Object From SessionFactory
            session = sessionFactory.openSession();
            // Getting Transaction Object From Session Object
            session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            result = true;
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
        return result;
    }

    //GET author
    //Query q=s.createQuery("FROM Authors Where Name=:n");
    //q.setParameter("n", "Tolkien");
    //Authors Auth=(Authors) q.list().get(0);


    // DELETE ALL
    //sessionObj.beginTransaction();
    //Query queryObj = sessionObj.createQuery("DELETE FROM Student");
    //queryObj.executeUpdate();
    // Committing The Transactions To The Database
    //sessionObj.getTransaction().commit();

    public User update(Integer id, User userUpdate) {
        User user = null;

        if (!id.equals(null)){
            try {
                // Getting Session Object From SessionFactory
                session = sessionFactory.openSession();
                // Getting Transaction Object From Session Object
                session.beginTransaction();
                User userOld = (User) session.get(User.class, id);
                if (!userUpdate.getName().isEmpty()){
                    userOld.setName(userUpdate.getName());
                }
                if (!userUpdate.getEmail().isEmpty()){
                    userOld.setEmail(userUpdate.getEmail());
                }
                session.getTransaction().commit();
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
            user = findById(id);
        }
        return user;
    }
}