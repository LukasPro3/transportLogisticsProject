package fxControllers;

import hibernate.ForumHib;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Forum;

import javax.persistence.EntityManagerFactory;

public class ForumPage {
    public TextField titleField;
    public Button createBtn;
    private EntityManagerFactory entityManagerFactory;
    private ForumHib forumHib;

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.forumHib = new ForumHib(entityManagerFactory);
    }

    public void addForum() {
        Forum forum = new Forum(titleField.getText());
        forumHib.createForum(forum);

    }
}
