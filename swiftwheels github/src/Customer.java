import javax.swing.*;
import java.sql.*;
import java.util.Objects;

public class Customer {
    private static final String URL = "jdbc:postgresql://localhost:5432/YOUR_DATABASE";
    private static final String USER = "YOUR_USERNAME";
    private static final String PASSWORD = "YOUR_PASSWORD";
    public int userId;
    public String name;
    public String surname;
    public String phone;
    public String mail;
    public String password;
    public boolean readFileStat = false;
    public Customer() {} // Empty constructor

    public String checkInfos(String name, String surname, String number, String mail, String password) {
        String status = ""; // Hic bir sorun yoksa bosluk doner
        if (Objects.equals(name, "") || Objects.equals(surname, "") || Objects.equals(number, "") ||
                Objects.equals(mail, "") || Objects.equals(password, "")) {
            return "Hicbir Bolum Bos Birakilmamali !!";
        } else if (!(mail.contains("@") && mail.endsWith(".com"))) {
            return "Yanlis Mail Yazimi !!";
        }
        return status;
    }

    public void takeInfos(String name, String surname, String phone, String mail, String password) {
        this.password = password;
        this.mail = mail;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        writeFile(name, surname, phone, mail, password);
    }

    public void writeFile(String name, String surname, String phone, String mail, String password) {
        String insertSQL = "CALL account_iu(?, ?, ?, ?, ?, 'INSERT')";

        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, phone);
            stmt.setString(4, mail);
            stmt.setString(5, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while inserting data: " + e.getMessage());
        }
    }

    public void readFile(String emailText, String passwordText) {
        String selectSQL = "SELECT id, fname, lname, phone, mail, password FROM kullanici WHERE mail = ? AND password = ?";

        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {
            stmt.setString(1, emailText);
            stmt.setString(2, passwordText);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.userId = rs.getInt("id");
                this.name = rs.getString("fname");
                this.surname = rs.getString("lname");
                this.phone = rs.getString("phone");
                this.mail = rs.getString("mail");
                this.password = rs.getString("password");
                readFileStat = true;

            } else {
                readFileStat = false;
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching data: " + e.getMessage());
            readFileStat = false;
        }
    }

    public void updateFile(JTextArea name, JTextArea surname, JTextArea phone, JTextArea email, JTextArea password) {
        String updateSQL = "CALL account_iu(?, ?, ?, ?, ?, 'UPDATE')";

        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
            stmt.setString(1, name.getText());
            stmt.setString(2, surname.getText());
            stmt.setString(3, phone.getText());
            stmt.setString(4, email.getText());
            stmt.setString(5, password.getText());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating data: " + e.getMessage());
        }
    }

    public void deleteFile(int userId) {
        String deleteSQL = "CALL account_delete(?)";

        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {
            stmt.setInt(1, userId); // Kullanıcı ID'sini bağla
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting data: " + e.getMessage());
        }
    }

    public void loadUserInfoToTextArea(int userId, JTextArea name, JTextArea surname, JTextArea phone, JTextArea mail, JTextArea password) {
        String selectSQL = "SELECT * FROM account_read(?)";

        try (Connection conn = createConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setInt(1, userId); // Kullanıcı ID'sine göre sorgu
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                name.setText(rs.getString("fname"));
                surname.setText(rs.getString("lname"));
                phone.setText(rs.getString("phone"));
                mail.setText(rs.getString("mail"));
                password.setText(rs.getString("password"));
            }
        } catch (SQLException e) {
            name.setText("Error while fetching data: " + e.getMessage());
        }
    }

    public boolean isReadFileStat() {
        return readFileStat;
    }

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean isAccountDeleted(int userId) {
        String checkSQL = "SELECT COUNT(*) FROM kullanici WHERE id = ?";
        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(checkSQL)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Eğer sonuç 0 ise hesap silinmiş demektir
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Hata durumunda silinmedi olarak kabul et
    }

    public boolean isEmailExists(String email) {
        String selectSQL = "SELECT COUNT(*) FROM kullanici WHERE mail = ?";
        boolean emailExists = false;

        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    emailExists = rs.getInt(1) > 0; // Eğer sonuç 0'dan büyükse e-posta kayıtlı
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking email existence: " + ex.getMessage());
        }

        return emailExists;
    }

}
