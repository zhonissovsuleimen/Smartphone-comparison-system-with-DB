package dao;

import entities.Smartphone;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.postgresql.util.PSQLException;
import services.SessionFactory;
import java.util.List;

public class SmartphoneDAO {
    public List<Smartphone> getSmartphoneList(){
        Session session = null;
        List<Smartphone> smartphoneList = null;
        try {
            session = SessionFactory.getSession();
            String queryString = "SELECT b FROM Smartphone b";
            Query query = session.createQuery(queryString);
            smartphoneList = query.list();
        }
        catch (Exception e){
            System.out.println("Something went wrong while getting smartphone list from database");
            e.printStackTrace();
        }
        finally {
            if(session != null) { session.close(); }
        }
        return smartphoneList;
    }

    public Smartphone getSmartphoneById(Long smartphoneId){
        Session session = null;
        Smartphone smartphone = null;
        try {
            session = SessionFactory.getSession();
            String queryString = "SELECT b FROM Smartphone b";
            smartphone = session.get(Smartphone.class, smartphoneId);
        }
        catch (Exception e){
            System.out.println("Something went wrong while getting smartphone by id from database");
            e.printStackTrace();
        }
        finally {
            if(session != null) { session.close(); }
        }
        return smartphone;
    }

    public void addSmartphone(Smartphone smartphone){
        Transaction transaction = null;
        try (Session session = SessionFactory.getSession()) {
            transaction = session.beginTransaction();
            session.persist(smartphone);
            transaction.commit();
            System.out.println("Smartphone " + smartphone.getName() + " was successfully added");
        } catch (Exception e) {
            System.out.println("Something went wrong while adding smartphone to database");
            if (transaction != null) transaction.rollback();
        }
    }

    public void deleteSmartphone(Smartphone smartphone){
        Transaction transaction = null;
        try (Session session = SessionFactory.getSession()) {
            transaction = session.beginTransaction();
            session.delete(smartphone);
            transaction.commit();
            System.out.println("Smartphone " + smartphone.getName() + " was deleted successfully");
        } catch (Exception e) {
            System.out.println("Something went wrong while deleting smartphone from database");
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
    }
}
