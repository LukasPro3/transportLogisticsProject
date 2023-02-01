package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String forumTitle;
    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Forum(String title) {
        this.forumTitle = title;
    }

    @Override
    public String toString() {
        return forumTitle;
    }
}
