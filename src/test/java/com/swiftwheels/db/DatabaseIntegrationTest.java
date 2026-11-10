package com.swiftwheels.db;

import com.swiftwheels.model.Car;
import com.swiftwheels.model.Customer;
import com.swiftwheels.model.CustomerRepository;
import com.swiftwheels.model.RentalRepository;
import com.swiftwheels.model.Vehicle;
import com.swiftwheels.model.VehicleRepository;
import com.swiftwheels.model.VehicleType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Runs against the real database configured in db.properties / defaults.
 * Skipped automatically when PostgreSQL is not reachable. Cleans up everything it creates.
 */
class DatabaseIntegrationTest {

    private final String mail = "it-" + UUID.randomUUID() + "@test.local";
    private int vehicleId;

    @BeforeAll
    static void requireDatabase() {
        boolean up;
        try (Connection ignored = Database.getConnection()) {
            up = true;
        } catch (SQLException e) {
            up = false;
        }
        assumeTrue(up, "PostgreSQL not reachable - integration tests skipped");
    }

    @AfterEach
    void cleanUp() throws SQLException {
        try (Connection c = Database.getConnection()) {
            if (vehicleId != 0) {
                exec(c, "DELETE FROM rented_vehicles WHERE vehicle_id = ?", vehicleId);
                exec(c, "DELETE FROM vehicles WHERE id = ?", vehicleId);
            }
            try (PreparedStatement s = c.prepareStatement(
                    "DELETE FROM rented_vehicles WHERE user_id IN (SELECT id FROM kullanici WHERE mail = ?)")) {
                s.setString(1, mail);
                s.executeUpdate();
            }
            try (PreparedStatement s = c.prepareStatement("DELETE FROM kullanici WHERE mail = ?")) {
                s.setString(1, mail);
                s.executeUpdate();
            }
        }
    }

    @Test
    void registerLoginAndLegacyPasswordUpgrade() throws SQLException {
        CustomerRepository.register("Int", "Test", "05001112233", mail, "secret12");
        // Locale.ROOT: with the Turkish default locale "i".toUpperCase() would be the dotted "İ"
        assertTrue(CustomerRepository.emailExists(mail.toUpperCase(Locale.ROOT)), "e-mail check is case-insensitive");
        assertTrue(CustomerRepository.authenticate(mail, "secret12").isPresent());
        assertTrue(CustomerRepository.authenticate(mail, "wrong").isEmpty());

        // simulate an account created by the old version (plain-text password)
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("UPDATE kullanici SET password = 'plain123' WHERE mail = ?")) {
            s.setString(1, mail);
            s.executeUpdate();
        }
        Optional<Customer> legacy = CustomerRepository.authenticate(mail, "plain123");
        assertTrue(legacy.isPresent(), "legacy plain-text password still works once");
        assertFalse(legacy.get().admin());
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT password FROM kullanici WHERE mail = ?")) {
            s.setString(1, mail);
            var rs = s.executeQuery();
            rs.next();
            assertTrue(rs.getString(1).startsWith("pbkdf2_sha256$"), "password was re-hashed on login");
        }
    }

    @Test
    void rentAndReturnKeepStockAndRevenueConsistent() throws SQLException {
        CustomerRepository.register("Int", "Test", "05001112233", mail, "secret12");
        int userId = CustomerRepository.authenticate(mail, "secret12").orElseThrow().id();
        long revenueBefore = RentalRepository.adminStats().totalRevenue();

        vehicleId = VehicleRepository.save(new Car(0, "Test", "Benzin", "Test", "Car", 2024, 0, "Mor",
                "1 HP", "1.0", 10, 1));
        RentalRepository.rent(userId, "Test address", vehicleId, 2, 20);
        assertEquals(0, stock(vehicleId));
        assertEquals(1, RentalRepository.activeRentalsOf(userId).getRowCount());

        SQLException outOfStock = assertThrows(SQLException.class,
                () -> RentalRepository.rent(userId, "x", vehicleId, 1, 10));
        assertTrue(outOfStock.getMessage().contains("stoğu"));

        RentalRepository.returnVehicle(userId, vehicleId);
        assertEquals(1, stock(vehicleId));
        assertEquals(0, RentalRepository.activeRentalsOf(userId).getRowCount());
        assertEquals(revenueBefore + 20, RentalRepository.adminStats().totalRevenue(),
                "returning a vehicle must not remove its revenue");
    }

    @Test
    void updatingAJeepKeepsFuelAndTractionInTheRightColumns() throws SQLException {
        vehicleId = VehicleRepository.save(new com.swiftwheels.model.Jeep(0, "S", "Dizel", "4x4",
                "Test", "Jeep", 2024, 0, "Mor", "1 HP", "1.0", 10, 1));
        VehicleRepository.save(new com.swiftwheels.model.Jeep(vehicleId, "S", "Benzin", "4x2",
                "Test", "Jeep", 2024, 0, "Mor", "1 HP", "1.0", 10, 1));
        var jeep = (com.swiftwheels.model.Jeep) VehicleRepository.findById(VehicleType.JEEP, vehicleId).orElseThrow();
        assertEquals("Benzin", jeep.getFuel());
        assertEquals("4x2", jeep.getTraction());
    }

    private static int stock(int id) throws SQLException {
        Vehicle v = VehicleRepository.findById(VehicleType.CAR, id).orElseThrow();
        return v.getStock();
    }

    private static void exec(Connection c, String sql, int id) throws SQLException {
        try (PreparedStatement s = c.prepareStatement(sql)) {
            s.setInt(1, id);
            s.executeUpdate();
        }
    }
}
