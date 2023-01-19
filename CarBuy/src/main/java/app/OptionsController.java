package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static app.SellerController.currentTable;
import static app.SellerController.selectedVehicle;

public class OptionsController implements Initializable {
    @FXML
    private TextField hpChange;
    @FXML
    private TextField makeYearChange;
    @FXML
    private TextField fuelChange;

    public void changeButtonClick(ActionEvent event){
        if(!hpChange.getText().isEmpty() && !fuelChange.getText().isEmpty() && !makeYearChange.getText().isEmpty()) {

            Connection c = null;
            Statement s = null;
            ResultSet rs = null;
            PreparedStatement ps;

            try {
                c = ConnectionPool.getInstance().checkOut();
                s = c.createStatement();
                ps = c.prepareStatement("UPDATE vehicle SET horsePower=?, fuel=?, makeyear=? WHERE (idVehicle =?);");
                ps.setInt(1, Integer.parseInt(hpChange.getText()));
                ps.setString(2, fuelChange.getText());


                ps.setInt(3,Integer.parseInt(makeYearChange.getText()));
                ps.setInt(4, selectedVehicle.getId());
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

        currentTable.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hpChange.setPromptText(String.valueOf(selectedVehicle.getHorsePower()));
        fuelChange.setPromptText(selectedVehicle.getFuel());
        makeYearChange.setPromptText(String.valueOf(selectedVehicle.getMakeYear()));
    }
}
