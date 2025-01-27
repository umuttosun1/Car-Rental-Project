import java.sql.*;
import java.util.ArrayList;

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
    public Vehicle(int id,String make, String model, int year, int kilometer, String color, String enginePower, String engineCapacity, int price, int stock) {
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
    public Vehicle() {
    }
    abstract void addToList();
    public String toString() {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter - Setter Methods
    public String getMake() {return make;}
    public void setMake(String make) {this.make = make;}
    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}
    public int getYear() {return year;}
    public void setYear(int year) {this.year = year;}
    public int getKilometer() {return kilometer;}
    public void setKilometer(int kilometer) {this.kilometer = kilometer;}
    public String getColor() {return color;}
    public void setColor(String color) {this.color = color;}
    public String getEnginePower() {return enginePower;}
    public void setEnginePower(String enginePower) {this.enginePower = enginePower;}
    public String getEngineCapacity() {return engineCapacity;}
    public void setEngineCapacity(String engineCapacity) {this.engineCapacity = engineCapacity;}
    public int getPrice() {return price;}
    public void setPrice(int price) {this.price = price;}
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    // End Getter - Setter Methods
}