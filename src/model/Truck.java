package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
public class Truck implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate technicalInspectionUntil;
    private int euroStandart;
    private int mileage;
    private int fuelTankCapacity;
    private String color;
    private int horsePower;
    private int kwPower;
    private String licensePlate;
    private String vin;
    private VehicleStatus currentStatus;
    @OneToOne(mappedBy = "assignedTruckId", cascade = CascadeType.ALL)
    private Driver assignedToId;

    public Truck() {

    }

    public Truck(LocalDate technicalInspectionUntil, int euroStandart, int mileage, int fuelTankCapacity, String color, int horsePower, int kwPower, String licensePlate, String vin, VehicleStatus currentStatus) {
        this.technicalInspectionUntil = technicalInspectionUntil;
        this.euroStandart = euroStandart;
        this.mileage = mileage;
        this.fuelTankCapacity = fuelTankCapacity;
        this.color = color;
        this.horsePower = horsePower;
        this.kwPower = kwPower;
        this.licensePlate = licensePlate;
        this.vin = vin;
        this.currentStatus = currentStatus;
    }

    @Override
    public String toString() {
        return licensePlate;
    }

    public int getId() {
        return id;
    }

    public LocalDate getTechnicalInspectionUntil() {
        return technicalInspectionUntil;
    }

    public void setTechnicalInspectionUntil(LocalDate technicalInspectionUntil) {
        this.technicalInspectionUntil = technicalInspectionUntil;
    }

    public int getEuroStandard() {
        return euroStandart;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(int fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public int getKwPower() {
        return kwPower;
    }

    public void setKwPower(int kwPower) {
        this.kwPower = kwPower;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public VehicleStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(VehicleStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setEuroStandart(int euroStandart) {
        this.euroStandart = euroStandart;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedToId = assignedToId;
    }
}
