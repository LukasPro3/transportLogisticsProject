package fxControllers;

import hibernate.UserHib;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class LoginPage {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TransportLogistics");
    UserHib userHib = new UserHib(entityManagerFactory);

    public void validate() throws IOException {
        User user = userHib.getUserByLoginData(loginField.getText(), passwordField.getText());
        if (user != null) {
            openMainWindow(user);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Wrong login/password :( ");
            a.show();
        }
    }

    public void openMainWindow(User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainPage = fxmlLoader.getController();
        mainPage.setData(entityManagerFactory, user);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.show();
    }

    public void openRegister() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/register-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) loginField.getScene().getWindow();
        RegisterPage registerPage = fxmlLoader.getController();
        registerPage.setData(entityManagerFactory);
        stage.setTitle("Transport Logistics");
        stage.setScene(scene);
        stage.show();
    }

}