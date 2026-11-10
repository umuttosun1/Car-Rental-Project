package com.swiftwheels.model;

import com.swiftwheels.db.Database;
import com.swiftwheels.security.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

/** Account related SQL. */
public final class CustomerRepository {

    private CustomerRepository() {
    }

    public static void register(String name, String surname, String phone, String mail, String password)
            throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("CALL account_insert(?, ?, ?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, phone);
            stmt.setString(4, mail);
            stmt.setString(5, PasswordHasher.hash(password));
            stmt.execute();
        }
    }

    /**
     * Returns the customer when mail + password match.
     * Accounts created by the old version stored plain-text passwords; those are verified once
     * and immediately re-saved as a hash.
     */
    public static Optional<Customer> authenticate(String mail, String password) throws SQLException {
        String sql = "SELECT id, fname, lname, phone, mail, password, is_admin FROM kullanici WHERE lower(mail) = lower(?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mail.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                String stored = rs.getString("password");
                boolean ok;
                if (PasswordHasher.isHash(stored)) {
                    ok = PasswordHasher.verify(password, stored);
                } else {
                    ok = stored.equals(password);
                    if (ok) {
                        upgradeLegacyPassword(conn, rs.getInt("id"), password);
                    }
                }
                if (!ok) {
                    return Optional.empty();
                }
                return Optional.of(new Customer(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"),
                        rs.getString("phone"), rs.getString("mail"), rs.getBoolean("is_admin")));
            }
        }
    }

    public static boolean emailExists(String mail) throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM kullanici WHERE lower(mail) = lower(?)")) {
            stmt.setString(1, mail.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /** Reads name / surname / phone / mail of a user. */
    public static Optional<String[]> readProfile(int userId) throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM account_read(?)")) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(new String[]{rs.getString("fname"), rs.getString("lname"),
                        rs.getString("phone"), rs.getString("mail")});
            }
        }
    }

    /** @param newPassword {@code null} or blank keeps the current password */
    public static void updateProfile(int userId, String name, String surname, String phone, String newPassword)
            throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("CALL account_update(?, ?, ?, ?, ?)")) {
            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setString(4, phone);
            if (newPassword == null || newPassword.isBlank()) {
                stmt.setNull(5, Types.VARCHAR);
            } else {
                stmt.setString(5, PasswordHasher.hash(newPassword));
            }
            stmt.execute();
        }
    }

    /** Throws with the database message (e.g. "you still have a rented vehicle") when not allowed. */
    public static void delete(int userId) throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("CALL account_delete(?)")) {
            stmt.setInt(1, userId);
            stmt.execute();
        }
    }

    private static void upgradeLegacyPassword(Connection conn, int userId, String password) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE kullanici SET password = ? WHERE id = ?")) {
            stmt.setString(1, PasswordHasher.hash(password));
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }
}
