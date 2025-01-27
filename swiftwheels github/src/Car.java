import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Car extends Vehicle {
    private static final String URL = "jdbc:postgresql://localhost:5432/YOUR_DATABASE";
    private static final String USER = "YOUR_USERNAME";
    private static final String PASSWORD = "YOUR_PASSWORD";
    private String serial;
    private String fuel;
    public Car(int id,String serial, String fuel, String make, String model, int year, int kilometer, String color, String enginePower, String engineCapacity, int price, int stock) {
        super(id,make, model, year, kilometer, color, enginePower, engineCapacity, price, stock);
        this.serial = serial;
        this.fuel = fuel;
    }
    public Car() {}
    public Car getCarById(int carId) {
        String query = "SELECT * FROM get_vehicle_details_by_type(?, 'car');";

        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, carId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Car(
                        rs.getInt("id"),
                        rs.getString("detail1"),             // Seri numarası
                        rs.getString("detail2"),               // fuel
                        rs.getString("make"),               // Marka
                        rs.getString("model"),              // Model
                        rs.getInt("year"),                  // Üretim yılı
                        rs.getInt("km"),                    // Kilometre
                        rs.getString("color"),              // Renk
                        rs.getString("engine_power"),       // Motor gücü
                        rs.getString("engine_capacity"),    // Motor kapasitesi
                        rs.getInt("price"),                 // Fiyat
                        rs.getInt("stock")                  // Stok miktarı
                );
            }

        } catch (SQLException e) {
            System.err.println("Car alınırken hata oluştu: " + e.getMessage());
        }
        return null; // Eğer araç bulunamazsa null döndür
    }
    public ArrayList<Car> getAllCars() {
        ArrayList<Car> carList = new ArrayList<>();
        String query = "SELECT * FROM get_vehicles('car');";

        try (Connection conn = createConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id"); // ID'yi al
                String make = rs.getString("make");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                int kilometer = rs.getInt("km");
                String color = rs.getString("color");
                String enginePower = rs.getString("engine_power");
                String engineCapacity = rs.getString("engine_capacity");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");
                String serial = rs.getString("detail1");
                String fuel = rs.getString("detail2");

                // ID dahil edilen Car nesnesi oluşturuluyor
                carList.add(new Car(id, serial, fuel, make, model, year, kilometer, color, enginePower, engineCapacity, price, stock));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }
    public void deleteCar(int id) throws SQLException {
        String deleteSQL = "call delete_vehicle('car',?);";

        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public void updateCar(
            int vehicleId, JTextArea make, JTextArea model, JTextArea year, JTextArea km, JTextArea color,
            JTextArea enginePower, JTextArea engineCapacity, JTextArea price, JTextArea stock,
            JTextArea serial, JTextArea fuel) {

        String sql = "CALL add_update_vehicle('car', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, NULL, NULL, NULL);";

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
            stmt.setString(11, serial.getText());
            stmt.setString(12, fuel.getText());

            stmt.executeUpdate();
            System.out.println("Car başarıyla güncellendi.");

        } catch (SQLException e) {
            System.err.println("Car güncellenirken hata oluştu: " + e.getMessage());
        }
    }
    public void addCarToDB(
            String make, String model, int year, int kilometer, String color,
            String enginePower, String engineCapacity, int price, int stock,
            String serial, String fuel) {
        String sql = "CALL add_update_vehicle('car', NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, NULL, NULL, NULL);";

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
            stmt.setString(10, serial);
            stmt.setString(11, fuel);

            stmt.executeUpdate();
            System.out.println("Car başarıyla eklendi.");
        } catch (SQLException e) {
            System.err.println("Car eklenirken hata oluştu: " + e.getMessage());
        }
    }
    @Override
    void addToList() {}
    public String getSerial() {
        return serial;
    }
    public String getFuel() {
        return fuel;
    }
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
