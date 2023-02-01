package fxControllers;

import hibernate.CargoHib;
import hibernate.TripHib;
import hibernate.UserHib;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Cargo;
import model.Trip;
import model.User;

import javax.persistence.EntityManagerFactory;
import java.time.LocalTime;
import java.util.Objects;

public class TripPage {


    public TextField driversIdField;
    public TextField stopLocationField;
    public TextField stopTimeField;
    public TextField destinationField;
    public TextField startField;
    public TextField cargoIdField;
    public Button createButton;

    private EntityManagerFactory entityManagerFactory;
    private Trip selectedTrip;
    private TripHib tripHib;
    private CargoHib cargoHib;

    public void setData(EntityManagerFactory entityManagerFactory, Trip selectedTrip) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedTrip = selectedTrip;
        this.tripHib = new TripHib(entityManagerFactory);
        this.cargoHib = new CargoHib(entityManagerFactory);
        fillFields();
    }

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.tripHib = new TripHib(entityManagerFactory);
        this.cargoHib = new CargoHib(entityManagerFactory);
    }

    private void fillFields() {
        Trip trip = tripHib.getTripById(selectedTrip.getId());
        //driversIdField.setText(String.valueOf(trip.getDriversId().getId()));
        try {
            driversIdField.setText(String.valueOf(trip.getDriversId().getId()));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        startField.setText(trip.getStartPoint());
        stopTimeField.setText(String.valueOf(trip.getStopTime()));
        stopLocationField.setText(trip.getStopLocation());
        destinationField.setText(trip.getDestination());
        cargoIdField.setText(String.valueOf(trip.getCargoId().getId()));
        createButton.setOnAction(actionEvent -> {
            updateTrip(trip);
        });
        createButton.setText("Update");
    }

    private void updateTrip(Trip trip) {
        User user = null;
        if (!Objects.equals(driversIdField.getText(), "")) {
            user = UserHib.getUserById(Integer.parseInt(driversIdField.getText()));
        }
        trip.setDriversId(user);
        trip.setStartPoint(startField.getText());
        trip.setStopLocation(stopLocationField.getText());
        trip.setStopTime(LocalTime.parse(stopTimeField.getText()));
        trip.setDestination(destinationField.getText());
        trip.setCargoId(cargoHib.getCargoById(Integer.parseInt(cargoIdField.getText())));
        Cargo cargo = cargoHib.getCargoById(Integer.parseInt(cargoIdField.getText()));
        cargo.setAssignedTrip(trip);
        cargo.setAssignedTo(user);
        cargoHib.updateCargo(cargo);
        tripHib.updateTrip(trip);

    }

    public void createTrip() {
        Trip trip = null;
        Cargo cargo = cargoHib.getCargoById(Integer.parseInt(cargoIdField.getText()));
        if (Objects.equals(driversIdField.getText(), "")) {
            trip = new Trip(cargo, startField.getText(), destinationField.getText(), LocalTime.parse(stopTimeField.getText()), stopLocationField.getText(), null);
            cargo.setAssignedTrip(trip);
        } else {
            User user = UserHib.getUserById(Integer.parseInt(driversIdField.getText()));
            trip = new Trip(cargo, startField.getText(), destinationField.getText(), LocalTime.parse(stopTimeField.getText()), stopLocationField.getText(), user);
            cargo.setAssignedTrip(trip);
            cargo.setAssignedTo(user);
        }

        tripHib.createTrip(trip);
        cargoHib.updateCargo(cargo);
    }
}
