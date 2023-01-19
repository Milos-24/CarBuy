package app;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Model;
import models.Vehicle;
import models.VehicleType;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static app.MainMenuController.loggedInUser;

public class CarDetailsController implements Initializable {
    @FXML
    private ComboBox<Integer> doorsComboBox;
    @FXML
    private ComboBox<Integer> seatsComboBox;
    @FXML
    private ComboBox<String> transmissionComboBox;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField brandTextField;
    @FXML
    private TextField modelTextField;
    @FXML
    private TextField hpTextField;
    @FXML
    private TextField registrationTextField;
    @FXML
    private TextField makeYearTextField;
    @FXML
    private ComboBox<String> fuelComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transmissionComboBox.getItems().add("Automatski");
        transmissionComboBox.getItems().add("Manuelac");

        seatsComboBox.getItems().add(2);
        seatsComboBox.getItems().add(4);
        seatsComboBox.getItems().add(5);

        doorsComboBox.getItems().add(2);
        doorsComboBox.getItems().add(4);

        typeComboBox.getItems().add("Limuzina");
        typeComboBox.getItems().add("Hecbek");
        typeComboBox.getItems().add("Karavan");
        typeComboBox.getItems().add("Kabriolet");

        fuelComboBox.getItems().add("Benzin");
        fuelComboBox.getItems().add("Dizel");

    }

    public void addButtonClick(ActionEvent event)
    {
        if(!(doorsComboBox.getSelectionModel().isEmpty() || seatsComboBox.getSelectionModel().isEmpty()
                || typeComboBox.getSelectionModel().isEmpty() || transmissionComboBox.getSelectionModel().isEmpty()
                || brandTextField.getText().isEmpty() ||  modelTextField.getText().isEmpty() || hpTextField.getText().isEmpty()
                || registrationTextField.getText().isEmpty() || makeYearTextField.getText().isEmpty() || fuelComboBox.getSelectionModel().isEmpty()))
        {
            Connection c = null;
            Statement s = null;
            ResultSet rs = null;
            PreparedStatement ps;

            try {
                c = ConnectionPool.getInstance().checkOut();
                s = c.createStatement();
                ps = c.prepareStatement("select * from model inner join brandname using (idBrandName);");

                rs = ps.executeQuery();
                while (rs.next()) {
                    if(rs.getString(4).equals(brandTextField.getText()) && rs.getString(3).equals(modelTextField.getText()) && Integer.parseInt(hpTextField.getText())>0 && Integer.parseInt(makeYearTextField.getText())>1900)
                    {
                        this.addCar(rs.getInt(2));
                        break;
                    }

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

    private int getIdType(String type)
    {
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        PreparedStatement ps;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            ps = c.prepareStatement("select * from vehicletype;");

            rs = ps.executeQuery();
            while (rs.next()) {
                if(rs.getString(2).equals(type))
                {
                    return rs.getInt(1);
                }

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

        return 0;
    }
    private void addCar(int idModel)
    {
        int idVehicleType=getIdType(typeComboBox.getSelectionModel().getSelectedItem());

        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        PreparedStatement ps;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            ps = c.prepareStatement("insert into vehicle (idAccount, idVehicleType, idModel, horsePower, numberOfDoors, transmission, fuel, seatNumber, registrationNumber, makeYear, vehicle.load, removed) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setInt(1, loggedInUser.getId());
            ps.setInt(2, idVehicleType);
            ps.setInt(3, idModel);
            ps.setInt(4, Integer.parseInt(hpTextField.getText()));
            ps.setInt(5, doorsComboBox.getSelectionModel().getSelectedItem());
            ps.setString(6, transmissionComboBox.getSelectionModel().getSelectedItem());
            ps.setString(7, fuelComboBox.getSelectionModel().getSelectedItem());
            ps.setInt(8, seatsComboBox.getSelectionModel().getSelectedItem());
            ps.setString(9, registrationTextField.getText());
            System.out.println(Integer.parseInt(makeYearTextField.getText()));
            ps.setInt(10, Integer.parseInt(makeYearTextField.getText()));
            ps.setInt(11,0);
            ps.setInt(12, 0);
            ps.executeUpdate();

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
