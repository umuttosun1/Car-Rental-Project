package com.swiftwheels.model;

public class Motorcycle extends Vehicle {
    private final String motorType;
    private final String cooling;
    private final String cylinders;

    public Motorcycle(int id, String motorType, String cooling, String cylinders, String make, String model, int year,
                      int kilometer, String color, String enginePower, String engineCapacity, int price, int stock) {
        super(id, make, model, year, kilometer, color, enginePower, engineCapacity, price, stock);
        this.motorType = motorType;
        this.cooling = cooling;
        this.cylinders = cylinders;
    }

    @Override
    public VehicleType getType() {
        return VehicleType.MOTORCYCLE;
    }

    @Override
    String[] detailParameters() {
        return new String[]{null, null, null, motorType, cooling, cylinders};
    }

    public String getMotorType() {return motorType;}
    public String getCooling() {return cooling;}
    public String getCylinders() {return cylinders;}
}
