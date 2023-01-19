package models;

public class Vehicle {
    private int horsePower, numberOfDoors, seatNumber, makeYear, load, id;
    private String transmission, fuel, registrationNumber;
    private VehicleType vehicleType;
    private Model model;
    private String date;

    public String getDate()
    {
        return this.date;
    }
    public void setDate(String date)
    {
        this.date=date;
    }
    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getMakeYear() {
        return makeYear;
    }

    public void setMakeYear(int makeYear) {
        this.makeYear = makeYear;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
    public int getId()
    {
        return this.id;
    }
    public Vehicle(int horsePower, int numberOfDoors, int seatNumber, int makeYear, int load, String transmission, String fuel, String registrationNumber, VehicleType vehicleType, Model model, int id) {
        this.horsePower = horsePower;
        this.numberOfDoors = numberOfDoors;
        this.seatNumber = seatNumber;
        this.makeYear = makeYear;
        this.load = load;
        this.transmission = transmission;
        this.fuel = fuel;
        this.registrationNumber = registrationNumber;
        this.vehicleType = vehicleType;
        this.model = model;
        this.id = id;
    }
}
