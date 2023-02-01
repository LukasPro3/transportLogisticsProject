package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class Manager extends User implements Serializable {
    @OneToMany(mappedBy = "responsible")
    private List<Cargo> processedCargoList;

    public Manager(String firstName, String lastName, String phoneNumber, String email, LocalDate birthDate, String login, String password, boolean isAdmin) {
        super(firstName, lastName, phoneNumber, email, birthDate, login, password, UserRole.MANAGER, isAdmin);
    }

    public Manager() {

    }
}
