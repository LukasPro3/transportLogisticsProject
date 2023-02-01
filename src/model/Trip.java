package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Entity
public class Trip implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Cargo cargoId;                // koki krovini veza
    private String startPoint;
    private String destination;
    private LocalTime stopTime;       // kada sustojo
    private String stopLocation;      // kur sustojo
    @ManyToOne
    private User driversId;   //kuris veza


    public Trip(Cargo cargoId, String startPoint, String destination, LocalTime stopTime, String stopLocation, User driversId) {
        this.cargoId = cargoId;
        this.startPoint = startPoint;
        this.destination = destination;
        this.stopTime = stopTime;
        this.stopLocation = stopLocation;
        this.driversId = driversId;
    }

    public Trip() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cargo getCargoId() {
        return cargoId;
    }

    public void setCargoId(Cargo cargoId) {
        this.cargoId = cargoId;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalTime stopTime) {
        this.stopTime = stopTime;
    }

    public String getStopLocation() {
        return stopLocation;
    }

    public void setStopLocation(String stopLocation) {
        this.stopLocation = stopLocation;
    }

    public User getDriversId() {
        return driversId;
    }

    public void setDriversId(User driversId) {
        this.driversId = driversId;
    }

    @Override
    public String toString() {
        return id + destination;
    }
}
