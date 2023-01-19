package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Account;
import models.BrandName;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainMenuController
{
    public static Account loggedInUser;
    @FXML
    private CheckBox sellerCheckBox;

    @FXML
    private CheckBox buyerCheckBox;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPasswordField;

    public void loginButtonClick(ActionEvent event) throws IOException {
        if(!(buyerCheckBox.isSelected() && sellerCheckBox.isSelected())) {

            if(buyerCheckBox.isSelected())
            {

                Connection c = null;
                Statement s = null;
                ResultSet rs = null;
                PreparedStatement ps;

                try {
                    c = ConnectionPool.getInstance().checkOut();
                    s = c.createStatement();
                    ps=c.prepareStatement("select * from account inner join buyer using (idAccount) where username=? and password=?;");
                    ps.setString(1, usernameTextField.getText());
                    ps.setString(2, passwordPasswordField.getText());
                    rs = ps.executeQuery();


                    while (rs.next())
                    {
                        if(rs.getString(2).equals(usernameTextField.getText()) && rs.getString(3).equals(passwordPasswordField.getText()))
                        {
                            loggedInUser=new Account(usernameTextField.getText(), passwordPasswordField.getText(), rs.getInt(1));
                            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Buyer.fxml")));
                            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.setTitle("Buyer");
                            stage.show();
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
            else if (sellerCheckBox.isSelected()){
                Connection c = null;
                Statement s = null;
                ResultSet rs = null;
                PreparedStatement ps;

                try {
                    c = ConnectionPool.getInstance().checkOut();
                    s = c.createStatement();
                    ps=c.prepareStatement("select * from account inner join salesman using (idAccount) where username=? and password=?;");
                    ps.setString(1, usernameTextField.getText());
                    ps.setString(2, passwordPasswordField.getText());
                    rs = ps.executeQuery();

                    while (rs.next())
                    {
                        if(rs.getString(2).equals(usernameTextField.getText()) && rs.getString(3).equals(passwordPasswordField.getText()))
                        {
                            loggedInUser=new Account(usernameTextField.getText(), passwordPasswordField.getText(), rs.getInt(1));
                            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Seller.fxml")));
                            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.setTitle("Seller");
                            stage.show();
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
    }




}