package dao;

import entities.OS;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import services.SessionFactory;
import java.util.List;

public class OSDAO {
    public List<OS> getOSList(){
        Session session = null;
        List<OS> osList = null;
        try {
            session = SessionFactory.getSession();
            String queryString = "SELECT b FROM OS b";
            Query query = session.createQuery(queryString);
            osList = query.list();
        }
        catch (Exception e){
            System.out.println("Something went wrong while getting os list from database");
            e.printStackTrace();
        }
        finally {
            if(session != null) { session.close(); }
        }
        return osList;
    }

    public OS getOSById(Long osId){
        Session session = null;
        OS os = null;
        try {
            session = SessionFactory.getSession();
            String queryString = "SELECT b FROM OS b";
            os = session.get(OS.class, osId);
        }
        catch (Exception e){
            System.out.println("Something went wrong while getting os by id from database");
            e.printStackTrace();
        }
        finally {
            if(session != null) { session.close(); }
        }
        return os;
    }

    public void addOS(OS os){
        Transaction transaction = null;
        try (Session session = SessionFactory.getSession()) {
            transaction = session.beginTransaction();
            session.persist(os);
            transaction.commit();
            System.out.println("OS " + os.getName() + " " + os.getVersion() + " was successfully added");
        } catch (Exception e) {
            System.out.println("Something went wrong while adding os to database");
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
    }

    public void deleteOS(OS os){
        Transaction transaction = null;
        try (Session session = SessionFactory.getSession()) {
            transaction = session.beginTransaction();
            session.delete(os);
            transaction.commit();
            System.out.println("OS " + os.getName() + " " + os.getVersion() + " was deleted successfully");
        } catch (Exception e) {
            System.out.println("Something went wrong while deleting os from database");
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
    }
}
