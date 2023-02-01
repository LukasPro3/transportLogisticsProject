package fxControllers;

import hibernate.TruckHib;
import hibernate.UserHib;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Driver;
import model.Truck;
import model.User;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class AssignDriverPage {


    public Button AssignButton;
    public ListView<Driver> driverList;
    public Button closeButton;
    private EntityManagerFactory entityManagerFactory;
    private User user;
    private Truck selectedTruck;
    private UserHib userHib;
    private TruckHib truckHib;


    public void setData(EntityManagerFactory entityManagerFactory, User user, Truck selectedTruck) {
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;
        this.selectedTruck = selectedTruck;
        this.userHib = new UserHib(entityManagerFactory);
        this.truckHib = new TruckHib(entityManagerFactory);
        fillList();
    }

    public void setData(EntityManagerFactory entityManagerFactory, Truck selectedTruck) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedTruck = selectedTruck;
        this.userHib = new UserHib(entityManagerFactory);
        this.truckHib = new TruckHib(entityManagerFactory);
        unAssign();
    }

    public void fillList() {
        List<Driver> allDrivers = userHib.getAllDrivers();
        allDrivers.forEach(d -> {
            if (d.getAssignedTruckId() == null) {
                driverList.getItems().add(d);
            }
        });
    }

    public void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void assign() {
        Driver selectedDriver = driverList.getSelectionModel().getSelectedItem();
        selectedTruck.setAssignedToId(selectedDriver);
        selectedDriver.setAssignedTruckId(selectedTruck);
        truckHib.editTruck(selectedTruck);
        userHib.updateUser(selectedDriver);
    }

    public void unAssign() {
        Driver selectedDriver = selectedTruck.getAssignedToId();
        selectedDriver.setAssignedTruckId(null);
        truckHib.editTruck(selectedTruck);
        userHib.updateUser(selectedDriver);


    }
}
