package fxControllers;

import hibernate.UserHib;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Driver;
import model.User;

import javax.persistence.EntityManagerFactory;

public class DriverDetailsPage {
    public TextField surnameField;
    public TextField statusField;
    public TextField nameField;
    public TextField assignedTruckField;
    public TextField assignedCargoField;
    public Button closeButton;
    public TextField phoneNumberField;
    public DatePicker dateOfBirthField;
    public TextField emailField;
    private EntityManagerFactory entityManagerFactory;
    private User user;
    private Driver selectedDriver;
    private UserHib userHib;

    public void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void setData(EntityManagerFactory entityManagerFactory, User user, Driver selectedDriver) {
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;
        this.selectedDriver = selectedDriver;
        this.userHib = new UserHib(entityManagerFactory);
        fillFields();
    }

    public void fillFields() {
        Driver driver = (Driver) userHib.getUserById(selectedDriver.getId());
        nameField.setText(driver.getFirstName());
        surnameField.setText(driver.getLastName());
        phoneNumberField.setText(driver.getPhoneNumber());
        emailField.setText(driver.getEmail());
        dateOfBirthField.setValue(driver.getBirthDate());

    }
}
