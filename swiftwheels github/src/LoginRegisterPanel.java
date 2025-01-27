import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.Objects;
public class LoginRegisterPanel {
    Customer customer = new Customer();
    public int userId;
    public String emailText;
    innerAppPanel innerAppPanel = new innerAppPanel(userId);
    JPanel mainPanel;
    private JLabel logoLabel;
    public JTextField emailLogField;
    private JPasswordField passwordLoginField;
    private JButton LoginButton;
    private JButton RegisterButton;
    private JPanel LogoPanel;
    private JPanel InputPanel;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField emailRegField;
    private JFormattedTextField numberField;
    private JPanel RegPanel;
    private JButton createAccountButton;
    private JPasswordField passwordRegField;
    private JCheckBox passwordShowCheckBox;
    private JCheckBox passwordShowCheckBox1;
    private JPanel parentPanel;
    private JLabel warningLabel;
    private JButton backButton;

    public LoginRegisterPanel() {
        createAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        RegisterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LoginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        RegisterButton.addActionListener(e -> {
            backButton.setVisible(true);
            createRegisterPanel();
        });//Register sayfasina gider
        passwordShowCheckBox1.addActionListener(e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            passwordLoginField.setEchoChar(checkBox.isSelected() ? '\0' : '•');
        });// Giriş yapma ekranında şifre gösterme
        passwordShowCheckBox.addActionListener(e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            passwordRegField.setEchoChar(checkBox.isSelected() ? '\0' : '•');
        });//Kayıt olma yerinde Şifre Gösterme
        createAccountButton.addActionListener(e -> {
            parentPanel.removeAll();
            parentPanel.add(InputPanel);
            parentPanel.repaint();
            parentPanel.revalidate();
            warningLabel.setVisible(false);

            // E-posta kontrolü
            boolean emailExists = customer.isEmailExists(emailRegField.getText());

            // Kullanıcı giriş doğrulaması
            String stringPassword = new String(passwordRegField.getPassword());
            String status = customer.checkInfos(
                    nameField.getText(),
                    surnameField.getText(),
                    numberField.getText(),
                    emailRegField.getText(),
                    stringPassword
            );

            if (Objects.equals(status, "")) {
                if (!emailExists) {
                    // Telefon numarasını temizle ve kaydet
                    String cleanedNumber = numberField.getText().replaceAll("[^0-9]", "");
                    customer.takeInfos(
                            nameField.getText(),
                            surnameField.getText(),
                            cleanedNumber,
                            emailRegField.getText(),
                            stringPassword
                    );
                    // Tüm giriş alanlarını temizle
                    nameField.setText("");
                    surnameField.setText("");
                    numberField.setText("");
                    emailRegField.setText("");
                    passwordRegField.setText("");

                    JOptionPane.showMessageDialog(createAccountButton, "Your Account Created!");
                } else {
                    // E-posta zaten kayıtlı
                    createRegisterPanel();
                    warningLabel.setText("E-Mail already exist");
                    warningLabel.setVisible(true);
                }
            } else {
                // Kullanıcı giriş doğrulaması başarısız
                createRegisterPanel();
                warningLabel.setText(status);
                warningLabel.setVisible(true);
            }
        });//Kayıt ol butonuna basınca ana giriş ekranına atıyor. Eğer geçerli bir e postaysa
        LoginButton.addActionListener(e -> {
            StringBuilder passwordText = new StringBuilder();
            for (char item : getPasswordLoginField()) {
                passwordText.append(item); // Kullanıcı şifresini al
            }

            emailText = emailLogField.getText(); // Kullanıcı e-posta adresini al
            String password = passwordText.toString(); // Şifreyi String olarak al

            // Kullanıcı doğrulaması için customer nesnesini kullan
            customer.readFile(emailText, password); // Veritabanından doğrulama işlemi

            // Doğrulama başarılıysa kullanıcı ID'sini al
            innerAppPanel.userId = customer.userId;

            if (customer.isReadFileStat()) {
                // Giriş başarılıysa yeni paneli aç
                closeAllFrames(); // Tüm mevcut pencereleri kapat
                JFrame frame = new JFrame("innerAppPanel");
                frame.setContentPane(new innerAppPanel(innerAppPanel.userId).mainPanel1);
                frame.setPreferredSize(new Dimension(1250, 800));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } else {
                // E-posta veya şifre yanlışsa hata mesajı göster
                JOptionPane.showMessageDialog(createAccountButton, "Mail or Password is incorrect!");
            }
        });// Burada veritabanindan yazılan verilerin doğru olup olmadığını kontrol ediyoruz doğruysa ana ekrana atıyor yanlışsa
        backButton.addActionListener(e-> {
            parentPanel.removeAll();
            parentPanel.add(InputPanel);
            parentPanel.repaint();
            parentPanel.revalidate();
            warningLabel.setVisible(false);
            backButton.setVisible(false);
        });// Login sayfasina gider
    }
    public void createRegisterPanel() {
        parentPanel.removeAll();
        parentPanel.add(RegPanel);
        parentPanel.repaint();
        parentPanel.revalidate();
        LogoPanel.setPreferredSize(new Dimension(200, 100));
        parentPanel.setPreferredSize(new Dimension(200, 700));
    }
    private void createUIComponents() {
        logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon(new ImageIcon("images/SwiftWheels.png").getImage().getScaledInstance(400, 420, Image.SCALE_SMOOTH));
        logoLabel.setIcon(logoIcon);
        try {
            MaskFormatter maskFormatter = new MaskFormatter("0(5##) ###-####");
            maskFormatter.setPlaceholderCharacter('_');
            maskFormatter.setValidCharacters("0123456789");
            maskFormatter.setAllowsInvalid(false);
            numberField = new JFormattedTextField(maskFormatter);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }//Logo fotoğrafımız
    static void closeAllFrames() {//ana paneli kapatmak için metot
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame instanceof JFrame) {
                frame.dispose();
            }
        }
    }
    public char[] getPasswordLoginField() {
        return passwordLoginField.getPassword();
    }
}