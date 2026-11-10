package com.swiftwheels.model;

public class Car extends Vehicle {
    private final String serial;
    private final String fuel;

    public Car(int id, String serial, String fuel, String make, String model, int year, int kilometer, String color,
               String enginePower, String engineCapacity, int price, int stock) {
        super(id, make, model, year, kilometer, color, enginePower, engineCapacity, price, stock);
        this.serial = serial;
        this.fuel = fuel;
    }

    @Override
    public VehicleType getType() {
        return VehicleType.CAR;
    }

    @Override
    String[] detailParameters() {
        return new String[]{serial, fuel, null, null, null, null};
    }

    public String getSerial() {return serial;}
    public String getFuel() {return fuel;}
}
