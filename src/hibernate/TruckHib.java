package hibernate;

import model.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TruckHib {
    static EntityManager entityManager = null;
    static EntityManagerFactory emf = null;


    public TruckHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public List<Truck> getFreeTrucks() {
        entityManager = emf.createEntityManager();
        try {
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Truck.class));
            Query q = entityManager.createQuery(query);
            return q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return new ArrayList<>();
    }

    public void createTruck(Truck truck) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(truck);
            entityManager.getTransaction().commit();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Truck getTruckById(int id) {
        entityManager = emf.createEntityManager();
        Truck truck = null;
        try {
            entityManager.getTransaction().begin();
            truck = entityManager.find(Truck.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such truck by given Id");
        }
        return truck;
    }

    public void editTruck(Truck truck) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(truck);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteTruck(int id) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Truck truck = null;

            truck = entityManager.getReference(Truck.class, id);
            entityManager.merge(truck);
            entityManager.remove(truck);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public Truck getByLicensePlate(String licensePlate) {
        entityManager = emf.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Truck> query = cb.createQuery(Truck.class);
        Root<Truck> root = query.from(Truck.class);
        query.select(root).where(cb.like(root.get("licensePlate"), licensePlate));
        Query q;
        try {
            q = entityManager.createQuery(query);
            return (Truck) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
