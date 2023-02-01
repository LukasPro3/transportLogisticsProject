package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Entity
public class Cargo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double price;
    private Double amount;
    private Double weight;
    private Double height;
    private Double width;
    private Double length;
    private String collectionPoint;
    private String deliveryPoint;
    private String otherInfo;
    private String crm;
    private CargoStatus currentStatus;
    @OneToOne(mappedBy = "cargoId")
    private Trip assignedTrip;
    @ManyToOne
    private User responsible;
    @ManyToOne
    private User assignedTo;

    public Cargo(Double price, Double amount, Double weight, Double height, Double width, Double length, String collectionPoint, String deliveryPoint, String otherInfo, String crm, CargoStatus currentStatus, User responsible, User assignedTo) {
        this.price = price;
        this.amount = amount;
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.length = length;
        this.collectionPoint = collectionPoint;
        this.deliveryPoint = deliveryPoint;
        this.otherInfo = otherInfo;
        this.crm = crm;
        this.currentStatus = currentStatus;
        this.responsible = responsible;
        this.assignedTo = assignedTo;
    }

    public Cargo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int cargoId) {
        this.id = cargoId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public String getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(String deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public CargoStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(CargoStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }
}
