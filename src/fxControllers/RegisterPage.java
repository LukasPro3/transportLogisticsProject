package fxControllers;

import hibernate.UserHib;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Driver;
import model.User;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.Objects;

import static model.UserRole.DRIVER;
import static model.UserRole.MANAGER;


public class RegisterPage<user> {
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField phoneNumberField;
    @FXML
    public TextField emailField;
    @FXML
    public DatePicker dateOfBirthField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField password2Field;
    @FXML
    public TextField loginField;
    @FXML
    public Button registerButton;
    @FXML
    public RadioButton managerField;
    @FXML
    public RadioButton driverField;
    public ButtonBar typeOfUser;
    public Label labelRegister;
    public DatePicker medicalField;
    public DatePicker licenseField;

    private EntityManagerFactory entityManagerFactory;
    private User currentUser;
    private Driver selectedDriver;
    private UserHib userHib;
    //EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("TransportLogistics");
    // UserHib userHib = new UserHib(entityManagerFactory);


    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, Driver selectedDriver) {
        this.currentUser = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.selectedDriver = selectedDriver;
        this.userHib = new UserHib(entityManagerFactory);
        fillFields();
    }

    private void fillFields() {
        Driver driver = (Driver) userHib.getUserById(selectedDriver.getId());
        loginField.setText(driver.getLogin());
        nameField.setText(driver.getFirstName());
        surnameField.setText(driver.getLastName());
        phoneNumberField.setText(driver.getPhoneNumber());
        emailField.setText(driver.getEmail());
        medicalField.setValue(driver.getMedicalSertificate());
        licenseField.setValue(driver.getDriversLicense());
        dateOfBirthField.setValue(driver.getBirthDate());
        typeOfUser.setDisable(true);
        registerButton.setOnAction(actionEvent -> updateUser(driver));
        labelRegister.setText("Edit info");
        registerButton.setText("Update");

    }

    public void updateUser(Driver driver) {
        driver.setLogin(loginField.getText());
        driver.setPassword(passwordField.getText());
        driver.setLastName(surnameField.getText());
        driver.setFirstName(nameField.getText());
        driver.setBirthDate(dateOfBirthField.getValue());
        driver.setPhoneNumber(phoneNumberField.getText());
        driver.setEmail(emailField.getText());
        driver.setDriversLicense(licenseField.getValue());
        driver.setMedicalSertificate(medicalField.getValue());
        if (Objects.equals(passwordField.getText(), password2Field.getText())) {
            userHib.updateUser(driver);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Something wrong !");
            a.show();
        }

    }

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.userHib = new UserHib(entityManagerFactory);
        medicalField.setDisable(true);
        licenseField.setDisable(true);
    }

    public void disableSelect() {
        if (managerField.isSelected()) {
            driverField.setDisable(true);
            medicalField.setDisable(true);
            licenseField.setDisable(true);
        } else if (driverField.isSelected()) {
            managerField.setDisable(true);
            medicalField.setDisable(false);
            licenseField.setDisable(false);
        } else if (!managerField.isSelected()) {
            driverField.setDisable(false);
            managerField.setDisable(false);
            medicalField.setDisable(true);
            licenseField.setDisable(true);
        }
    }

    public void createNewUser() throws IOException {
        User user = null;
        if (loginField.getText() == null || passwordField.getText() == null || surnameField.getText() == null || nameField.getText() == null || dateOfBirthField.getValue() == null || phoneNumberField.getText() == null || emailField.getText() == null) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("All fields must be filled");
            a.show();
        } else if (!passwordField.getText().equals(password2Field.getText())) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Passwords do not match :( ");
            a.show();
        } else if (managerField.isSelected()) {
            user = new User(nameField.getText(), surnameField.getText(), phoneNumberField.getText(), emailField.getText(), dateOfBirthField.getValue(), loginField.getText(), passwordField.getText(), MANAGER, false);
            userHib.createUser(user);
            launchLogin();
        } else if (driverField.isSelected() && medicalField.getValue() != null && licenseField.getValue() != null) {
            user = new Driver(nameField.getText(), surnameField.getText(), phoneNumberField.getText(), emailField.getText(), dateOfBirthField.getValue(), loginField.getText(), passwordField.getText(), DRIVER, false, medicalField.getValue(), licenseField.getValue());
            userHib.createUser(user);
            launchLogin();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Something wrong !");
            a.show();
        }

    }

    private void launchLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/login-page.fxml"));
        Parent parent = fxmlLoader.load();
        //LoginPage loginPage = fxmlLoader.getController();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.show();
    }
}