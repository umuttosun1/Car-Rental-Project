package com.swiftwheels.ui;

import com.swiftwheels.db.Database;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;

import javax.swing.JOptionPane;
import java.awt.Component;
import java.sql.SQLException;

/** Consistent, user friendly error dialogs. */
final class Dialogs {

    private Dialogs() {
    }

    static void error(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Hata", JOptionPane.ERROR_MESSAGE);
    }

    static void info(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "SwiftWheels", JOptionPane.INFORMATION_MESSAGE);
    }

    static boolean confirm(Component parent, String message, String title) {
        return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    /** Shows the message raised by our PL/pgSQL code (RAISE EXCEPTION ...) without the technical noise. */
    static void databaseError(Component parent, SQLException e) {
        e.printStackTrace();
        error(parent, describe(e));
    }

    static String describe(SQLException e) {
        String state = e.getSQLState();
        if (state != null && state.startsWith("08")) {
            return "Veritabanına bağlanılamadı (" + Database.url() + ").\n"
                    + "PostgreSQL'in çalıştığından ve bağlantı ayarlarının doğru olduğundan emin olun.\n"
                    + "Ayar dosyası: " + Database.configLocation();
        }
        if (e instanceof PSQLException psql) {
            ServerErrorMessage server = psql.getServerErrorMessage();
            if (server != null && server.getMessage() != null) {
                return server.getMessage();
            }
        }
        return "Veritabanı hatası: " + e.getMessage();
    }
}
