package dao;

import entities.Brand;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import services.SessionFactory;
import java.util.List;

public class BrandDAO {
    public List<Brand> getBrandList(){
        Session session = null;
        List<Brand> brandList = null;
        try {
            session = SessionFactory.getSession();
            String queryString = "SELECT b FROM Brand b";
            Query query = session.createQuery(queryString);
            brandList = query.list();
        }
        catch (Exception e){
            System.out.println("Something went wrong while getting brand list from database");
            e.printStackTrace();
        }
        finally {
            if(session != null) { session.close(); }
        }
        return brandList;
    }

    public Brand getBrandById(Long brandId){
        Session session = null;
        Brand brand = null;
        try {
            session = SessionFactory.getSession();
            String queryString = "SELECT b FROM Brand b";
            brand = session.get(Brand.class, brandId);
        }
        catch (Exception e){
            System.out.println("Something went wrong while getting brand by id from database");
            e.printStackTrace();
        }
        finally {
            if(session != null) { session.close(); }
        }
        return brand;
    }

    public void addBrand(Brand brand){
        Transaction transaction = null;
        try (Session session = SessionFactory.getSession()) {
            transaction = session.beginTransaction();
            session.persist(brand);
            transaction.commit();
            System.out.println("Brand " + brand.getName() + " was successfully added");
        } catch (Exception e) {
            System.out.println("Something went wrong while adding brand to database");
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
    }

    public void deleteBrand(Brand brand){
        Transaction transaction = null;
        try (Session session = SessionFactory.getSession()) {
            transaction = session.beginTransaction();
            session.delete(brand);
            transaction.commit();
            System.out.println("Brand " + brand.getName() + " was deleted successfully");
        } catch (Exception e) {
            System.out.println("Something went wrong while deleting brand from database");
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
    }
}
