package hibernate;

import model.Driver;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserHib {

    static EntityManager entityManager = null;
    static EntityManagerFactory emf = null;


    public UserHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public static User getUserById(int id) {
        entityManager = emf.createEntityManager();
        User user = null;
        try {
            entityManager.getTransaction().begin();
            user = entityManager.find(User.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return user;

    }

    public static void deleteUser(int id) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            User user = null;

            user = entityManager.getReference(User.class, id);
            entityManager.merge(user);
            entityManager.remove(user);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void createUser(User user) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public User getUserByLoginData(String login, String password) {
        entityManager = emf.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(cb.and(cb.like(root.get("login"), login), cb.like(root.get("password"), password)));
        Query q;
        try {
            q = entityManager.createQuery(query);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Driver> getAllDrivers() {
        entityManager = emf.createEntityManager();
        try {
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Driver.class));
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
}


