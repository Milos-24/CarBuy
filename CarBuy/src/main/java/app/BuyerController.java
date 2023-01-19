package app;

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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Model;
import models.SalesMan;
import models.Vehicle;
import models.VehicleType;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

import static app.MainMenuController.loggedInUser;


public class BuyerController implements Initializable {
    @FXML
    private TextField seller;
    @FXML
    private TextField carModel;
    @FXML
    private TextField carBrand;

    @FXML
    private Button buy;

    private ObservableList<Vehicle> vehiclesObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Vehicle> vehicleTableView = new TableView<>();
    public static TableView<Vehicle> currentTable;


    @FXML
    public void searchClick(ActionEvent event) {
        vehiclesObservableList.clear();
        buy.setDisable(false);
        if (carModel.getText() != null || carBrand.getText()!=null || seller.getText()!=null) {
            if (seller.getText().isEmpty()) {
                Connection c = null;
                Statement s = null;
                ResultSet rs = null;
                PreparedStatement ps;

                try {
                    c = ConnectionPool.getInstance().checkOut();
                    s = c.createStatement();
                    if (carModel.getText().isEmpty()) {
                        ps = c.prepareStatement("select * from vehicle inner join model using (idModel) inner join brandname " +
                                "using (idBrandName) where brandName=? and removed=0;");
                        ps.setString(1, carBrand.getText());

                    } else if (carBrand.getText().isEmpty()) {
                        ps = c.prepareStatement("select * from vehicle inner join model using (idModel) inner join brandname " +
                                "using (idBrandName) where modelName=? and removed=0;");
                        ps.setString(1, carModel.getText());

                    } else {
                        ps = c.prepareStatement("select * from vehicle inner join model using (idModel) inner join brandname " +
                                "using (idBrandName) where brandName=? and modelName=? and removed=0;");
                        ps.setString(1, carBrand.getText());
                        ps.setString(2, carModel.getText());
                    }
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        vehiclesObservableList.add(new Vehicle(rs.getInt(6), rs.getInt(7),
                                rs.getInt(10), rs.getInt(12),
                                0, rs.getString(8), rs.getString(9),
                                rs.getString(11), new VehicleType(rs.getInt(5)),
                                new Model(rs.getInt(2)),
                                rs.getInt(4)));

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
            } else {
                Connection c = null;
                Statement s = null;
                ResultSet rs = null;
                PreparedStatement ps;

                try {
                    c = ConnectionPool.getInstance().checkOut();
                    s = c.createStatement();

                    if (carBrand.getText().isEmpty() && carModel.getText().isEmpty()) {
                        ps = c.prepareStatement("select * from vehicle inner join model using (idModel) " +
                                "inner join brandname using (idBrandName)" +
                                " inner join account using (idAccount) where username=? and removed=0;");

                        ps.setString(1, seller.getText());

                    }
                    else if (carModel.getText().isEmpty()) {
                        ps = c.prepareStatement("select * from vehicle inner join model using (idModel)" +
                                " inner join brandname using (idBrandName)" +
                                " inner join account using (idAccount) where username=? and brandname=? and removed=0;");
                        ps.setString(1, seller.getText());
                        ps.setString(2, carBrand.getText());

                    } else if (carBrand.getText().isEmpty()) {
                        ps = c.prepareStatement("select * from vehicle inner join model using (idModel)" +
                                " inner join brandname using (idBrandName)" +
                                " inner join account using (idAccount) where username=? and modelname=? and removed=0;");
                        ps.setString(1, seller.getText());
                        ps.setString(2, carModel.getText());

                    }
                    else {
                        ps = c.prepareStatement("select * from vehicle inner join model using (idModel)" +
                                " inner join brandname using (idBrandName)" +
                                " inner join account using (idAccount) where username=? and modelname=? and brandname=? and removed=0;");
                        ps.setString(1, seller.getText());
                        ps.setString(2, carModel.getText());
                        ps.setString(3, carBrand.getText());
                    }

                    rs = ps.executeQuery();


                    while (rs.next()) {
                        vehiclesObservableList.add(
                                new Vehicle(
                                        rs.getInt(6), //hp
                                        rs.getInt(7), //doors
                                        rs.getInt(10), //seats
                                        rs.getInt(12),  //makeyear
                                         0,                         //load
                                        rs.getString(8), //transmission
                                        rs.getString(9),    //fuel
                                        rs.getString(11),   //registration
                                        new VehicleType(rs.getInt(5)), //idvehicletype
                                         new Model(rs.getInt(3)),
                                         rs.getInt(4)   ));     //id model

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
            }
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

        currentTable=vehicleTableView;
        currentTable.refresh();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        vehiclesObservableList.clear();
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            rs = s.executeQuery("select * from vehicle where removed=0");

            while (rs.next()) {
                vehiclesObservableList.add(new Vehicle(rs.getInt(5), rs.getInt(6),
                        rs.getInt(9), rs.getInt(11),
                        0, rs.getString(7), rs.getString(8),
                        rs.getString(10), new VehicleType(rs.getInt(3)),
                        new Model(rs.getInt(4)),
                        rs.getInt(1)));

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

        currentTable=vehicleTableView;
        currentTable.refresh();
    }

    public void backButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("CarBuy");
        stage.show();
    }

    public void buyButtonClick()
    {
        Vehicle selectedVehicle = currentTable.getSelectionModel().getSelectedItem();
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = ConnectionPool.getInstance().checkOut();
            ps = c.prepareStatement("insert into buy (idAccount, idVehicle, date) values (?, ?, ?)");
            ps.setInt(1, loggedInUser.getId());
            ps.setInt(2, selectedVehicle.getId());
            ps.setString(3, String.valueOf(java.time.LocalDate.now()));


            ps.executeUpdate();
            removeBoughtCar(selectedVehicle.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (c != null)
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    public void allCarsButtonClick()
    {
        buy.setDisable(false);
        vehiclesObservableList.clear();
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            rs = s.executeQuery("select * from vehicle where removed=0");

            while (rs.next()) {
                vehiclesObservableList.add(new Vehicle(rs.getInt(5), rs.getInt(6),
                        rs.getInt(9), rs.getInt(11),
                        0, rs.getString(7), rs.getString(8),
                        rs.getString(10), new VehicleType(rs.getInt(3)),
                        new Model(rs.getInt(4)),
                        rs.getInt(1)));

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

        currentTable=vehicleTableView;
        currentTable.refresh();

    }

    public void myCarsClick()
    {
        buy.setDisable(true);
        vehiclesObservableList.clear();
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        PreparedStatement ps=null;
        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            ps = c.prepareStatement("select * from buy inner join vehicle using(idVehicle) where buy.idAccount=?");
            ps.setInt(1, loggedInUser.getId());
            rs=ps.executeQuery();

            while (rs.next()) {
                Vehicle tmp = new Vehicle(rs.getInt(8), //hp
                        rs.getInt(9),//doors
                        rs.getInt(12), //seats
                        rs.getInt(14),//makeyear
                        0,                      //load
                        rs.getString(10), //transmision
                        rs.getString(11),//fuel
                        rs.getString(13), //registration
                        new VehicleType(rs.getInt(6)),//id vehicle type
                        new Model(rs.getInt(7)),//id model
                        rs.getInt(1));
                tmp.setDate(rs.getString(4));
                vehiclesObservableList.add(tmp);

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

        TableColumn<Vehicle, String> dateColumn = new TableColumn<>("DateBought");
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));


        idColumn.setPrefWidth(100);

        vehicleTableView.getColumns().setAll(idColumn, iddColumn, idddColumn, iddddColumn, idddddColumn, dateColumn);



        currentTable=vehicleTableView;
        currentTable.refresh();

    }

    public void removeBoughtCar(int id) {

        Connection c = null;
        Statement s = null;
        PreparedStatement ps;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            ps = c.prepareStatement("UPDATE vehicle SET removed=1 WHERE (idVehicle =?);");
            ps.setInt(1, id);
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

        currentTable.refresh();
    }

}
