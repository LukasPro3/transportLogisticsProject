package hibernate;

import model.Cargo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CargoHib {
    static EntityManager entityManager = null;
    static EntityManagerFactory emf = null;


    public CargoHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public void createCargo(Cargo cargo) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cargo);
            entityManager.getTransaction().commit();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAllCargo() {

    }

    public void deleteCargo(int id) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Cargo cargo = null;

            cargo = entityManager.getReference(Cargo.class, id);
            entityManager.merge(cargo);
            entityManager.remove(cargo);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }


    }

    public void updateCargo(Cargo cargo) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cargo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

    }

    public List<Cargo> getAllCargo() {
        entityManager = emf.createEntityManager();
        try {
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Cargo.class));
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

    public Cargo getCargoById(int cargoId) {
        entityManager = emf.createEntityManager();
        Cargo cargo = null;
        try {
            entityManager.getTransaction().begin();
            cargo = entityManager.find(Cargo.class, cargoId);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return cargo;
    }
}
