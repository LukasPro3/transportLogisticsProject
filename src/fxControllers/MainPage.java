package fxControllers;

import hibernate.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    public ListView<Driver> driverList;
    public Tab driversTab;
    public ListView<Truck> freeTruckList;
    public ListView<Truck> assignedTruckList;
    public TableView cargoTable;
    public TableColumn<CargoTableParameters, String> columnAmount;
    public TableColumn<CargoTableParameters, String> columnPrice;
    public TableColumn<CargoTableParameters, String> columnCollection;
    public TableColumn<CargoTableParameters, String> columnDelivery;
    public TableColumn<CargoTableParameters, String> columnStatus;
    public TableColumn<CargoTableParameters, String> columnId;
    public ListView<Trip> tripList;
    public Button logOutButton;
    public Button detailsBtn;
    public Button editBtn;
    public Button deleteBtn;
    public Button createCargoBtn;
    public Button deleteCargoBtn;
    public Button assignDriverToTruckBtn;
    public Button editTruckBtn;
    public Button addTruckBtn;
    public Button deleteTruckBtn;
    public Button addTripBtn;
    public Button editTripBtn;
    public Button deleteTripBtn;
    public Button freeBtn;
    public MenuItem deleteItem;
    public MenuItem updateItem;
    public TreeView<Comment> commentTree;
    public Tab ForumTab;


    public ListView<Forum> forumList;
    public Tab main;
    public ListView<Trip> yourTripsList;
    public ListView<Truck> yourTruckList;
    public MenuItem addItem;

    @FXML
    private AnchorPane ap;

    private ObservableList<CargoTableParameters> data = FXCollections.observableArrayList();

    private EntityManagerFactory entityManagerFactory;
    private UserHib userHib;
    private User user;
    private EntityManager entityManager;
    private TruckHib truckHib;
    private CargoHib cargoHib;
    private TripHib tripHib;
    private CommentHib commentHib;
    private ForumHib forumHib;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.userHib = new UserHib(entityManagerFactory);
        this.truckHib = new TruckHib(entityManagerFactory);
        this.cargoHib = new CargoHib(entityManagerFactory);
        this.tripHib = new TripHib(entityManagerFactory);
        this.commentHib = new CommentHib(entityManagerFactory);
        this.forumHib = new ForumHib(entityManagerFactory);
        this.user = user;
        this.entityManager = entityManagerFactory.createEntityManager();
        System.out.println(user);

        fillAllLists();
        disableData();
    }

    private void disableData() {
        if (user.getUserRole() == UserRole.DRIVER) {
            driversTab.setDisable(true);
            addTripBtn.setDisable(true);
            editTruckBtn.setDisable(true);
            addTruckBtn.setDisable(true);
            createCargoBtn.setDisable(true);
            deleteCargoBtn.setDisable(true);
            deleteTripBtn.setDisable(true);
            deleteTruckBtn.setDisable(true);
            cargoTable.setEditable(false);
        } else if (user.getUserRole() == UserRole.MANAGER) {
            editBtn.setDisable(true);
            deleteBtn.setDisable(true);
            main.setDisable(true);
        } else {
            main.setDisable(true);
        }

    }

    private void fillAllLists() {
        driverList.getItems().clear();
        yourTruckList.getItems().clear();
        freeTruckList.getItems().clear();
        assignedTruckList.getItems().clear();
        tripList.getItems().clear();
        yourTripsList.getItems().clear();
        forumList.getItems().clear();
        cargoTable.getItems().clear();
        List<Driver> allDrivers = userHib.getAllDrivers();
        allDrivers.forEach(d -> driverList.getItems().add(d));
        List<Truck> freeTrucks = truckHib.getFreeTrucks();
        freeTrucks.forEach(f -> {
            if (f.getAssignedToId() == null) {
                freeTruckList.getItems().add(f);
            } else {
                if (f.getAssignedToId().getId() == user.getId()) {
                    yourTruckList.getItems().add(f);
                }
                assignedTruckList.getItems().add(f);
            }
        });
        List<Cargo> allCargo = cargoHib.getAllCargo();
        for (Cargo c : allCargo) {
            CargoTableParameters cargoTableParameters = new CargoTableParameters();
            cargoTableParameters.setCargoId(String.valueOf(c.getId()));
            cargoTableParameters.setCargoAmount(String.valueOf(c.getAmount()));
            cargoTableParameters.setCargoCollection(c.getCollectionPoint());
            cargoTableParameters.setCargoDelivery(c.getDeliveryPoint());
            cargoTableParameters.setCargoStatus(String.valueOf(c.getCurrentStatus()));
            cargoTableParameters.setCargoPrice(String.valueOf(c.getPrice()));
            data.add(cargoTableParameters);
        }
        cargoTable.setItems(data);
        List<Trip> allTrips = tripHib.getAllTrips();
        allTrips.forEach(t -> {
            if (t.getDriversId() != null) {
                if (t.getDriversId().getId() == user.getId()) {
                    yourTripsList.getItems().add(t);
                }
            }
            tripList.getItems().add(t);
        });
        List<Forum> forums = forumHib.getAllForums();
        forums.forEach(p -> forumList.getItems().add(p));

    }

    public void logOut() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/login-page.fxml"));
        Parent parent = fxmlLoader.load();
        LoginPage loginPage = fxmlLoader.getController();

        Scene scene = new Scene(parent);
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.show();
    }

    public void showDetails() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/driverDetails-page.fxml"));
        Parent parent = fxmlLoader.load();
        DriverDetailsPage driverDetailsPage = fxmlLoader.getController();
        if (driverList.getSelectionModel().getSelectedItem() != null) {
            driverDetailsPage.setData(entityManagerFactory, user, driverList.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(driverList.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("TransportLogistics");
            stage.setScene(scene);
            stage.showAndWait();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Select driver!");
            a.show();

        }

    }

    public void updateDriver() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/register-page.fxml"));
        Parent parent = fxmlLoader.load();
        RegisterPage registerPage = fxmlLoader.getController();
        if (driverList.getSelectionModel().getSelectedItem() != null) {
            registerPage.setData(entityManagerFactory, user, driverList.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(driverList.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("TransportLogistics");
            stage.setScene(scene);
            stage.showAndWait();
            fillAllLists();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Select driver!");
            a.show();

        }
    }

    public void deleteDriver() {
        if (driverList.getSelectionModel().getSelectedItem() != null) {
            User selectedDriver = driverList.getSelectionModel().getSelectedItem();
            UserHib.deleteUser(selectedDriver.getId());
            fillAllLists();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Select driver!");
            a.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargoTable.setEditable(true);
        columnId.setCellValueFactory(new PropertyValueFactory<>("cargoId"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("cargoAmount"));
        columnAmount.setCellFactory(TextFieldTableCell.forTableColumn());
        columnAmount.setOnEditCommit(t -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setCargoAmount(t.getNewValue());
                    Cargo cargo = cargoHib.getCargoById(Integer.parseInt(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoId()));
                    cargo.setAmount(Double.parseDouble(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoAmount()));
                    cargoHib.updateCargo(cargo);
                }
        );
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("cargoPrice"));
        columnPrice.setCellFactory(TextFieldTableCell.forTableColumn());
        columnPrice.setOnEditCommit(t -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setCargoPrice(t.getNewValue());
                    Cargo cargo = cargoHib.getCargoById(Integer.parseInt(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoId()));
                    cargo.setPrice(Double.parseDouble(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoPrice()));
                    cargoHib.updateCargo(cargo);
                }
        );

        columnCollection.setCellValueFactory(new PropertyValueFactory<>("cargoCollection"));
        columnCollection.setCellFactory(TextFieldTableCell.forTableColumn());
        columnCollection.setOnEditCommit(t -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setCargoCollection(t.getNewValue());
                    Cargo cargo = cargoHib.getCargoById(Integer.parseInt(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoId()));
                    cargo.setCollectionPoint(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoCollection());
                    cargoHib.updateCargo(cargo);
                }
        );

        columnDelivery.setCellValueFactory(new PropertyValueFactory<>("cargoDelivery"));
        columnDelivery.setCellFactory(TextFieldTableCell.forTableColumn());
        columnDelivery.setOnEditCommit(t -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setCargoDelivery(t.getNewValue());
                    Cargo cargo = cargoHib.getCargoById(Integer.parseInt(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoId()));
                    cargo.setDeliveryPoint(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoDelivery());
                    cargoHib.updateCargo(cargo);
                }
        );
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("cargoStatus"));
        columnStatus.setCellFactory(TextFieldTableCell.forTableColumn());
        columnStatus.setOnEditCommit(t -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setCargoStatus(t.getNewValue());
                    Cargo cargo = cargoHib.getCargoById(Integer.parseInt(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoId()));
                    cargo.setCurrentStatus(CargoStatus.valueOf(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoStatus()));
                    cargoHib.updateCargo(cargo);
                }
        );

    }

    public void assignDriverToTruck() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/assignDriver-page.fxml"));
        Parent parent = fxmlLoader.load();
        AssignDriverPage assignDriverPage = fxmlLoader.getController();
        if (freeTruckList.getSelectionModel().getSelectedItem() != null) {
            assignDriverPage.setData(entityManagerFactory, user, freeTruckList.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(freeTruckList.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("TransportLogistics");
            stage.setScene(scene);
            stage.showAndWait();
            fillAllLists();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Select driver!");
            a.show();

        }


    }


    public void viewTruckDetails() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/truck-page.fxml"));
        Parent parent = fxmlLoader.load();
        TruckPage truckPage = fxmlLoader.getController();
        truckPage.setData(entityManagerFactory, freeTruckList.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(freeTruckList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void createTruck() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/truck-page.fxml"));
        Parent parent = fxmlLoader.load();
        TruckPage truckPage = fxmlLoader.getController();
        truckPage.setData(entityManagerFactory);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(freeTruckList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
        fillAllLists();
    }

    public void deleteTruck() {
        Truck selectedTruck = freeTruckList.getSelectionModel().getSelectedItem();
        truckHib.deleteTruck(selectedTruck.getId());
        fillAllLists();
    }

    public void editTruck() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/truck-page.fxml"));
        Parent parent = fxmlLoader.load();
        TruckPage truckPage = fxmlLoader.getController();
        truckPage.setData(entityManagerFactory, freeTruckList.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(freeTruckList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
        fillAllLists();

    }

    public void createTrip() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainPage.class.getResource("/view/trip-page.fxml"));
        Parent parent = fxmlLoader.load();
        TripPage tripPage = fxmlLoader.getController();
        tripPage.setData(entityManagerFactory);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(tripList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
        fillAllLists();

    }

    public void editTrip() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/trip-page.fxml"));
        Parent parent = fxmlLoader.load();
        TripPage tripPage = fxmlLoader.getController();
        tripPage.setData(entityManagerFactory, tripList.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(tripList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
        fillAllLists();
    }

    public void deleteTrip() {
        Trip selectedTrip = tripList.getSelectionModel().getSelectedItem();
        if (selectedTrip.getDriversId() == null) {
            tripHib.deleteTrip(selectedTrip.getId());
            fillAllLists();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Trip is in process!");
            a.show();
        }
    }

    public void createCargo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainPage.class.getResource("/view/cargo-page.fxml"));
        Parent parent = fxmlLoader.load();
        CargoPage cargoPage = fxmlLoader.getController();
        cargoPage.setData(entityManagerFactory, user);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(cargoTable.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
        fillAllLists();
    }

    public void deleteCargo() {
    }

    public void unAssignTruck() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/assignDriver-page.fxml"));
        Parent parent = fxmlLoader.load();
        AssignDriverPage assignDriverPage = fxmlLoader.getController();
        if (assignedTruckList.getSelectionModel().getSelectedItem() != null) {
            assignDriverPage.setData(entityManagerFactory, assignedTruckList.getSelectionModel().getSelectedItem());
            fillAllLists();
            //            Scene scene = new Scene(parent);
//            Stage stage = new Stage();
//            stage.initOwner(freeTruckList.getScene().getWindow());
//            stage.initModality(Modality.WINDOW_MODAL);
//            stage.setTitle("TransportLogistics");
//            stage.setScene(scene);
//            stage.showAndWait();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Select driver!");
            a.show();

        }
    }


    public void createForum() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainPage.class.getResource("/view/forum-page.fxml"));
        Parent parent = fxmlLoader.load();
        ForumPage forumPage = fxmlLoader.getController();
        forumPage.setData(entityManagerFactory);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(forumList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
        fillAllLists();
    }


    public void loadComment() {
        List<Comment> comments = forumHib.getForumById(forumList.getSelectionModel().getSelectedItem().getId()).getComments();
        System.out.println(comments);
        commentTree.setRoot(new TreeItem<>(new Comment()));
        commentTree.setShowRoot(false);
        commentTree.getRoot().setExpanded(true);
        comments.forEach(comment -> addTreeItem(comment, commentTree.getRoot()));
    }

    private void addTreeItem(Comment comment, TreeItem parent) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parent.getChildren().add(treeItem);
        comment.getReplies().forEach(r -> addTreeItem(r, treeItem));
    }

    public void addComment() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainPage.class.getResource("/view/comment-page.fxml"));
        Parent parent = fxmlLoader.load();
        CommentPage commentPage = fxmlLoader.getController();
        if (commentTree.getSelectionModel().getSelectedItem() != null) {
            commentPage.setData(entityManagerFactory, commentTree.getSelectionModel().getSelectedItem().getValue());
        } else {
            commentPage.setData(entityManagerFactory, forumList.getSelectionModel().getSelectedItem());
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(forumList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
        fillAllLists();


    }
}
