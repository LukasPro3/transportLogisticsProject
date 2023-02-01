package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String text;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies;
    @ManyToOne
    private Comment parentComment;
    @ManyToOne
    private Forum forum;

    public Comment(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public Comment(String title, String text, Comment parentComment) {
        this.title = title;
        this.text = text;
        this.replies = new ArrayList<>();
        this.parentComment = parentComment;
    }

    public Comment(Forum forum, String title, String text, Comment parentComment) {
        this.forum = forum;
        this.title = title;
        this.text = text;
        this.replies = new ArrayList<>();
        this.parentComment = parentComment;
    }

    public Comment(Forum forum, String title, String text) {
        this.forum = forum;
        this.title = title;
        this.text = text;
        this.replies = new ArrayList<>();
        this.parentComment = null;
    }
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        Comment comment = (Comment) o;
//        return Objects.equals(id, comment.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 0;
//    }

    @Override
    public String toString() {
        return text;
    }
}
