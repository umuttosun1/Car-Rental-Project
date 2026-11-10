package com.swiftwheels.ui;

import com.swiftwheels.db.Database;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Builds the real GUI-Designer forms (without showing a window) and clicks through the
 * pages that do not open dialogs. Catches broken form bindings, which would otherwise only
 * show up as a NullPointerException at start-up. Needs the demo data from database/seed.sql.
 */
class UiSmokeTest {

    @BeforeAll
    static void requirements() {
        assumeFalse(GraphicsEnvironment.isHeadless(), "needs a display");
        boolean up;
        try (Connection ignored = Database.getConnection()) {
            up = true;
        } catch (SQLException e) {
            up = false;
        }
        assumeTrue(up, "PostgreSQL not reachable");
    }

    @Test
    void loginFormIsWired() throws Exception {
        onEdt(() -> {
            LoginRegisterPanel login = new LoginRegisterPanel();
            assertNotNull(login.mainPanel);
            assertNotNull(field(login, "numberField"), "custom-created component");
            return null;
        });
    }

    @Test
    void vehiclesAccountAndContractsPagesLoad() throws Exception {
        onEdt(() -> {
            MainAppPanel app = new MainAppPanel(2, false); // demo@swiftwheels.com

            ((JButton) field(app, "vehicleButton")).doClick();
            assertEquals(4, cardCount(field(app, "carPanel")), "4 demo cars");
            assertEquals(3, cardCount(field(app, "suvPanel")), "3 demo SUVs");
            assertEquals(3, cardCount(field(app, "motorcyclePanel")), "3 demo motorcycles");

            ((JButton) field(app, "accountButton")).doClick();
            assertEquals("demo@swiftwheels.com", ((JTextArea) field(app, "emailArea")).getText());
            assertFalse(((JButton) field(app, "adminButton")).isVisible(), "customers get no admin button");
            assertFalse(((JTextArea) field(app, "adminKey")).isVisible(), "the old 'type admin' box is gone");

            ((JButton) field(app, "contractButton")).doClick();
            JTable rentals = (JTable) field(app, "myRentedVehicleTable");
            assertTrue(rentals.getRowCount() >= 1, "demo user has an active rental");
            return null;
        });
    }

    @Test
    void adminSeesAdminButton() throws Exception {
        onEdt(() -> {
            MainAppPanel app = new MainAppPanel(1, true);
            ((JButton) field(app, "accountButton")).doClick();
            assertTrue(((JButton) field(app, "adminButton")).isVisible());
            assertFalse(((JButton) field(app, "addVehicle1Button")).isVisible(), "admin mode starts OFF");
            return null;
        });
    }

    /** Cards live in a grid panel that is the only child of the list panel. */
    private static int cardCount(Object listPanel) {
        JPanel list = (JPanel) listPanel;
        assertEquals(1, list.getComponentCount());
        return ((JPanel) list.getComponent(0)).getComponentCount();
    }

    private static Object field(Object target, String name) throws ReflectiveOperationException {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return f.get(target);
    }

    private interface EdtTask {
        Object run() throws Exception;
    }

    private static void onEdt(EdtTask task) throws Exception {
        AtomicReference<Throwable> error = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            try {
                task.run();
            } catch (Throwable t) {
                error.set(t);
            }
        });
        if (error.get() instanceof Exception e) {
            throw e;
        }
        if (error.get() instanceof Error e) {
            throw e;
        }
    }
}
