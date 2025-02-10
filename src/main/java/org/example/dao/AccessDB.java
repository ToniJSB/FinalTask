package org.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AccessDB {
    private final SessionFactory sessionFactory;

    public AccessDB() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public Session getSessionFactory() {
        return sessionFactory.openSession();
    }
}
