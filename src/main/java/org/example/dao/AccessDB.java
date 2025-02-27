package org.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AccessDB {
    private final SessionFactory sessionFactory;

    /**
     * Constructs an AccessDB object and initializes the SessionFactory.
     */
    public AccessDB() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    /**
     * Retrieves the current session from the SessionFactory.
     * @return the current session
     */
    public Session getSessionFactory() {
        return sessionFactory.openSession();
    }
}
