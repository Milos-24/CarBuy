package app;

import com.almasb.fxgl.entity.action.Action;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

import static app.MainMenuController.loggedInUser;

public class SellerController implements Initializable {

    public static TableView<Vehicle> currentTable;
    public static Vehicle selectedVehicle;

    @FXML
    private TableView<Vehicle> vehicleTableView = new TableView<>();
    private ObservableList<Vehicle> vehiclesObservableList = FXCollections.observableArrayList();
    public void backButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("CarBuy");
        stage.show();
    }

    public void updateButtonClick(ActionEvent event) throws IOException {
        selectedVehicle = currentTable.getSelectionModel().getSelectedItem();
        if(selectedVehicle!=null) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Options.fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Options");
            stage.initModality(Modality.WINDOW_MODAL);

            stage.show();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        vehiclesObservableList.clear();
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        PreparedStatement ps;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            ps = c.prepareStatement("select * from vehicle inner join model using (idModel)" +
                    "inner join brandname using (idBrandName)" +
                    "inner join account using (idAccount) where username=? and removed=0;");

            ps.setString(1, loggedInUser.getUsername());
            rs = ps.executeQuery();
            while (rs.next()) {
                vehiclesObservableList.add(
                        new Vehicle(rs.getInt(6), //hp
                                rs.getInt(7),//doors
                                rs.getInt(10), //seats
                                rs.getInt(12),//makeyear
                                0,                      //load
                                rs.getString(8), //transmision
                                rs.getString(9),//fuel
                                rs.getString(11), //registration
                                new VehicleType(rs.getInt(5)),//id vehicle type
                                new Model(rs.getInt(3)),//id model
                                rs.getInt(4)));//idvehicle

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (s != null)
                try {
                    s.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            ConnectionPool.getInstance().checkIn(c);
        }

        vehicleTableView.setItems(vehiclesObservableList);
        TableColumn<Vehicle, String> idColumn = new TableColumn<>("BrandName");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel().getBrandName().getBrandName()));

        TableColumn<Vehicle, String> iddColumn = new TableColumn<>("ModelName");
        iddColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel().getModelName()));

        TableColumn<Vehicle, Integer> idddColumn = new TableColumn<>("HP");
        idddColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHorsePower()).asObject());

        TableColumn<Vehicle, String> iddddColumn = new TableColumn<>("Fuel");
        iddddColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFuel()));

        TableColumn<Vehicle, Integer> idddddColumn = new TableColumn<>("MakeYear");
        idddddColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMakeYear()).asObject());



        idColumn.setPrefWidth(100);

        vehicleTableView.getColumns().setAll(idColumn, iddColumn, idddColumn, iddddColumn, idddddColumn);

        currentTable = vehicleTableView;
    }

    public void removeButtonClick()
    {
        selectedVehicle = currentTable.getSelectionModel().getSelectedItem();
        if(selectedVehicle!=null) {
            Connection c = null;
            Statement s = null;
            ResultSet rs = null;
            PreparedStatement ps;

            try {
                c = ConnectionPool.getInstance().checkOut();
                s = c.createStatement();
                ps = c.prepareStatement("UPDATE vehicle SET removed=1 WHERE (idVehicle =?);");
                ps.setInt(1, selectedVehicle.getId());
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (s != null)
                    try {
                        s.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                ConnectionPool.getInstance().checkIn(c);
            }
        }
        currentTable.refresh();
    }

    public void addButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("CarDetails.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Car Details");
        stage.setScene(scene);
        stage.show();
    }

    public void myCarsClick(ActionEvent event)
    {
        vehiclesObservableList.clear();
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        PreparedStatement ps;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            ps = c.prepareStatement("select * from vehicle inner join model using (idModel)" +
                    "inner join brandname using (idBrandName)" +
                    "inner join account using (idAccount) where username=? and removed=0;");

            ps.setString(1, loggedInUser.getUsername());
            rs = ps.executeQuery();
            while (rs.next()) {
                vehiclesObservableList.add(
                        new Vehicle(rs.getInt(6), //hp
                                rs.getInt(7),//doors
                                rs.getInt(10), //seats
                                rs.getInt(12),//makeyear
                                0,                      //load
                                rs.getString(8), //transmision
                                rs.getString(9),//fuel
                                rs.getString(11), //registration
                                new VehicleType(rs.getInt(5)),//id vehicle type
                                new Model(rs.getInt(3)),//id model
                                rs.getInt(4)));//idvehicle

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (s != null)
                try {
                    s.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            ConnectionPool.getInstance().checkIn(c);
        }

        vehicleTableView.setItems(vehiclesObservableList);
        TableColumn<Vehicle, String> idColumn = new TableColumn<>("BrandName");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel().getBrandName().getBrandName()));

        TableColumn<Vehicle, String> iddColumn = new TableColumn<>("ModelName");
        iddColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel().getModelName()));

        TableColumn<Vehicle, Integer> idddColumn = new TableColumn<>("HP");
        idddColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHorsePower()).asObject());

        TableColumn<Vehicle, String> iddddColumn = new TableColumn<>("Fuel");
        iddddColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFuel()));

        TableColumn<Vehicle, Integer> idddddColumn = new TableColumn<>("MakeYear");
        idddddColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMakeYear()).asObject());



        idColumn.setPrefWidth(100);

        vehicleTableView.getColumns().setAll(idColumn, iddColumn, idddColumn, iddddColumn, idddddColumn);

        currentTable = vehicleTableView;
    }
}
