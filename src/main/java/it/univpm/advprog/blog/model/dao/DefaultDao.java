package it.univpm.advprog.blog.model.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;

public abstract class DefaultDao {

    private SessionFactory sessionFactory;
    private Session session;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        // shared session exists
        Session session = this.session;
        if (session == null) {
            // if the session does not exist, create it
            try {
                session = this.sessionFactory.getCurrentSession();
            } catch (HibernateException ex) {
                session = this.sessionFactory.openSession();
            }
        }
        return session;
    }

}
