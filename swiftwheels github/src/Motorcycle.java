import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Motorcycle extends Vehicle {
    private static final String URL = "jdbc:postgresql://localhost:5432/YOUR_DATABASE";
    private static final String USER = "YOUR_USERNAME";
    private static final String PASSWORD = "YOUR_PASSWORD";
    private String type;
    private String cooling;
    private String cylinders;
    public Motorcycle(int id,String type, String cooling, String cylinders, String make, String model, int year, int kilometer, String color, String enginePower, String engineCapacity, int price, int stock) {
        super(id,make, model, year, kilometer, color, enginePower, engineCapacity, price, stock);
        this.type = type;
        this.cooling = cooling;
        this.cylinders = cylinders;
    }
    public Motorcycle() {}
    public Motorcycle getMotorcycleById(int motorcycleId) {
        String query = "SELECT * FROM get_vehicle_details_by_type(?, 'motor');";

        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, motorcycleId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Motorcycle(
                        rs.getInt("id"),
                        rs.getString("detail1"),             // Motor tipi
                        rs.getString("detail2"),          // Soğutma türü
                        rs.getString("detail3"),         // Silindir sayısı
                        rs.getString("make"),             // Marka
                        rs.getString("model"),            // Model
                        rs.getInt("year"),                // Üretim yılı
                        rs.getInt("km"),                  // Kilometre
                        rs.getString("color"),            // Renk
                        rs.getString("engine_power"),     // Motor gücü
                        rs.getString("engine_capacity"),  // Motor kapasitesi
                        rs.getInt("price"),               // Fiyat
                        rs.getInt("stock")                // Stok miktarı
                );
            }

        } catch (SQLException e) {
            System.err.println("Motorcycle alınırken hata oluştu: " + e.getMessage());
        }
        return null;
    }
    public ArrayList<Motorcycle> getAllMotorcycles() {
        ArrayList<Motorcycle> motorcycleList = new ArrayList<>();
        String query = "SELECT * FROM get_vehicles('motor');";

        try (Connection conn = createConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id"); // ID'yi al
                String make = rs.getString("make");
                String type = rs.getString("detail1");
                String cooling = rs.getString("detail2");
                String cylinders = rs.getString("detail3");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                int kilometer = rs.getInt("km");
                String color = rs.getString("color");
                String enginePower = rs.getString("engine_power");
                String engineCapacity = rs.getString("engine_capacity");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");

                // ID dahil edilen Motorcycle nesnesi oluşturuluyor
                motorcycleList.add(new Motorcycle(id, type, cooling, cylinders, make, model, year, kilometer, color, enginePower, engineCapacity, price, stock));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return motorcycleList;
    }
    public void deleteMotorcycle(int id) throws SQLException{
        String deleteSQL = "call delete_vehicle('motor',?);";

        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public void updateMotorcycle(
            int vehicleId, JTextArea make, JTextArea model, JTextArea year, JTextArea km, JTextArea color,
            JTextArea enginePower, JTextArea engineCapacity, JTextArea price, JTextArea stock,
            JTextArea type, JTextArea cooling, JTextArea cylinder) {

        String sql = "CALL add_update_vehicle('motor', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, NULL, NULL, ?, ?, ?);";

        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vehicleId);
            stmt.setString(2, make.getText());
            stmt.setString(3, model.getText());
            stmt.setInt(4, Integer.parseInt(year.getText()));
            stmt.setInt(5, Integer.parseInt(km.getText()));
            stmt.setString(6, color.getText());
            stmt.setString(7, enginePower.getText());
            stmt.setString(8, engineCapacity.getText());
            stmt.setInt(9, Integer.parseInt(price.getText()));
            stmt.setInt(10, Integer.parseInt(stock.getText()));
            stmt.setString(11, type.getText());
            stmt.setString(12, cooling.getText());
            stmt.setString(13, cylinder.getText());

            stmt.executeUpdate();
            System.out.println("Motorcycle başarıyla güncellendi.");

        } catch (SQLException e) {
            System.err.println("Motorcycle güncellenirken hata oluştu: " + e.getMessage());
        }
    }
    public void addMotorToDB(
            String make, String model, int year, int kilometer, String color,
            String enginePower, String engineCapacity, int price, int stock,
            String motorType, String cooling, String cylinder) {
        String sql = "CALL add_update_vehicle('motor', NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, NULL, NULL, ?, ?, ?)";

        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, make);
            stmt.setString(2, model);
            stmt.setInt(3, year);
            stmt.setInt(4, kilometer);
            stmt.setString(5, color);
            stmt.setString(6, enginePower);
            stmt.setString(7, engineCapacity);
            stmt.setInt(8, price);
            stmt.setInt(9, stock);
            stmt.setString(10, motorType);
            stmt.setString(11, cooling);
            stmt.setString(12, cylinder);

            stmt.executeUpdate();
            System.out.println("Motor başarıyla eklendi.");
        } catch (SQLException e) {
            System.err.println("Motor eklenirken hata oluştu: " + e.getMessage());
        }
    }
    @Override
    void addToList() {}

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    @Override
    public String toString() {
        return "Make: " + getMake() + ", Model: " + getModel();
    }

    // Getter and Setter methods
    public String getType() {
        return type;
    }

    public String getCooling() {
        return cooling;
    }

    public String getCylinders() {
        return cylinders;
    }
}
