package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data

@Entity
public class Driver extends User implements Serializable {
    private LocalDate medicalSertificate;
    private LocalDate driversLicense;

    @OneToOne
    private Truck assignedTruckId;

    @OneToMany(mappedBy = "driversId")
    private List<Trip> trips;

    @OneToMany(mappedBy = "assignedTo")
    private List<Cargo> cargoList;

//    public Driver(String firstName, String lastName, String phoneNumber, String email, LocalDate birthDate, String login, String password, UserRole driver, boolean isAdmin,LocalDate medical) {
//        super(firstName, lastName, phoneNumber, email, birthDate, login, password,UserRole.DRIVER, isAdmin,);
//    }


    public Driver(String firstName, String lastName, String phoneNumber, String email, LocalDate birthDate, String login, String password, UserRole userRole, boolean isAdmin, LocalDate medicalSertificate, LocalDate driversLicense) {
        super(firstName, lastName, phoneNumber, email, birthDate, login, password, userRole, isAdmin);
        this.medicalSertificate = medicalSertificate;
        this.driversLicense = driversLicense;
    }

    public Driver() {

    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
