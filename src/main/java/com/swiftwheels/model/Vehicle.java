package com.swiftwheels.model;

/** Common data of every rentable vehicle. {@code id == 0} means "not saved yet". */
public abstract class Vehicle {
    private int id;
    private String make;
    private String model;
    private int year;
    private int kilometer;
    private String color;
    private String enginePower;
    private String engineCapacity;
    private int price;
    private int stock;

    protected Vehicle(int id, String make, String model, int year, int kilometer, String color,
                      String enginePower, String engineCapacity, int price, int stock) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.kilometer = kilometer;
        this.color = color;
        this.enginePower = enginePower;
        this.engineCapacity = engineCapacity;
        this.price = price;
        this.stock = stock;
    }

    public abstract VehicleType getType();

    /**
     * Type-specific values in the order the {@code save_vehicle} SQL function expects:
     * seri, fuel, traction, motor type, cooling, cylinder (unused ones are {@code null}).
     */
    abstract String[] detailParameters();

    public int getId() {return id;}
    void setId(int id) {this.id = id;}
    public String getMake() {return make;}
    public String getModel() {return model;}
    public int getYear() {return year;}
    public int getKilometer() {return kilometer;}
    public String getColor() {return color;}
    public String getEnginePower() {return enginePower;}
    public String getEngineCapacity() {return engineCapacity;}
    public int getPrice() {return price;}
    public int getStock() {return stock;}

    @Override
    public String toString() {
        return make + " " + model;
    }
}
