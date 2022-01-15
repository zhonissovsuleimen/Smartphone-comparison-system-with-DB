package dao;

import entities.Chipset;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import services.SessionFactory;
import java.util.List;

public class ChipsetDAO {
    public List<Chipset> getChipsetList(){
        Session session = null;
        List<Chipset> chipsetList = null;
        try {
            session = SessionFactory.getSession();
            String queryString = "SELECT b FROM Chipset b";
            Query query = session.createQuery(queryString);
            chipsetList = query.list();
        }
        catch (Exception e){
            System.out.println("Something went wrong while getting chipset list from database");
            e.printStackTrace();
        }
        finally {
            if(session != null) { session.close(); }
        }
        return chipsetList;
    }

    public Chipset getChipsetById(Long chipsetId){
        Session session = null;
        Chipset chipset = null;
        try {
            session = SessionFactory.getSession();
            String queryString = "SELECT b FROM Chipset b";
            chipset = session.get(Chipset.class, chipsetId);
        }
        catch (Exception e){
            System.out.println("Something went wrong while getting chipset by id from database");
            e.printStackTrace();
        }
        finally {
            if(session != null) { session.close(); }
        }
        return chipset;
    }

    public void addChipset(Chipset chipset){
        Transaction transaction = null;
        try (Session session = SessionFactory.getSession()) {
            transaction = session.beginTransaction();
            session.persist(chipset);
            transaction.commit();
            System.out.println("Chipset " + chipset.getName() + " was successfully added");
        } catch (Exception e) {
            System.out.println("Something went wrong while adding chipset to database");
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
    }

    public void deleteChipset(Chipset chipset){
        Transaction transaction = null;
        try (Session session = SessionFactory.getSession()) {
            transaction = session.beginTransaction();
            session.delete(chipset);
            transaction.commit();
            System.out.println("Chipset " + chipset.getName() + " was deleted successfully");
        } catch (Exception e) {
            System.out.println("Something went wrong while deleting chipset from database");
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
    }
}
