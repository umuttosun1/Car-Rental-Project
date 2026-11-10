package com.swiftwheels.model;

/** The three vehicle kinds, with the code the database functions expect. */
public enum VehicleType {
    CAR("car", "default_car.png"),
    JEEP("jeep", "default_car.png"),
    MOTORCYCLE("motor", "default_motorcycle.png");

    private final String dbCode;
    private final String defaultImage;

    VehicleType(String dbCode, String defaultImage) {
        this.dbCode = dbCode;
        this.defaultImage = defaultImage;
    }

    public String dbCode() {
        return dbCode;
    }

    /** Classpath image (under /images) shown when a vehicle has no photo. */
    public String defaultImage() {
        return defaultImage;
    }
}
