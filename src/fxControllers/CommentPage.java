package fxControllers;

import hibernate.CommentHib;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Comment;
import model.Forum;

import javax.persistence.EntityManagerFactory;

public class CommentPage {
    public TextField commentField;
    public Button commentBtn;
    public TextField titleField;
    private CommentHib commentHib;
    private EntityManagerFactory entityManagerFactory;
    private Forum forum;
    private Comment parent;

    public void createComment() {
        Comment comment = new Comment(forum, titleField.getText(), commentField.getText(), parent);
        commentHib.createComment(comment);
    }

    public void setData(EntityManagerFactory entityManagerFactory, Forum forum, Comment parent) {
        this.entityManagerFactory = entityManagerFactory;
        this.commentHib = new CommentHib(entityManagerFactory);
        this.forum = forum;
        this.parent = parent;
    }

    public void setData(EntityManagerFactory entityManagerFactory, Comment parent) {
        this.entityManagerFactory = entityManagerFactory;
        this.commentHib = new CommentHib(entityManagerFactory);
        this.forum = null;
        this.parent = parent;
    }

    public void setData(EntityManagerFactory entityManagerFactory, Forum forum) {
        this.entityManagerFactory = entityManagerFactory;
        this.commentHib = new CommentHib(entityManagerFactory);
        this.forum = forum;
        this.parent = null;
    }
}
