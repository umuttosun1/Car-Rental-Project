package com.swiftwheels.model;

import com.swiftwheels.db.Database;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Rentals, returns and the admin dashboard numbers. */
public final class RentalRepository {

    /** Numbers shown on the admin panel (from the {@code admin} view). */
    public record AdminStats(long vehicleCount, long userCount, long activeRentals, long totalRevenue, long totalStock) {
    }

    private RentalRepository() {
    }

    public static void rent(int userId, String address, int vehicleId, int days, int fee) throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("CALL vehicle_rent(?, ?, ?, ?, ?)")) {
            stmt.setInt(1, userId);
            stmt.setString(2, address);
            stmt.setInt(3, vehicleId);
            stmt.setInt(4, days);
            stmt.setInt(5, fee);
            stmt.execute();
        }
    }

    public static void returnVehicle(int userId, int vehicleId) throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("CALL return_vehicle(?, ?)")) {
            stmt.setInt(1, userId);
            stmt.setInt(2, vehicleId);
            stmt.execute();
        }
    }

    /** Vehicles the user still has to return. */
    public static DefaultTableModel activeRentalsOf(int userId) throws SQLException {
        DefaultTableModel model = readOnlyModel("Id", "Make", "Model", "Fee", "Purchase Date", "Return Date");
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM get_rented_vehicles_by_user(?)")) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{rs.getInt("vehicle_id"), rs.getString("make"), rs.getString("model"),
                            rs.getInt("fee"), rs.getDate("purchase_date"), rs.getDate("return_date")});
                }
            }
        }
        return model;
    }

    /** Every rental ever made (admin view). */
    public static DefaultTableModel allRentals() throws SQLException {
        DefaultTableModel model = readOnlyModel("User ID", "Address", "Purchase Date", "Return Date",
                "Vehicle ID", "Fee", "Status");
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM get_all_rented_vehicles()")) {
            while (rs.next()) {
                Object userId = rs.getObject("user_id");
                Object vehicleId = rs.getObject("vehicle_id");
                model.addRow(new Object[]{userId == null ? "silinmiş" : userId, rs.getString("address"),
                        rs.getDate("purchase_date"), rs.getDate("return_date"),
                        vehicleId == null ? "silinmiş" : vehicleId, rs.getInt("fee"),
                        rs.getDate("returned_at") == null ? "Kirada" : "İade edildi (" + rs.getDate("returned_at") + ")"});
            }
        }
        return model;
    }

    public static AdminStats adminStats() throws SQLException {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM admin")) {
            rs.next();
            return new AdminStats(rs.getLong("vehicle_type_number"), rs.getLong("user_number"),
                    rs.getLong("rented_vehicle_number"), rs.getLong("total_turnover"), rs.getLong("vehicle_stock"));
        }
    }

    private static DefaultTableModel readOnlyModel(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}
