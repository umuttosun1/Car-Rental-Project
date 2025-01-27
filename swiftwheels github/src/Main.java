import javax.swing.*;
import java.awt.*;
public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("LoginRegisterPanel");
        frame.setContentPane(new LoginRegisterPanel().mainPanel);
        frame.setPreferredSize(new Dimension(1000,450));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}