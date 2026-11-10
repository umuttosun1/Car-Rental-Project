package com.swiftwheels.model;

public class Jeep extends Vehicle {
    private final String serial;
    private final String fuel;
    private final String traction;

    public Jeep(int id, String serial, String fuel, String traction, String make, String model, int year, int kilometer,
                String color, String enginePower, String engineCapacity, int price, int stock) {
        super(id, make, model, year, kilometer, color, enginePower, engineCapacity, price, stock);
        this.serial = serial;
        this.fuel = fuel;
        this.traction = traction;
    }

    @Override
    public VehicleType getType() {
        return VehicleType.JEEP;
    }

    @Override
    String[] detailParameters() {
        return new String[]{serial, fuel, traction, null, null, null};
    }

    public String getSerial() {return serial;}
    public String getFuel() {return fuel;}
    public String getTraction() {return traction;}
}
