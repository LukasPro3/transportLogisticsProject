package hibernate;

import model.Trip;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TripHib {
    EntityManager entityManager = null;
    EntityManagerFactory emf = null;


    public TripHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public void showAllTrip() {

    }

    public void deleteTrip(int id) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Trip trip = null;

            trip = entityManager.getReference(Trip.class, id);
            entityManager.merge(trip);
            entityManager.remove(trip);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

    }

    public void updateTrip(Trip trip) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(trip);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }


    public List<Trip> getAllTrips() {
        entityManager = emf.createEntityManager();
        try {
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Trip.class));
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

    public void createTrip(Trip trip) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(trip);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public Trip getTripById(int id) {
        entityManager = emf.createEntityManager();
        Trip trip = null;
        try {
            entityManager.getTransaction().begin();
            trip = entityManager.find(Trip.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such trip by given Id");
        }
        return trip;
    }

    public Trip getTripCargoById(int cargoId) {
        entityManager = emf.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> query = cb.createQuery(Trip.class);
        Root<Trip> root = query.from(Trip.class);
        query.select(root).where(cb.equal(root.get("cargoId"), cargoId));
        Query q;
        try {
            q = entityManager.createQuery(query);
            return (Trip) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
