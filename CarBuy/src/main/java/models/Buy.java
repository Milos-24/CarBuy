package models;

public class Buy {
    private String date;
    private int idVehicle, idAccount;

    public Buy(String date, int idVehicle) {
        this.date = date;
        this.idVehicle = idVehicle;
    }
}
