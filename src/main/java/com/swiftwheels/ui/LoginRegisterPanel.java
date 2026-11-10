package com.swiftwheels.ui;

import com.swiftwheels.model.Customer;
import com.swiftwheels.model.CustomerRepository;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Optional;

/** Login + registration screen. The layout lives in LoginRegisterPanel.form (IntelliJ GUI Designer). */
public class LoginRegisterPanel {
    private static final char ECHO_CHAR = '•';

    // ---- components bound from the .form file (names must match the form) ----
    JPanel mainPanel;
    private JLabel logoLabel;
    private JTextField emailLogField;
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
        for (JButton b : new JButton[]{createAccountButton, RegisterButton, LoginButton, backButton}) {
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        RegisterButton.addActionListener(e -> showRegisterPanel());
        backButton.addActionListener(e -> showLoginPanel());

        passwordShowCheckBox1.addActionListener(e ->
                passwordLoginField.setEchoChar(passwordShowCheckBox1.isSelected() ? (char) 0 : ECHO_CHAR));
        passwordShowCheckBox.addActionListener(e ->
                passwordRegField.setEchoChar(passwordShowCheckBox.isSelected() ? (char) 0 : ECHO_CHAR));

        createAccountButton.addActionListener(e -> register());
        LoginButton.addActionListener(e -> login());
        // Enter in the password field logs in
        passwordLoginField.addActionListener(e -> login());
    }

    /** Opens the login window (used at start-up and after sign-out / account deletion). */
    public static void showWindow() {
        closeAllFrames();
        JFrame frame = new JFrame("SwiftWheels - Giriş");
        frame.setContentPane(new LoginRegisterPanel().mainPanel);
        frame.setIconImage(Images.icon("SwiftWheels.png", 64, 64).getImage());
        frame.setPreferredSize(new Dimension(1000, 450));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static void closeAllFrames() {
        for (Frame frame : Frame.getFrames()) {
            if (frame instanceof JFrame) {
                frame.dispose();
            }
        }
    }

    private void login() {
        String email = emailLogField.getText().trim();
        char[] chars = passwordLoginField.getPassword();
        String password = new String(chars);
        Arrays.fill(chars, '\0');

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "E-posta ve şifre girin.", "Giriş", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Optional<Customer> customer = CustomerRepository.authenticate(email, password);
            if (customer.isPresent()) {
                MainAppPanel.showWindow(customer.get());
            } else {
                JOptionPane.showMessageDialog(mainPanel, "E-posta veya şifre hatalı!", "Giriş", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel, ex);
        }
    }

    private void register() {
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        String phone = numberField.getText().replaceAll("[^0-9]", "");
        String mail = emailRegField.getText().trim();
        String password = new String(passwordRegField.getPassword());

        String error = Customer.validate(name, surname, phone, mail, password);
        if (error != null) {
            showWarning(error);
            return;
        }
        try {
            if (CustomerRepository.emailExists(mail)) {
                showWarning("Bu e-posta adresi zaten kayıtlı.");
                return;
            }
            CustomerRepository.register(name, surname, phone, mail, password);
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel, ex);
            return;
        }

        nameField.setText("");
        surnameField.setText("");
        numberField.setValue(null);
        emailRegField.setText("");
        passwordRegField.setText("");
        showLoginPanel();
        emailLogField.setText(mail);
        passwordLoginField.requestFocusInWindow();
        JOptionPane.showMessageDialog(mainPanel, "Hesabınız oluşturuldu! Şimdi giriş yapabilirsiniz.");
    }

    private void showWarning(String message) {
        warningLabel.setText(message);
        warningLabel.setVisible(true);
    }

    private void showRegisterPanel() {
        backButton.setVisible(true);
        warningLabel.setVisible(false);
        parentPanel.removeAll();
        parentPanel.add(RegPanel);
        parentPanel.revalidate();
        parentPanel.repaint();
        LogoPanel.setPreferredSize(new Dimension(200, 100));
        parentPanel.setPreferredSize(new Dimension(200, 700));
    }

    private void showLoginPanel() {
        backButton.setVisible(false);
        warningLabel.setVisible(false);
        parentPanel.removeAll();
        parentPanel.add(InputPanel);
        parentPanel.revalidate();
        parentPanel.repaint();
    }

    /** Called by the GUI Designer for components marked "custom create". */
    private void createUIComponents() {
        logoLabel = new JLabel(Images.icon("SwiftWheels.png", 400, 420));
        try {
            MaskFormatter mask = new MaskFormatter("0(5##) ###-####");
            mask.setPlaceholderCharacter('_');
            mask.setValidCharacters("0123456789");
            mask.setAllowsInvalid(false);
            numberField = new JFormattedTextField(mask);
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }
}
