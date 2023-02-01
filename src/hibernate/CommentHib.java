package hibernate;

import model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CommentHib {

    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public CommentHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public void createComment(Comment comment) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(comment);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void updateComment(Comment comment) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(comment);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public Comment getCommentById(int id) {
        entityManager = emf.createEntityManager();
        Comment comment = null;
        try {
            entityManager.getTransaction().begin();
            comment = entityManager.find(Comment.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such comment by given Id");
        }
        return comment;

    }

    public List<Comment> getAllComments() {
        entityManager = emf.createEntityManager();
        try {
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Comment.class));
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

    public Comment getByForumId(int forumId) {
        entityManager = emf.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query = cb.createQuery(Comment.class);
        Root<Comment> root = query.from(Comment.class);
        query.select(root).where(cb.equal(root.get("forum"), forumId));
        Query q;
        try {
            q = entityManager.createQuery(query);
            return (Comment) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Comment> getCommentsByForum(int forumId) {
        entityManager = emf.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Comment> query = cb.createQuery(Comment.class);
            Root<Comment> root = query.from(Comment.class);
            query.select(root).where(cb.equal(root.get("forum"), forumId));
            Query q;
            try {
                q = entityManager.createQuery(query);
                return q.getResultList();
            } catch (NoResultException e) {
                return new ArrayList<>();
            }

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
