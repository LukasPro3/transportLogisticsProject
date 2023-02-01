package fxControllers;

import hibernate.TruckHib;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Truck;

import javax.persistence.EntityManagerFactory;

import static model.VehicleStatus.FREE;

public class TruckPage {
    public TextField licensePlateField;
    public TextField euroStandardField;
    public TextField VINField;
    public TextField mileageField;
    public TextField horsePowerField;
    public TextField powerField;
    public TextField colorField;
    public Button createButton;
    public DatePicker technicalInspectionField;
    public TextField fuelTankField;
    private Truck selectedTruck;

    private TruckHib truckHib;
    private EntityManagerFactory entityManagerFactory;
    //EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("TransportLogistics");
    //TruckHib truckHib = new TruckHib(entityManagerFactory);

    public void setData(EntityManagerFactory entityManagerFactory, Truck selectedTruck) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedTruck = selectedTruck;
        this.truckHib = new TruckHib(entityManagerFactory);
        fillFields();
    }

    private void fillFields() {
        Truck truck = truckHib.getTruckById(selectedTruck.getId());
        technicalInspectionField.setValue(truck.getTechnicalInspectionUntil());
        euroStandardField.setText(String.valueOf(truck.getEuroStandard()));
        mileageField.setText(String.valueOf(truck.getMileage()));
        fuelTankField.setText(String.valueOf(truck.getFuelTankCapacity()));
        colorField.setText(truck.getColor());
        horsePowerField.setText(String.valueOf(truck.getHorsePower()));
        powerField.setText(String.valueOf(truck.getKwPower()));
        licensePlateField.setText(truck.getLicensePlate());
        VINField.setText(truck.getVin());
        createButton.setOnAction(actionEvent -> {
            editTruck(truck);
        });
        createButton.setText("Update");
    }

    private void editTruck(Truck truck) {
        truck.setTechnicalInspectionUntil(technicalInspectionField.getValue());
        truck.setEuroStandart(Integer.parseInt(euroStandardField.getText()));
        truck.setMileage(Integer.parseInt(mileageField.getText()));
        truck.setFuelTankCapacity(Integer.parseInt(fuelTankField.getText()));
        truck.setColor(colorField.getText());
        truck.setHorsePower(Integer.parseInt(horsePowerField.getText()));
        truck.setKwPower(Integer.parseInt(powerField.getText()));
        truck.setLicensePlate(licensePlateField.getText());
        truck.setVin(VINField.getText());
        truckHib.editTruck(truck);

    }

    public void createNewTruck() {
        Truck truck = null;
        truck = new Truck(technicalInspectionField.getValue(), Integer.parseInt(euroStandardField.getText()), Integer.parseInt(mileageField.getText()), Integer.parseInt(fuelTankField.getText()), colorField.getText(), Integer.parseInt(horsePowerField.getText()), Integer.parseInt(powerField.getText()), licensePlateField.getText(), VINField.getText(), FREE);
        truckHib.createTruck(truck);
    }

    public void deleteTruck(int id) {
        truckHib.deleteTruck(id);
    }


    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.truckHib = new TruckHib(entityManagerFactory);
    }
}
