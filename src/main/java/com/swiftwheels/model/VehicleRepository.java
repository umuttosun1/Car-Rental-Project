package com.swiftwheels.model;

import com.swiftwheels.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** All vehicle SQL lives here; the UI never builds queries itself. */
public final class VehicleRepository {

    private VehicleRepository() {
    }

    public static List<Car> findAllCars() throws SQLException {
        return query(VehicleType.CAR, null, VehicleRepository::mapCar);
    }

    public static List<Jeep> findAllJeeps() throws SQLException {
        return query(VehicleType.JEEP, null, VehicleRepository::mapJeep);
    }

    public static List<Motorcycle> findAllMotorcycles() throws SQLException {
        return query(VehicleType.MOTORCYCLE, null, VehicleRepository::mapMotorcycle);
    }

    /** Fresh copy of one vehicle (e.g. to show the current stock). */
    public static Optional<Vehicle> findById(VehicleType type, int id) throws SQLException {
        RowMapper<? extends Vehicle> mapper = switch (type) {
            case CAR -> VehicleRepository::mapCar;
            case JEEP -> VehicleRepository::mapJeep;
            case MOTORCYCLE -> VehicleRepository::mapMotorcycle;
        };
        List<? extends Vehicle> rows = query(type, id, mapper);
        return rows.isEmpty() ? Optional.empty() : Optional.of(rows.get(0));
    }

    /** Inserts when {@code vehicle.getId() == 0}, otherwise updates. Returns the id. */
    public static int save(Vehicle vehicle) throws SQLException {
        String sql = "SELECT save_vehicle(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int i = 1;
            stmt.setString(i++, vehicle.getType().dbCode());
            if (vehicle.getId() == 0) {
                stmt.setNull(i++, Types.INTEGER);
            } else {
                stmt.setInt(i++, vehicle.getId());
            }
            stmt.setString(i++, vehicle.getMake());
            stmt.setString(i++, vehicle.getModel());
            stmt.setInt(i++, vehicle.getYear());
            stmt.setInt(i++, vehicle.getKilometer());
            stmt.setString(i++, vehicle.getColor());
            stmt.setString(i++, vehicle.getEnginePower());
            stmt.setString(i++, vehicle.getEngineCapacity());
            stmt.setInt(i++, vehicle.getPrice());
            stmt.setInt(i++, vehicle.getStock());
            for (String detail : vehicle.detailParameters()) {
                stmt.setString(i++, detail);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                int id = rs.getInt(1);
                vehicle.setId(id);
                return id;
            }
        }
    }

    public static void delete(VehicleType type, int id) throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("CALL delete_vehicle(?, ?)")) {
            stmt.setString(1, type.dbCode());
            stmt.setInt(2, id);
            stmt.execute();
        }
    }

    // ------------------------------------------------------------------

    @FunctionalInterface
    private interface RowMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    private static <T> List<T> query(VehicleType type, Integer id, RowMapper<T> mapper) throws SQLException {
        String sql = id == null
                ? "SELECT * FROM get_vehicles(?)"
                : "SELECT * FROM get_vehicle_details_by_type(?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (id == null) {
                stmt.setString(1, type.dbCode());
            } else {
                stmt.setInt(1, id);
                stmt.setString(2, type.dbCode());
            }
            List<T> result = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapper.map(rs));
                }
            }
            return result;
        }
    }

    private static Car mapCar(ResultSet rs) throws SQLException {
        return new Car(rs.getInt("id"), rs.getString("detail1"), rs.getString("detail2"),
                rs.getString("make"), rs.getString("model"), rs.getInt("year"), rs.getInt("km"),
                rs.getString("color"), rs.getString("engine_power"), rs.getString("engine_capacity"),
                rs.getInt("price"), rs.getInt("stock"));
    }

    private static Jeep mapJeep(ResultSet rs) throws SQLException {
        return new Jeep(rs.getInt("id"), rs.getString("detail1"), rs.getString("detail2"), rs.getString("detail3"),
                rs.getString("make"), rs.getString("model"), rs.getInt("year"), rs.getInt("km"),
                rs.getString("color"), rs.getString("engine_power"), rs.getString("engine_capacity"),
                rs.getInt("price"), rs.getInt("stock"));
    }

    private static Motorcycle mapMotorcycle(ResultSet rs) throws SQLException {
        return new Motorcycle(rs.getInt("id"), rs.getString("detail1"), rs.getString("detail2"), rs.getString("detail3"),
                rs.getString("make"), rs.getString("model"), rs.getInt("year"), rs.getInt("km"),
                rs.getString("color"), rs.getString("engine_power"), rs.getString("engine_capacity"),
                rs.getInt("price"), rs.getInt("stock"));
    }
}
