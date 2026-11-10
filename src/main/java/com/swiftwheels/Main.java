package com.swiftwheels;

import com.swiftwheels.db.Database;
import com.swiftwheels.ui.LoginRegisterPanel;
import com.swiftwheels.ui.Theme;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Theme.install();
            if (!databaseReachable()) {
                System.exit(1);
            }
            LoginRegisterPanel.showWindow();
        });
    }

    /** Fail fast with a clear message instead of a half-working UI. */
    private static boolean databaseReachable() {
        try (Connection ignored = Database.getConnection()) {
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Veritabanına bağlanılamadı:\n" + e.getMessage()
                            + "\n\nBağlantı adresi: " + Database.url()
                            + "\nAyarları değiştirmek için şu dosyayı oluşturun:\n" + Database.configLocation()
                            + "\n(Örnek: db.properties.example)",
                    "SwiftWheels - Veritabanı", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
