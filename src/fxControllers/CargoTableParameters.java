package fxControllers;

import javafx.beans.property.SimpleStringProperty;

public class CargoTableParameters {
    private SimpleStringProperty cargoId = new SimpleStringProperty();
    private SimpleStringProperty cargoAmount = new SimpleStringProperty();
    private SimpleStringProperty cargoPrice = new SimpleStringProperty();
    private SimpleStringProperty cargoCollection = new SimpleStringProperty();
    private SimpleStringProperty cargoDelivery = new SimpleStringProperty();
    private SimpleStringProperty cargoStatus = new SimpleStringProperty();

    public CargoTableParameters(SimpleStringProperty cargoId, SimpleStringProperty cargoAmount, SimpleStringProperty cargoPrice, SimpleStringProperty cargoCollection, SimpleStringProperty cargoDelivery, SimpleStringProperty cargoStatus) {
        this.cargoId = cargoId;
        this.cargoAmount = cargoAmount;
        this.cargoPrice = cargoPrice;
        this.cargoCollection = cargoCollection;
        this.cargoDelivery = cargoDelivery;
        this.cargoStatus = cargoStatus;
    }

    public CargoTableParameters() {
    }

    public String getCargoId() {
        return cargoId.get();
    }

    public void setCargoId(String cargoId) {
        this.cargoId.set(cargoId);
    }

    public SimpleStringProperty cargoIdProperty() {
        return cargoId;
    }

    public String getCargoAmount() {
        return cargoAmount.get();
    }

    public void setCargoAmount(String cargoAmount) {
        this.cargoAmount.set(cargoAmount);
    }

    public SimpleStringProperty cargoAmountProperty() {
        return cargoAmount;
    }

    public String getCargoPrice() {
        return cargoPrice.get();
    }

    public void setCargoPrice(String cargoPrice) {
        this.cargoPrice.set(cargoPrice);
    }

    public SimpleStringProperty cargoPriceProperty() {
        return cargoPrice;
    }

    public String getCargoCollection() {
        return cargoCollection.get();
    }

    public void setCargoCollection(String cargoCollection) {
        this.cargoCollection.set(cargoCollection);
    }

    public SimpleStringProperty cargoCollectionProperty() {
        return cargoCollection;
    }

    public String getCargoDelivery() {
        return cargoDelivery.get();
    }

    public void setCargoDelivery(String cargoDelivery) {
        this.cargoDelivery.set(cargoDelivery);
    }

    public SimpleStringProperty cargoDeliveryProperty() {
        return cargoDelivery;
    }

    public String getCargoStatus() {
        return cargoStatus.get();
    }

    public void setCargoStatus(String cargoStatus) {
        this.cargoStatus.set(cargoStatus);
    }

    public SimpleStringProperty cargoStatusProperty() {
        return cargoStatus;
    }
}
