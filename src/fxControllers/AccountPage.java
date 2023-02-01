package fxControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountPage {
    @FXML
    private AnchorPane anchorPane;


    public void changePass() {

    }

    public void goToMainPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AccountPage.class.getResource("/view/main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setTitle("Transport Logistics");
        stage.setScene(scene);
        stage.show();
    }

    public void logOut() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/login-page.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setTitle("Logistics");
        stage.setScene(scene);
        stage.show();
    }
}