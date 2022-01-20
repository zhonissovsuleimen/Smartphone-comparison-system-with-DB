package services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;

public class SessionFactory {
    private static final org.hibernate.SessionFactory ourSessionFactory;
    static {
        try {
            ourSessionFactory = new Configuration().
                    configure("hibernate.cfg.xml").
                    buildSessionFactory();
        } catch (Throwable ex) {
            ApplicationLogger.log(Level.SEVERE, ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }
}
