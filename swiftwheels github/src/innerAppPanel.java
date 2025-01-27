import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.table.*;


public class innerAppPanel {
    private static final String URL = "jdbc:postgresql://localhost:5432/YOUR_DATABASE";
    private static final String USER = "YOUR_USERNAME";
    private static final String PASSWORD = "YOUR_PASSWORD";
    public int userId;
    Color background = new Color(76,147,152);
    boolean readFileStat;
    public String email_degiskeni ;
    public Customer customer = new Customer();
    int counters = 0;
    int currentVehicleId = 0;
    public  Car car = new Car();
    public Motorcycle motorcycle = new Motorcycle();
    public Jeep jeep = new Jeep();
    public JPanel mainPanel1;
    private JLabel LogoLabel;
    private JButton homeButton;
    private JButton accountButton;
    private JButton contractButton;
    private JButton vehicleButton;
    public JPanel InnerLogoPanel;
    private JPanel MenuPanel;
    public JPanel parentPanel;
    private JPanel menuOpen;
    private JPanel MenuClose;
    private JButton menu;
    private JButton menuCloseButton;
    private JPanel homePanel;
    private JPanel accountPanel;
    private JPanel vehiclesMenuPanel;
    private JPanel contractParentPanel;
    private JPanel MenuButtonPanel;
    private JLabel ppLabel;
    private JButton signOutButton;
    public JTextArea nameArea;
    public JTextArea surnameArea;
    public JTextArea phoneArea;
    public JTextArea emailArea;
    public JTextArea passwordArea;
    private JButton logOutButton;
    private JLabel car1Logo;
    private JLabel car2Logo;
    private JLabel motorLogo;
    private JRadioButton carRadioButton;
    private JRadioButton motorcycleRadioButton;
    private JRadioButton suvRadioButton;
    private JPanel vehicleTypePanel;
    private JPanel vehicleParentPanel;
    private JPanel carPanel;
    private JPanel suvPanel;
    private JPanel motorcyclePanel;
    private JPanel carInfoPanel;
    private JPanel suvInfoPanel;
    private JPanel motorInfoPanel;
    private JLabel carbigInfo;
    private JLabel motorbigInfo;
    private JLabel suvbigInfo;
    private JTextArea carPriceprivate;
    private JTextArea carEngineCrivate;
    private JTextArea carEnginePprivate;
    private JTextArea carColorprivate;
    private JTextArea carKmprivate;
    private JTextArea carYearprivate;
    private JTextArea carModelprivate;
    private JTextArea carFuelprivate;
    private JTextArea carSerialprivate;
    public JTextArea carMakeprivate;
    private JButton backButton;
    private JPanel contractDefaultPanel;
    private JPanel contractActionPanel;
    private JLabel homeLogo;
    private JLabel mailLogo;
    private JLabel phoneLogo;
    private JLabel instaLogo;
    private JLabel locationLogo;
    private JButton carContractButton;
    private JLabel contractImage;
    private JCheckBox okudumBox1;
    private JTextField idField;
    private JTextField addressField;
    private JButton rentButton;
    private JTextField dayField;
    private JTextField licenseField;
    private JTextField feeField;
    private JScrollBar scrollBar1;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane;
    private JButton seeVehiclesButton;
    private JButton suvContractButton;
    private JTextArea suvPrice;
    private JTextArea suvEngineP;
    private JTextArea suvEngineC;
    private JTextArea suvColor;
    private JTextArea suvKm;
    private JTextArea suvYear;
    private JTextArea suvTraction;

    private JTextArea suvModel;
    private JTextArea suvFuel;
    private JTextArea suvSerial;
    private JTextArea suvMake;
    private JTextArea motoMakePrivate;
    private JTextArea motoTypePrivate;
    private JTextArea motoCoolingPrivate;
    private JTextArea motoModelPrivate;
    private JTextArea motoCyclinderPrivate;
    private JTextArea motoYearPrivate;
    private JTextArea motoKmPrivate;
    private JTextArea motoEnginePPrivate;
    private JTextArea motoEngineCPrivate;
    private JTextArea motoPricePrivate;
    private JTextArea motoColorPrivate;
    private JButton motoContractButton;
    private JFormattedTextField cardDateField;
    private JButton deleteButton;
    private JButton editButton;
    private JButton updateButton;
    private JTextArea carStockPrivate;
    private JTextArea motoStockPrivate;
    private JTextArea suvStockP;
    private JTextArea adminKey;
    private JButton adminButton;
    private JButton adminEdit1;
    private JButton adminDelete1;
    private JButton adminEdit2;
    private JButton adminDelete2;
    private JButton adminEdit3;
    private JButton adminDelete3;
    private JButton adminUpdate1;
    private JButton adminUpdate3;
    private JButton adminUpdate2;
    private JButton addVehicle1Button;
    private JPanel addVehiclePanel;
    private JButton addVehicle2Button;
    private JTextField addVehicleMakeText;
    private JTextField addVehicleSerialText;
    private JTextField addVehicleFuelText;
    private JTextField addVehicleModelText;
    private JTextField addVehicleYearText;
    private JTextField addVehicleKilometerText;
    private JTextField addVehicleColorText;
    private JTextField addVehicleEnginePowerText;
    private JTextField addVehicleEngineCapacityText;
    private JTextField addVehiclePriceText;
    private JTextField addVehicleStockText;
    private JLabel addCarMake;
    private JLabel addVehicleSerial;
    private JLabel addVehicleFuel;
    private JLabel addVehicleModel;
    private JLabel addVehicleYear;
    private JLabel addVehicleKilometer;
    private JLabel addVehicleColor;
    private JLabel addVehicleEnginePower;
    private JLabel addVehicleEngineCapacity;
    private JLabel addVehiclePrice;
    private JLabel addVehicleStock;
    private JButton imageUploadButton;
    private JTextField addMotoTypeText;
    private JTextField addMotoCoolingText;
    private JTextField addMotoCylinderText;
    private JLabel addMotoType;
    private JLabel addMotoCooling;
    private JLabel addMotoCylinder;
    private JTextField addJeepTractionText;
    private JLabel addJeepTraction;
    private JPanel seeVehiclesPanel;
    private JScrollPane scrollPane2;
    private JLabel imageLabel;
    private JFormattedTextField identityNumberField;
    private JFormattedTextField licenseNumberField;
    private JFormattedTextField vehicleReturnField;
    private JButton adminPanelButton;
    private JPanel adminPanel;
    private JLabel vehicleInfo;
    private JFormattedTextField cardNumberField;
    private JFormattedTextField cardCvcField;
    private JTextArea cinfo;
    private JCheckBox wantCheckbox;
    private JCheckBox acceptCheckbox;
    private JTable myRentedVehicleTable;
    private JTable adminContractTable;
    private JTextField adminVehicleType;
    private JTextField adminUserNumber;
    private JTextField adminOnRentedVehicle;
    private JTextField adminRevenue;
    private JTextField adminVehicleStock;
    private JTextField iadeField;
    private JButton iadeButton;
    String folderPath = "images";
    boolean[] isAdminMode = {false};
    ArrayList<Car> carList = new ArrayList<>();
    ArrayList<Motorcycle> motorList = new ArrayList<>();
    ArrayList<Jeep> jeepList = new ArrayList<>();

    public innerAppPanel(int userId) {
        // Menu paneli action listenerleri
        menu.addActionListener(e -> {
            menuOpen.setVisible(false);
            MenuClose.setVisible(true);
            MenuPanel.setVisible(true);
        });//Menu tuşuna basıldığında menu paneli açacak
        menuCloseButton.addActionListener(e -> {
            MenuClose.setVisible(true);
            menuOpen.setVisible(true);
            MenuPanel.setVisible(false);
        });//Menu tuşuna basıldığında menu panelini kapatacak
        homeButton.addActionListener(e -> {
            parentPanel.removeAll();
            parentPanel.add(homePanel);
            parentPanel.repaint();
            parentPanel.revalidate();
            vehicleTypePanel.setVisible(false);
        });//Home  tuşuna basıldığında Home sayfasını açacak
        //uygulamada soldaki menu paneli
        vehicleButton.addActionListener(e -> {
            carRadioButton.setSelected(true);
            parentPanel.removeAll();
            parentPanel.add(vehiclesMenuPanel);
            parentPanel.repaint();
            parentPanel.revalidate();
            vehicleTypePanel.setVisible(true);

            if (counters == 0) {
                carList.addAll(car.getAllCars());
                jeepList.addAll(jeep.getAllJeeps());
                motorList.addAll(motorcycle.getAllMotorcycles());
                updateCarPanel();
                updateJeepPanel();
                updateMotoPanel();
                counters++;
            }
        });//Vehicle tuşuna basıldığında Vehicle sayfasını açacak
        contractButton.addActionListener(e -> {
            parentPanel.removeAll();
            if(isAdminMode[0]){
                adminContractTable.setModel(getAllRentedVehiclesTable());
                myRentedVehicleTable.setVisible(false);
                adminContractTable.setVisible(true);
            }else {
                myRentedVehicleTable.setModel(getRentedVehicleTable(userId));
                adminContractTable.setVisible(false);
                myRentedVehicleTable.setVisible(true);
            }
            parentPanel.add(contractParentPanel);
            parentPanel.repaint();
            parentPanel.revalidate();
            vehicleTypePanel.setVisible(false);
        }); // burada admin cikisi oldugunda sorun cikiyor
        accountButton.addActionListener(e -> {
            parentPanel.removeAll();
            parentPanel.add(accountPanel);
            customer.loadUserInfoToTextArea(userId,nameArea,surnameArea,phoneArea, emailArea,passwordArea); // Bilgileri yükle
            parentPanel.repaint();
            parentPanel.revalidate();
            vehicleTypePanel.setVisible(false);
        } );//Account tuşuna basıldığında account sayfasını açacak
        logOutButton.addActionListener(e -> LoginRegisterPanel.closeAllFrames());//Uygulamayı kapatıyor
        //account panelde isim degiskenlerini degistirmek icin
        editButton.addActionListener(e -> {
            nameArea.setEditable(true);
            surnameArea.setEditable(true);
            phoneArea.setEditable(true);
            passwordArea.setEditable(true);

            editButton.setVisible(false);
            signOutButton.setVisible(false);
            deleteButton.setVisible(false);
            adminButton.setVisible(false);
            updateButton.setVisible(true);

        });
        deleteButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    null,
                    "Hesabı silmek istiyor musunuz?",
                    "Hesap Silme",
                    JOptionPane.YES_NO_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                try {
                    // Hesabı silmek için deleteFile metodunu çağır
                    customer.deleteFile(userId);

                    // Başarı durumunu kontrol etmek için bir ek mantık
                    if (customer.isAccountDeleted(userId)) {
                        // Başarılı işlem sonrası kullanıcıyı giriş ekranına yönlendir
                        LoginRegisterPanel.closeAllFrames();
                        JFrame frame = new JFrame("LoginRegisterPanel");
                        frame.setContentPane(new LoginRegisterPanel().mainPanel);
                        frame.setPreferredSize(new Dimension(1000, 450));
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    } else {

                        JOptionPane.showMessageDialog(
                                null,
                                "Hesap silinemedi. Kiralanmış aracınız olabilir.",
                                "Hata",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }

                } catch (Exception ex) {
                    // Beklenmeyen diğer hatalar için kullanıcıya mesaj göster
                    JOptionPane.showMessageDialog(
                            null,
                            "Hesap silinemedi: " + ex.getMessage(),
                            "Hata",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        updateButton.addActionListener(e -> {
            nameArea.setEditable(false);
            surnameArea.setEditable(false);
            phoneArea.setEditable(false);
            passwordArea.setEditable(false);

            signOutButton.setVisible(true);
            deleteButton.setVisible(true);
            updateButton.setVisible(false);
            editButton.setVisible(true);
            adminButton.setVisible(true);

            customer.updateFile(nameArea,surnameArea,phoneArea,emailArea,passwordArea);
            customer.loadUserInfoToTextArea(userId,nameArea,surnameArea,phoneArea,emailArea,passwordArea);
        });
        adminButton.addActionListener(e -> {
            String admin = adminKey.getText(); // Kullanıcıdan giriş metnini al

            if (!isAdminMode[0] && Objects.equals(admin, "admin")) {
                // Admin moduna giriş
                isAdminMode[0] = true; // Admin modu aktif
                toggleAdminMode(true); // Admin modu açılır
                JOptionPane.showMessageDialog(null, "Admin girişi başarılı");
            }
            else if (isAdminMode[0] && Objects.equals(admin, "admincikis")) {
                // Admin modundan çıkış
                isAdminMode[0] = false; // Admin modu devre dışı
                toggleAdminMode(false); // Admin modu kapatılır
                JOptionPane.showMessageDialog(null, "Admin çıkışı yapıldı");
            }
            else if (!isAdminMode[0]) {
                // Hatalı giriş (Admin değilken giriş denemesi)
                JOptionPane.showMessageDialog(null, "Hatalı giriş. Lütfen doğru bir değer girin!");
            }
            else {
                // Admin modunda ama hatalı komut girildi
                JOptionPane.showMessageDialog(null, "Geçersiz işlem. Admin çıkışı yapmak için 'admincikis' yazın.");
            }
        });// Admin durumunu takip eden bir değişken
        signOutButton.addActionListener(e -> {
            LoginRegisterPanel.closeAllFrames();
            JFrame frame = new JFrame("LoginRegisterPanel");
            frame.setContentPane(new LoginRegisterPanel().mainPanel);
            frame.setPreferredSize(new Dimension(1000,450));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });//Hesaptan çıkış yapıp login register atıyor
        //account panel islemleri bitti
        //show details tuslariyla araclara edit update islemleri
        adminEdit1.addActionListener(e -> {
            carMakeprivate.setEditable(true);
            carFuelprivate.setEditable(true);
            carYearprivate.setEditable(true);
            carKmprivate.setEditable(true);
            carColorprivate.setEditable(true);
            carEngineCrivate.setEditable(true);
            carEnginePprivate.setEditable(true);
            carPriceprivate.setEditable(true);
            carStockPrivate.setEditable(true);
            carSerialprivate.setEditable(true);
            carModelprivate.setEditable(true);

            adminEdit1.setVisible(false);
            adminUpdate1.setVisible(true);
        });// car edit button
        adminUpdate1.addActionListener(e->{
            carMakeprivate.setEditable(false);
            carFuelprivate.setEditable(false);
            carYearprivate.setEditable(false);
            carKmprivate.setEditable(false);
            carColorprivate.setEditable(false);
            carEngineCrivate.setEditable(false);
            carEnginePprivate.setEditable(false);
            carPriceprivate.setEditable(false);
            carStockPrivate.setEditable(false);
            carModelprivate.setEditable(false);
            carSerialprivate.setEditable(false);

            car.updateCar(currentVehicleId,carMakeprivate,carModelprivate,carYearprivate,carKmprivate,carColorprivate,carEnginePprivate,carEngineCrivate,carPriceprivate
                    ,carStockPrivate,carSerialprivate,carFuelprivate);
            adminEdit1.setVisible(true);
            adminUpdate1.setVisible(false);
        });// car update button
        adminDelete1.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null,
                    "Bu aracı silmek istediğinizden emin misiniz?",
                    "Aracı Sil",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            // Eğer kullanıcı "Evet" derse, aracı sil
            if (response == JOptionPane.YES_OPTION) {
                // İlk olarak, `carList` içerisinde doğru aracı bul
                Car vehicleToDelete = null;
                for (Car car : carList) {
                    if (car.getId() == currentVehicleId) {
                        vehicleToDelete = car;
                        break;
                    }
                }

                // Eğer araç bulunduysa, silme işlemini gerçekleştir
                if (vehicleToDelete != null) {
                    try {
                        // Veritabanından silme işlemini dene
                        car.deleteCar(currentVehicleId); // Prosedürü çağır

                        // Fotoğraf dosyasının yolunu oluştur
                        int vehicleId = vehicleToDelete.getId(); // Araç ID'si
                        String photoPath = String.format("images/vehicles_%d.png", vehicleId);

                        // Fotoğrafı sil
                        File photoFile = new File(photoPath);
                        if (photoFile.exists()) {
                            if (photoFile.delete()) {
                                System.out.println("Fotoğraf başarıyla silindi: " + photoPath);
                            } else {
                                System.out.println("Fotoğraf silinemedi: " + photoPath);
                            }
                        } else {
                            System.out.println("Fotoğraf dosyası bulunamadı: " + photoPath);
                        }

                        // Listeden sil ve paneli güncelle
                        carList.remove(vehicleToDelete);
                        vehicleParentPanel.removeAll();
                        vehicleParentPanel.add(carPanel);
                        vehicleParentPanel.revalidate();
                        vehicleParentPanel.repaint();
                        updateCarPanel(); // Paneli güncelle

                        JOptionPane.showMessageDialog(null, "Araç ve fotoğraf başarıyla silindi.");

                    } catch (SQLException ex) {
                        // Eğer SQL işlemi hata verirse, kullanıcıyı bilgilendir
                        JOptionPane.showMessageDialog(null,
                                "Bir hata oluştu: " + ex.getMessage(),
                                "Silme Hatası", JOptionPane.ERROR_MESSAGE);
                        System.err.println("SQL Hatası: " + ex.getMessage());
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Araç bulunamadı. Silme işlemi gerçekleştirilemedi.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Silme işlemi iptal edildi.");
            }
        });

// car delete button
        adminEdit2.addActionListener(e -> {
            motoColorPrivate.setEditable(true);
            motoCoolingPrivate.setEditable(true);
            motoCyclinderPrivate.setEditable(true);
            motoColorPrivate.setEditable(true);
            motoKmPrivate.setEditable(true);
            motoMakePrivate.setEditable(true);
            motoModelPrivate.setEditable(true);
            motoPricePrivate.setEditable(true);
            motoStockPrivate.setEditable(true);
            motoTypePrivate.setEditable(true);
            motoYearPrivate.setEditable(true);
            motoEngineCPrivate.setEditable(true);
            motoEnginePPrivate.setEditable(true);

            adminEdit2.setVisible(false);
            adminUpdate2.setVisible(true);
        });// motor edit button
        adminUpdate2.addActionListener(e->{
            motoColorPrivate.setEditable(false);
            motoCoolingPrivate.setEditable(false);
            motoCyclinderPrivate.setEditable(false);
            motoColorPrivate.setEditable(false);
            motoKmPrivate.setEditable(false);
            motoMakePrivate.setEditable(false);
            motoModelPrivate.setEditable(false);
            motoPricePrivate.setEditable(false);
            motoStockPrivate.setEditable(false);
            motoTypePrivate.setEditable(false);
            motoYearPrivate.setEditable(false);
            motoEngineCPrivate.setEditable(false);
            motoEnginePPrivate.setEditable(false);

            motorcycle.updateMotorcycle(currentVehicleId,motoMakePrivate,motoModelPrivate,motoYearPrivate,motoKmPrivate,motoColorPrivate,motoEnginePPrivate,
                    motoEngineCPrivate,motoPricePrivate,motoStockPrivate,motoTypePrivate,motoCoolingPrivate,motoCyclinderPrivate);
            adminEdit2.setVisible(true);
            adminUpdate2.setVisible(false);
        });// motor update button
        adminDelete2.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null,
                    "Bu aracı silmek istediğinizden emin misiniz?",
                    "Aracı Sil",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                // İlk olarak, motor listesinde doğru motoru bul
                Motorcycle motorcycleToDelete = null;
                for (Motorcycle moto : motorList) {
                    if (moto.getId() == currentVehicleId) {
                        motorcycleToDelete = moto;
                        break;
                    }
                }

                // Eğer motor bulunduysa, silme işlemini gerçekleştir
                if (motorcycleToDelete != null) {
                    try {
                        // Veritabanından silme işlemini dene
                        motorcycle.deleteMotorcycle(currentVehicleId); // Prosedürü çağır

                        // Fotoğraf dosyasının yolunu oluştur
                        int motorcycleId = motorcycleToDelete.getId(); // Motor ID'si
                        String photoPath = String.format("images/vehicles_%d.png", motorcycleId);

                        // Fotoğrafı sil
                        File photoFile = new File(photoPath);
                        if (photoFile.exists()) {
                            if (photoFile.delete()) {
                                System.out.println("Fotoğraf başarıyla silindi: " + photoPath);
                            } else {
                                System.out.println("Fotoğraf silinemedi: " + photoPath);
                            }
                        } else {
                            System.out.println("Fotoğraf dosyası bulunamadı: " + photoPath);
                        }

                        // Listeden sil ve paneli güncelle
                        motorList.remove(motorcycleToDelete);
                        vehicleParentPanel.removeAll();
                        vehicleParentPanel.add(motorcyclePanel);
                        vehicleParentPanel.revalidate();
                        vehicleParentPanel.repaint();
                        updateMotoPanel(); // Paneli güncelle

                        JOptionPane.showMessageDialog(null, "Araç ve fotoğraf başarıyla silindi.");

                    } catch (SQLException ex) {
                        // Eğer SQL işlemi hata verirse, kullanıcıyı bilgilendir
                        JOptionPane.showMessageDialog(null,
                                "Bir hata oluştu: " + ex.getMessage(),
                                "Silme Hatası", JOptionPane.ERROR_MESSAGE);
                        System.err.println("SQL Hatası: " + ex.getMessage());
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Araç bulunamadı. Silme işlemi gerçekleştirilemedi.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Silme işlemi iptal edildi.");
            }
        });
// motor delete button
        adminEdit3.addActionListener(e ->{
            suvColor.setEditable(true);
            suvYear.setEditable(true);
            suvTraction.setEditable(true);
            suvStockP.setEditable(true);
            suvSerial.setEditable(true);
            suvPrice.setEditable(true);
            suvModel.setEditable(true);
            suvMake.setEditable(true);
            suvKm.setEditable(true);
            suvFuel.setEditable(true);
            suvEngineP.setEditable(true);
            suvEngineC.setEditable(true);

            adminEdit3.setVisible(false);
            adminUpdate3.setVisible(true);
        });// jeep edit button
        adminUpdate3.addActionListener(e->{
            suvColor.setEditable(false);
            suvYear.setEditable(false);
            suvTraction.setEditable(false);
            suvStockP.setEditable(false);
            suvSerial.setEditable(false);
            suvPrice.setEditable(false);
            suvModel.setEditable(false);
            suvMake.setEditable(false);
            suvKm.setEditable(false);
            suvFuel.setEditable(false);
            suvEngineP.setEditable(false);
            suvEngineC.setEditable(false);

            jeep.updateJeep(currentVehicleId,suvMake,suvModel,suvYear,suvKm,suvColor,suvEngineP,suvEngineC,suvPrice
                    ,suvStockP,suvSerial,suvTraction,suvFuel);

            adminEdit3.setVisible(true);
            adminUpdate3.setVisible(false);
        });// jeep update button
        adminDelete3.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null,
                    "Bu aracı silmek istediğinizden emin misiniz?",
                    "Aracı Sil",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                // İlk olarak, jeep listesinde doğru aracı bul
                Jeep jeepToDelete = null;
                for (Jeep suv : jeepList) {
                    if (suv.getId() == currentVehicleId) {
                        jeepToDelete = suv;
                        break;
                    }
                }

                // Eğer jeep bulunduysa, silme işlemini gerçekleştir
                if (jeepToDelete != null) {
                    try {
                        // Veritabanından silme işlemini dene
                        jeep.deleteJeep(currentVehicleId); // Prosedürü çağır

                        // Fotoğraf dosyasının yolunu oluştur
                        int jeepId = jeepToDelete.getId(); // Jeep ID'si
                        String photoPath = String.format("images/vehicles_%d.png", jeepId);

                        // Fotoğrafı sil
                        File photoFile = new File(photoPath);
                        if (photoFile.exists()) {
                            if (photoFile.delete()) {
                                System.out.println("Fotoğraf başarıyla silindi: " + photoPath);
                            } else {
                                System.out.println("Fotoğraf silinemedi: " + photoPath);
                            }
                        } else {
                            System.out.println("Fotoğraf dosyası bulunamadı: " + photoPath);
                        }

                        // Listeden sil ve paneli güncelle
                        jeepList.remove(jeepToDelete);
                        vehicleParentPanel.removeAll();
                        vehicleParentPanel.add(suvPanel);
                        vehicleParentPanel.revalidate();
                        vehicleParentPanel.repaint();
                        updateJeepPanel(); // Paneli güncelle

                        JOptionPane.showMessageDialog(null, "Araç ve fotoğraf başarıyla silindi.");

                    } catch (SQLException ex) {
                        // Eğer SQL işlemi hata verirse, kullanıcıyı bilgilendir
                        JOptionPane.showMessageDialog(null,
                                "Bir hata oluştu: " + ex.getMessage(),
                                "Silme Hatası", JOptionPane.ERROR_MESSAGE);
                        System.err.println("SQL Hatası: " + ex.getMessage());
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Araç bulunamadı. Silme işlemi gerçekleştirilemedi.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Silme işlemi iptal edildi.");
            }
        });
// jeep delete button
        //show details islemleri bitti
        carRadioButton.addActionListener(e -> {
            vehicleParentPanel.removeAll();
            vehicleParentPanel.add(carPanel);
            vehicleParentPanel.revalidate();
            vehicleParentPanel.repaint();

        });//Araba panelini açan radio buttonu
        motorcycleRadioButton.addActionListener(e -> {
            vehicleParentPanel.removeAll();
            vehicleParentPanel.add(motorcyclePanel);
            vehicleParentPanel.revalidate();
            vehicleParentPanel.repaint();
        });//motor panelini açan radio buttonu
        suvRadioButton.addActionListener(e -> {
            vehicleParentPanel.removeAll();
            vehicleParentPanel.add(suvPanel);
            vehicleParentPanel.revalidate();
            vehicleParentPanel.repaint();
        });//suv panelini açan radio buttonu
        backButton.addActionListener(e -> {
            if(carRadioButton.isSelected()){
            vehicleParentPanel.removeAll();
            vehicleParentPanel.add(carPanel);
            vehicleParentPanel.revalidate();
            vehicleParentPanel.repaint();
            } else if (motorcycleRadioButton.isSelected()) {
                vehicleParentPanel.removeAll();
                vehicleParentPanel.add(motorcyclePanel);
                vehicleParentPanel.revalidate();
                vehicleParentPanel.repaint();
            }
            else if (suvRadioButton.isSelected()) {
                vehicleParentPanel.removeAll();
                vehicleParentPanel.add(suvPanel);
                vehicleParentPanel.revalidate();
                vehicleParentPanel.repaint();
            }
        });// Back button
        // CONTRACT PANELLER
        seeVehiclesButton.addActionListener(e -> {
            vehicleTypePanel.setVisible(true);
            parentPanel.removeAll();
            parentPanel.add(vehiclesMenuPanel);
            parentPanel.revalidate();
            parentPanel.repaint();

        });// in contract panel see Vehicle button
        carContractButton.addActionListener(e -> {
            vehicleTypePanel.setVisible(false);
            parentPanel.removeAll();
            parentPanel.add(contractActionPanel);
            parentPanel.revalidate();
            parentPanel.repaint();
            // GUI Designer'da oluşturulmuş alanlara format uygula
            setupFormattedFields();
        });
        suvContractButton.addActionListener(e -> {
            vehicleTypePanel.setVisible(false);
            parentPanel.removeAll();
            parentPanel.add(contractActionPanel);
            parentPanel.revalidate();
            parentPanel.repaint();
            setupFormattedFields();

        });// vehicle contract button
        motoContractButton.addActionListener(e -> {
            vehicleTypePanel.setVisible(false);
            parentPanel.removeAll();
            parentPanel.add(contractActionPanel);
            parentPanel.revalidate();
            parentPanel.repaint();
            setupFormattedFields();
        });// vehicle contract button
        rentButton.addActionListener(e -> {
            String cardNumber = cardNumberField.getText().replaceAll("[^0-9]", "");
            String cardDate = cardDateField.getText();
            String cardCvc = cardCvcField.getText().replaceAll("[^0-9]", "");
            String returnDate = vehicleReturnField.getText();
            String address = addressField.getText();

            // Alanların doluluğunu kontrol et
            if ( cardNumber.isEmpty() ||  cardDate.isEmpty() || cardCvc.isEmpty() || returnDate.isEmpty() || address.isEmpty() && acceptCheckbox.isSelected()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tarih doğrulaması
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false); // Tarih formatının kesinliğini sağlar
            try {
                // Kullanıcının girdiği tarihi kontrol et
                Date enteredDate = dateFormat.parse(returnDate);

                // Sistem tarihinden bir gün sonrasını kontrol et
                Calendar systemCalendar = Calendar.getInstance();
                systemCalendar.add(Calendar.DAY_OF_MONTH, 1); // Sistem tarihine 1 gün ekle
                Date minDate = systemCalendar.getTime();

                if (!enteredDate.after(minDate)) {
                    JOptionPane.showMessageDialog(null, "Return date must be at least one day after the current date.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid date in DD/MM/YYYY format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int daysBetween = calculateDaysBetween(returnDate, "dd/MM/yyyy") ;
            if (carRadioButton.isSelected() || motorcycleRadioButton.isSelected() || suvRadioButton.isSelected()) {
                try {
                    rentVehicle(userId,address,currentVehicleId,daysBetween,feeField, parentPanel);

                    // Ekranı temizleme ve güncelleme
                    parentPanel.removeAll();
                    parentPanel.add(homePanel);
                    parentPanel.repaint();
                    parentPanel.revalidate();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(parentPanel, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Alanları temizle
            cardNumberField.setText("");
            cardDateField.setText("");
            cardCvcField.setText("");
            vehicleReturnField.setText("");
            addressField.setText("");
            feeField.setText("");
            wantCheckbox.setSelected(false);
            acceptCheckbox.setSelected(false);
        });
        // Alanları temizle
        acceptCheckbox.addActionListener(e -> {
            // Kiralama islemleri
            String returnDate = vehicleReturnField.getText();
            int daysBetween = calculateDaysBetween(returnDate, "dd/MM/yyyy") ;
            if (carRadioButton.isSelected()){
                if (wantCheckbox.isSelected()){
                    feeField.setText(String.valueOf((Integer.parseInt(carPriceprivate.getText()) * daysBetween) + 20));
                }else {
                    feeField.setText(String.valueOf((Integer.parseInt(carPriceprivate.getText()) * daysBetween)));
                }
            }
            else if(motorcycleRadioButton.isSelected()){
                if (wantCheckbox.isSelected()){
                    feeField.setText(String.valueOf((Integer.parseInt(motoPricePrivate.getText()) * daysBetween) + 20));
                }else{
                feeField.setText(String.valueOf((Integer.parseInt(motoPricePrivate.getText()) * daysBetween)));
                }
            }
            else if (suvRadioButton.isSelected()) {
                if (wantCheckbox.isSelected()){
                    feeField.setText(String.valueOf((Integer.parseInt(suvPrice.getText()) * daysBetween) + 20));
                }
                else{
                feeField.setText(String.valueOf((Integer.parseInt(suvPrice.getText()) * daysBetween)));
                }
            }

        });// onaylama button
        addVehicle1Button.addActionListener(e -> {
            vehicleParentPanel.removeAll();
            vehicleParentPanel.add(addVehiclePanel);
            vehicleParentPanel.revalidate();
            vehicleParentPanel.repaint();

            ImageIcon whiteIcon = new ImageIcon(new ImageIcon("images/pictureimage.png").getImage().getScaledInstance(400,300, Image.SCALE_SMOOTH));
            imageLabel.setIcon(whiteIcon);


            if (carRadioButton.isSelected()){

                addMotoCooling.setVisible(false);
                addMotoCylinder.setVisible(false);
                addMotoType.setVisible(false);
                addMotoTypeText.setVisible(false);
                addMotoCylinderText.setVisible(false);
                addMotoCoolingText.setVisible(false);
                addJeepTraction.setVisible(false);
                addJeepTractionText.setVisible(false);

                addVehicleSerial.setVisible(true);
                addVehicleSerialText.setVisible(true);
                addVehicleFuel.setVisible(true);
                addVehicleFuelText.setVisible(true);
            }
            else if (motorcycleRadioButton.isSelected()){
                addVehicleSerial.setVisible(false);
                addVehicleSerialText.setVisible(false);
                addVehicleFuel.setVisible(false);
                addVehicleFuelText.setVisible(false);
                addJeepTraction.setVisible(false);
                addJeepTractionText.setVisible(false);

                addMotoCooling.setVisible(true);
                addMotoCylinder.setVisible(true);
                addMotoType.setVisible(true);
                addMotoTypeText.setVisible(true);
                addMotoCylinderText.setVisible(true);
                addMotoCoolingText.setVisible(true);

            }
            else if (suvRadioButton.isSelected()){
                addMotoCooling.setVisible(false);
                addMotoCylinder.setVisible(false);
                addMotoType.setVisible(false);
                addMotoTypeText.setVisible(false);
                addMotoCylinderText.setVisible(false);
                addMotoCoolingText.setVisible(false);

                addVehicleSerial.setVisible(true);
                addVehicleSerialText.setVisible(true);
                addVehicleFuel.setVisible(true);
                addVehicleFuelText.setVisible(true);
                addJeepTraction.setVisible(true);
                addJeepTractionText.setVisible(true);
            }
        });// add vehicle panel opener
        addVehicle2Button.addActionListener(e -> {
            try {
                // Ortak alanları al
                String make = addVehicleMakeText.getText();
                String model = addVehicleModelText.getText();
                int year = Integer.parseInt(addVehicleYearText.getText());
                int kilometer = Integer.parseInt(addVehicleKilometerText.getText());
                String color = addVehicleColorText.getText();
                String enginePower = addVehicleEnginePowerText.getText();
                String engineCapacity = addVehicleEngineCapacityText.getText();
                int price = Integer.parseInt(addVehiclePriceText.getText());
                int stock = Integer.parseInt(addVehicleStockText.getText());

                // Araç ID'sini al
                int vehicleId = getLastId("vehicles") + 1;

                if (carRadioButton.isSelected()) {
                    // Araba bilgilerini al
                    String serial = addVehicleSerialText.getText();
                    String fuel = addVehicleFuelText.getText();

                    // Yeni araba nesnesi oluştur ve listeye ekle
                    Car car = new Car(vehicleId, serial, fuel, make, model, year, kilometer, color, enginePower, engineCapacity, price, stock);
                    carList.add(car);
                    car.addCarToDB(make,model,year,kilometer,color,enginePower,engineCapacity,price,stock,serial,fuel);

                    // Resmi kaydet
                    saveImageFromLabel(imageLabel, "images", "vehicles_" + vehicleId + ".png");

                    // Paneli güncelle
                    updateCarPanel();
                    vehicleParentPanel.removeAll();
                    vehicleParentPanel.add(carPanel);
                    vehicleParentPanel.revalidate();
                    vehicleParentPanel.repaint();

                } else if (motorcycleRadioButton.isSelected()) {
                    // Motosiklet bilgilerini al
                    String type = addMotoTypeText.getText();
                    String cooling = addMotoCoolingText.getText();
                    String cylinder = addMotoCylinderText.getText();

                    // Yeni motosiklet nesnesi oluştur ve listeye ekle
                    Motorcycle motor = new Motorcycle(vehicleId, type, cooling, cylinder, make, model, year, kilometer, color, enginePower, engineCapacity, price, stock);
                    motorList.add(motor);
                    motor.addMotorToDB(make, model, year, kilometer, color, enginePower, engineCapacity, price, stock, type, cooling, cylinder);

                    // Resmi kaydet
                    saveImageFromLabel(imageLabel, "images", "vehicles_" + vehicleId + ".png");

                    // Paneli güncelle
                    updateMotoPanel();
                    vehicleParentPanel.removeAll();
                    vehicleParentPanel.add(motorcyclePanel);
                    vehicleParentPanel.revalidate();
                    vehicleParentPanel.repaint();

                } else if (suvRadioButton.isSelected()) {
                    // SUV bilgilerini al
                    String serial = addVehicleSerialText.getText();
                    String fuel = addVehicleFuelText.getText();
                    String traction = addJeepTractionText.getText();

                    // Yeni SUV nesnesi oluştur ve listeye ekle
                    Jeep jeep = new Jeep(vehicleId, serial, fuel, traction, make, model, year, kilometer, color, enginePower, engineCapacity, price, stock);
                    jeepList.add(jeep);
                    jeep.addJeepToDB(make, model, year, kilometer, color, enginePower, engineCapacity, price, stock, serial, fuel, traction);

                    // Resmi kaydet
                    saveImageFromLabel(imageLabel, "images", "vehicles_" + vehicleId + ".png");

                    // Paneli güncelle
                    updateJeepPanel();
                    vehicleParentPanel.removeAll();
                    vehicleParentPanel.add(suvPanel);
                    vehicleParentPanel.revalidate();
                    vehicleParentPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen bir araç türü seçin!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Hata: " + ex.getMessage());
            }
        });// bu butona tiklandiginda araba ekleme islemi gerceklesir
        imageUploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Vehicle Image");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "png"));
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                try {
                    // Fotoğrafı ölçeklendir ve JLabel içine koy
                    ImageIcon vehicleImageIcon = new ImageIcon(new ImageIcon(selectedFile.getAbsolutePath())
                            .getImage()
                            .getScaledInstance(450, 200, Image.SCALE_SMOOTH)); // Görüntüyü ölçeklendir

                    imageLabel.setIcon(vehicleImageIcon); // JLabel'e ikonu ata
                    JOptionPane.showMessageDialog(null, "Image successfully loaded.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error loading image: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Image loading canceled.");
            }
        });// pcden fotograf yukleme
        adminPanelButton.addActionListener(e -> {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                // Admin tablosundan verileri almak için SQL sorgusu
                String query = "SELECT vehicle_type_number, user_number, rented_vehicle_number, total_turnover, vehicle_stock FROM public.admin";

                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(query)) {

                    if (resultSet.next()) {
                        // Veritabanındaki verileri değişkenlere al
                        int vehicleTypeNumber = resultSet.getInt("vehicle_type_number");
                        int userNumber = resultSet.getInt("user_number");
                        int rentedVehicleNumber = resultSet.getInt("rented_vehicle_number");
                        double totalTurnover = resultSet.getDouble("total_turnover");
                        int vehicleStock = resultSet.getInt("vehicle_stock");

                        // JTextArea'lara değerleri yerleştir
                        adminVehicleType.setText(String.valueOf(vehicleTypeNumber));
                        adminUserNumber.setText(String.valueOf(userNumber));
                        adminOnRentedVehicle.setText(String.valueOf(rentedVehicleNumber));
                        adminRevenue.setText(String.valueOf(totalTurnover));
                        adminVehicleStock.setText(String.valueOf(vehicleStock));
                    }
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Veritabanına bağlanırken bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }

            // Panel görünürlük ve yeniden çizim işlemleri
            vehicleTypePanel.setVisible(false);
            parentPanel.removeAll();
            parentPanel.add(adminPanel);
            parentPanel.revalidate();
            parentPanel.repaint();
        });
        iadeButton.addActionListener(e -> {
            int iadeId = Integer.parseInt(iadeField.getText());
            try {
                // Araç iade fonksiyonunu çağır
                vehicle_return(userId, iadeId);

                // Eğer başarılı olursa homePanel'e geçiş yap
                parentPanel.removeAll();
                parentPanel.add(homePanel);
                parentPanel.revalidate();
                parentPanel.repaint();

                // Başarı mesajı göster
                JOptionPane.showMessageDialog(null, "Araç başarıyla iade edildi.");
            } catch (Exception ex) {
                // Eğer hata olursa adminPanel'de kal ve hata mesajı göster
                JOptionPane.showMessageDialog(null,
                        "İade işlemi sırasında bir hata oluştu: " + ex.getMessage(),
                        "İade Hatası", JOptionPane.ERROR_MESSAGE);

                // Hata durumunda paneli adminPanel olarak bırak
                parentPanel.removeAll();
                parentPanel.add(adminPanel);
                parentPanel.revalidate();
                parentPanel.repaint();
            }
        });
    }
    public static void saveImageFromLabel(JLabel imageLabel, String destDirPath, String fileName) throws IOException {
        if (imageLabel.getIcon() != null) {
            // Klasör kontrolü (yoksa oluştur)
            File destDir = new File(destDirPath);
            if (!destDir.exists()) {
                destDir.mkdir();
            }

            // Dosya yolu
            File destFile = new File(destDir, fileName);

            // ImageIcon'dan BufferedImage oluştur
            ImageIcon icon = (ImageIcon) imageLabel.getIcon();
            Image img = icon.getImage();

            // BufferedImage'e dönüştürme
            BufferedImage bufferedImage = new BufferedImage(
                    img.getWidth(null),
                    img.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB
            );
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(img, 0, 0, null);
            g2d.dispose();

            // Resmi dosyaya yaz
            ImageIO.write(bufferedImage, "png", destFile);
            JOptionPane.showMessageDialog(null, "Image successfully saved as " + fileName);
        } else {
            JOptionPane.showMessageDialog(null, "No image found on the label!");
        }
    }
    public void rentVehicle(int userId, String address, int vehicleId, int daysBetween, JTextField feeField, JPanel parentPanel) {

        String insertSQL = "CALL vehicle_rent(?, ?, ?, ?, ?)";  // Prosedür çağrısı

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

            // Kullanıcıdan alınan bilgileri prosedüre geçiyoruz
            stmt.setInt(1, userId);  // Kullanıcı ID
            stmt.setString(2, address);  // Kullanıcı adresi
            stmt.setInt(3, vehicleId);  // Araç ID
            stmt.setInt(4, daysBetween);  // Kiralama süresi (gün olarak)
            stmt.setInt(5, Integer.parseInt(feeField.getText()));  // Kiralama ücreti

            // Prosedürü çalıştır
            stmt.executeUpdate();

            // Başarılı işlem mesajı
            JOptionPane.showMessageDialog(parentPanel, "Araç başarıyla kiralandı!");

        } catch (SQLException ex) {
            ex.printStackTrace();  // Hata detaylarını yazdır
            JOptionPane.showMessageDialog(parentPanel, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(parentPanel, "Ücret alanı geçerli bir sayı olmalıdır!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static int calculateDaysBetween(String inputDate, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false); // Tarih formatının kesinliğini sağlar
        try {
            // Kullanıcının girdiği tarihi ve sistem tarihini al
            Date enteredDate = sdf.parse(inputDate);
            Date currentDate = new Date(); // Sistem tarihi

            // Milisaniye cinsinden farkı hesapla
            long differenceInMillis = enteredDate.getTime() - currentDate.getTime();

            // Gün cinsinden farkı hesapla
            return (int) (differenceInMillis / (1000 * 60 * 60 * 24) +1);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format. Please use " + dateFormat, e);
        }
    }
    private void setupFormattedFields() {
        try {

            // Kart Numarası (16 haneli)
            MaskFormatter cardNumberFormatter = new MaskFormatter("####-####-####-####");
            cardNumberFormatter.setPlaceholderCharacter('_');
            cardNumberFormatter.install(cardNumberField);

            // Kart Tarihi (MM/YY)
            MaskFormatter cardDateFormatter = new MaskFormatter("##/##");
            cardDateFormatter.setPlaceholderCharacter('_');
            cardDateFormatter.install(cardDateField);

            // Kart CVC (3 haneli)
            MaskFormatter cardCvcFormatter = new MaskFormatter("###");
            cardCvcFormatter.setPlaceholderCharacter('_');
            cardCvcFormatter.install(cardCvcField);

            // Araç İade Tarihi (DD/MM/YYYY)
            MaskFormatter vehicleReturnFormatter = new MaskFormatter("##/##/####");
            vehicleReturnFormatter.setPlaceholderCharacter('_');
            vehicleReturnFormatter.install(vehicleReturnField);

        } catch (ParseException e) {
            throw new RuntimeException("Error setting up formatted fields", e);
        }
    }
    public void toggleAdminMode(boolean enable) {
        if (enable) {
            // Admin moduna geçiş
            carContractButton.setVisible(false);
            motoContractButton.setVisible(false);
            suvContractButton.setVisible(false);
            iadeField.setEditable(false);

            adminEdit1.setVisible(true);
            adminDelete1.setVisible(true);
            adminEdit2.setVisible(true);
            adminDelete2.setVisible(true);
            adminEdit3.setVisible(true);
            adminDelete3.setVisible(true);
            addVehicle1Button.setVisible(true);
            adminContractTable.setVisible(true);
            adminPanelButton.setVisible(true);
        } else {
            // Admin modundan çıkış
            carContractButton.setVisible(true);
            motoContractButton.setVisible(true);
            suvContractButton.setVisible(true);
            iadeField.setEditable(true);

            adminEdit1.setVisible(false);
            adminUpdate1.setVisible(false);
            adminDelete1.setVisible(false);
            adminEdit2.setVisible(false);
            adminUpdate2.setVisible(false);
            adminDelete2.setVisible(false);
            adminEdit3.setVisible(false);
            adminUpdate3.setVisible(false);
            adminDelete3.setVisible(false);
            addVehicle1Button.setVisible(false);
            adminContractTable.setVisible(false);
            adminPanelButton.setVisible(false);
        }
    }// Admin modunu kontrol eden metot
    private void vehicle_return(int userId, int vehicleId) {
        String callProcedureSQL = "CALL return_vehicle(?, ?)";

        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(callProcedureSQL)) {

            // Prosedüre parametreleri aktar
            stmt.setInt(1, userId);
            stmt.setInt(2, vehicleId);

            // Prosedürü çalıştır
            stmt.execute();

            // Kullanıcıya başarı mesajı göster
            JOptionPane.showMessageDialog(null, "Araç başarıyla iade edildi.");

        } catch (SQLException e) {
            // Hata durumunda kullanıcıyı bilgilendir
            JOptionPane.showMessageDialog(null,
                    "Bir hata oluştu: " + e.getMessage(),
                    "İade Hatası", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void updateJeepPanel() {
        // Veritabanından Jeep listesini güncelle
        jeepList = jeep.getAllJeeps();

        suvPanel.removeAll();  // Mevcut elemanları temizle
        suvPanel.setLayout(new GridLayout(0, 2, 10, 10));  // 2 sütunlu bir GridLayout oluştur

        for (Jeep vehicle : jeepList) {
            if (vehicle != null) {  // Null kontrolü
                JPanel singleVehiclePanel = new JPanel(new BorderLayout());  // Her araç için ayrı panel
                singleVehiclePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                singleVehiclePanel.setBackground(new Color(214, 240, 247));  // Arka plan rengi

                // Jeep ID'sini al
                int vehicleId = vehicle.getId();
                String imageFileName = String.format("images/vehicles_%d.png", vehicleId);

                // Resmi kontrol et ve yükle
                ImageIcon vehicleImageIcon;
                File imageFile = new File(imageFileName);
                if (imageFile.exists()) {
                    vehicleImageIcon = new ImageIcon(new ImageIcon(imageFileName)
                            .getImage()
                            .getScaledInstance(150, 100, Image.SCALE_SMOOTH));  // Daha küçük ölçeklendirme
                } else {
                    // Varsayılan bir resim yükle
                    vehicleImageIcon = new ImageIcon(new ImageIcon("images/default_jeep.png")
                            .getImage()
                            .getScaledInstance(150, 100, Image.SCALE_SMOOTH));
                }

                // Resim etiketi
                JLabel vehicleImageLabel = new JLabel(vehicleImageIcon);

                // Araç bilgisi etiketi
                JLabel vehicleInfoLabel = new JLabel(
                        "<html>Make: " + vehicle.getMake() +
                                "<br>Serial: " + vehicle.getSerial() +
                                "<br>Price: " + vehicle.getPrice() + "</html>"
                );
                vehicleInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Metni ortala
                vehicleInfoLabel.setForeground(Color.BLACK);

                // Detay butonu
                JButton detailsButton = new JButton("Show Details");
                detailsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                detailsButton.setBackground(background);
                detailsButton.setForeground(Color.WHITE);

                detailsButton.addActionListener(e -> {
                    vehicleParentPanel.removeAll();
                    vehicleParentPanel.add(suvInfoPanel);
                    vehicleParentPanel.revalidate();
                    vehicleParentPanel.repaint();

                    // Dosya adını dinamik oluştur
                    String fileName = String.format("images/vehicles_%d.png", vehicleId);

                    // Büyük resim ölçeklendirme
                    ImageIcon jeepImage;
                    File largeImageFile = new File(fileName);
                    if (largeImageFile.exists()) {
                        jeepImage = new ImageIcon(new ImageIcon(fileName)
                                .getImage()
                                .getScaledInstance(450, 300, Image.SCALE_SMOOTH));
                    } else {
                        jeepImage = new ImageIcon(new ImageIcon("images/default_jeep.png")
                                .getImage()
                                .getScaledInstance(450, 300, Image.SCALE_SMOOTH));
                    }
                    suvbigInfo.setIcon(jeepImage);

                    // Küçük resim ölçeklendirme
                    ImageIcon smallImage;
                    if (largeImageFile.exists()) {
                        smallImage = new ImageIcon(new ImageIcon(fileName)
                                .getImage()
                                .getScaledInstance(150, 100, Image.SCALE_SMOOTH));
                    } else {
                        smallImage = new ImageIcon(new ImageIcon("images/default_jeep.png")
                                .getImage()
                                .getScaledInstance(150, 100, Image.SCALE_SMOOTH));
                    }
                    contractImage.setIcon(smallImage);

                    vehicleInfo.setText("<html>Make: " + vehicle.getMake() +
                            "<br>Serial: " + vehicle.getSerial() +
                            "<br>Price: " + vehicle.getPrice() + "</html>");

                    currentVehicleId = vehicleId;  // Güncel ID'yi güncelle
                    updateJeepInfoPanel(jeep.getJeepById(currentVehicleId));
                });

                // Panel düzenlemesi: Resim üstte, bilgi ortada, buton altta
                singleVehiclePanel.add(vehicleImageLabel, BorderLayout.WEST);  // Resmi üstte göster
                singleVehiclePanel.add(vehicleInfoLabel, BorderLayout.CENTER); // Bilgileri ortada göster
                singleVehiclePanel.add(detailsButton, BorderLayout.SOUTH);     // Butonu altta göster

                suvPanel.add(singleVehiclePanel);  // Ana panele ekle
            }
        }
        // Paneli yeniden çiz
        suvPanel.revalidate();
        suvPanel.repaint();
    }
    private void updateMotoPanel() {
        // motorList'i veritabanından güncelle
        motorList = motorcycle.getAllMotorcycles(); // Tüm motosikletleri yeniden yükleyin

        motorcyclePanel.removeAll();  // Mevcut elemanları temizle
        motorcyclePanel.setLayout(new GridLayout(0, 2, 10, 10));  // 2 sütunlu bir GridLayout oluştur

        for (Motorcycle vehicle : motorList) {
            if (vehicle != null) {  // Null kontrolü
                JPanel singleMotoPanel = new JPanel(new BorderLayout());
                singleMotoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                singleMotoPanel.setBackground(new Color(214, 240, 247));

                int vehicleId = vehicle.getId(); // ID'yi doğrudan al
                String imageFileName = String.format("images/vehicles_%d.png", vehicleId);

                // Yeni eklenen araç için resim dosyasını kontrol et
                ImageIcon vehicleImageIcon = null;
                File imageFile = new File(imageFileName);
                if (imageFile.exists()) {
                    vehicleImageIcon = new ImageIcon(new ImageIcon(imageFileName)
                            .getImage()
                            .getScaledInstance(150, 100, Image.SCALE_SMOOTH));
                } else {
                    // Varsayılan resim kullan
                    vehicleImageIcon = new ImageIcon(new ImageIcon("images/default_motorcycle.png")
                            .getImage()
                            .getScaledInstance(150, 100, Image.SCALE_SMOOTH));
                }

                JLabel vehicleImageLabel = new JLabel(vehicleImageIcon);

                JLabel vehicleInfoLabel = new JLabel(
                        "<html>Make: " + vehicle.getMake() +
                                "<br>Model: " + vehicle.getModel() +
                                "<br>Price: " + vehicle.getPrice() + "</html>"
                );
                vehicleInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                vehicleInfoLabel.setForeground(Color.BLACK);

                JButton detailsButton = new JButton("Show Details");
                detailsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                detailsButton.setBackground(background);
                detailsButton.setForeground(Color.WHITE);
                detailsButton.addActionListener(e -> {
                    vehicleParentPanel.removeAll();
                    vehicleParentPanel.add(motorInfoPanel);
                    vehicleParentPanel.revalidate();
                    vehicleParentPanel.repaint();

                    System.out.println(vehicleId);

                    String fileName = String.format("images/vehicles_%d.png", vehicleId);

                    ImageIcon carImage = new ImageIcon(new ImageIcon(fileName)
                            .getImage()
                            .getScaledInstance(450, 300, Image.SCALE_SMOOTH));
                    motorbigInfo.setIcon(carImage);

                    ImageIcon a = new ImageIcon(new ImageIcon(fileName)
                            .getImage()
                            .getScaledInstance(150, 100, Image.SCALE_SMOOTH));
                    contractImage.setIcon(a);

                    vehicleInfo.setText("<html>Make: " + vehicle.getMake() +
                            "<br>Model: " + vehicle.getModel() +
                            "<br>Price: " + vehicle.getPrice() + "</html>");

                    currentVehicleId = vehicleId;  // Güncel ID'yi güncelle
                    updateMotorcycleInfoPanel(motorcycle.getMotorcycleById(currentVehicleId));
                });

                singleMotoPanel.add(vehicleImageLabel, BorderLayout.WEST);
                singleMotoPanel.add(vehicleInfoLabel, BorderLayout.CENTER);
                singleMotoPanel.add(detailsButton, BorderLayout.SOUTH);

                motorcyclePanel.add(singleMotoPanel);
            }
        }

        motorcyclePanel.revalidate(); // Layout'u yeniden düzenle
        motorcyclePanel.repaint();   // Paneli yeniden boyayarak görünümü güncelle
    }
    private void updateCarPanel() {
        // Veritabanından araç listesini güncelle
        carList = car.getAllCars();

        carPanel.removeAll();  // Mevcut elemanları temizle
        carPanel.setLayout(new GridLayout(0, 2, 10, 10));  // 2 sütunlu bir GridLayout oluştur

        for (Car vehicle : carList) {
            if (vehicle != null) {  // Null kontrolü
                JPanel singleCarPanel = new JPanel(new BorderLayout());  // Her araç için ayrı panel
                singleCarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                singleCarPanel.setBackground(new Color(214, 240, 247));  // Arka plan rengi

                int vehicleId = vehicle.getId(); // ID'yi doğrudan al
                String imageFileName = String.format("images/vehicles_%d.png", vehicleId);

                // Resmi yükle ve kontrol et
                ImageIcon vehicleImageIcon;
                File imageFile = new File(imageFileName);
                if (imageFile.exists()) {
                    vehicleImageIcon = new ImageIcon(new ImageIcon(imageFileName)
                            .getImage()
                            .getScaledInstance(150, 100, Image.SCALE_SMOOTH));  // Daha küçük ölçeklendirme
                } else {
                    // Varsayılan bir resim yükle
                    vehicleImageIcon = new ImageIcon(new ImageIcon("images/default_car.png")
                            .getImage()
                            .getScaledInstance(150, 100, Image.SCALE_SMOOTH));
                }

                JLabel vehicleImageLabel = new JLabel(vehicleImageIcon);

                JLabel vehicleInfoLabel = new JLabel(
                        "<html>Make: " + vehicle.getMake() +
                                "<br>Serial: " + vehicle.getSerial() +
                                "<br>Price: " + vehicle.getPrice() + "</html>"
                );
                vehicleInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Metni ortala
                vehicleInfoLabel.setForeground(Color.BLACK);

                JButton detailsButton = new JButton("Show Details");
                detailsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                detailsButton.setBackground(background);
                detailsButton.setForeground(Color.WHITE);

                detailsButton.addActionListener(e -> {
                    vehicleParentPanel.removeAll();
                    vehicleParentPanel.add(carInfoPanel);
                    vehicleParentPanel.revalidate();
                    vehicleParentPanel.repaint();

                    String fileName = String.format("images/vehicles_%d.png", vehicleId);

                    // Büyük resmi yükle ve kontrol et
                    ImageIcon carImage;
                    File largeImageFile = new File(fileName);
                    if (largeImageFile.exists()) {
                        carImage = new ImageIcon(new ImageIcon(fileName)
                                .getImage()
                                .getScaledInstance(450, 300, Image.SCALE_SMOOTH));  // Büyük resim ölçeklendirme
                    } else {
                        carImage = new ImageIcon(new ImageIcon("images/default_car.png")
                                .getImage()
                                .getScaledInstance(450, 300, Image.SCALE_SMOOTH));
                    }
                    carbigInfo.setIcon(carImage);

                    // Küçük resmi yükle ve kontrol et
                    ImageIcon smallImage;
                    if (largeImageFile.exists()) {
                        smallImage = new ImageIcon(new ImageIcon(fileName)
                                .getImage()
                                .getScaledInstance(150, 100, Image.SCALE_SMOOTH));
                    } else {
                        smallImage = new ImageIcon(new ImageIcon("images/default_car.png")
                                .getImage()
                                .getScaledInstance(150, 100, Image.SCALE_SMOOTH));
                    }
                    contractImage.setIcon(smallImage);

                    vehicleInfo.setText("<html>Make: " + vehicle.getMake() +
                            "<br>Serial: " + vehicle.getSerial() +
                            "<br>Price: " + vehicle.getPrice() + "</html>");

                    currentVehicleId = vehicleId;  // Güncel ID'yi güncelle
                    updateCarInfoPanel(car.getCarById(currentVehicleId));
                });

                singleCarPanel.add(vehicleImageLabel, BorderLayout.WEST);  // Resmi sol tarafa yerleştir
                singleCarPanel.add(vehicleInfoLabel, BorderLayout.CENTER); // Bilgileri ortada göster
                singleCarPanel.add(detailsButton, BorderLayout.SOUTH);     // Butonu altta göster

                carPanel.add(singleCarPanel);  // Ana panele ekle
            }
        }

        // Paneli yeniden çiz
        carPanel.revalidate();
        carPanel.repaint();
    }
    public void updateCarInfoPanel(Car car) {
        if (car != null) {
            carMakeprivate.setText(car.getMake());
            carSerialprivate.setText(car.getSerial());
            carFuelprivate.setText(car.getFuel());
            carYearprivate.setText(String.valueOf(car.getYear()));
            carKmprivate.setText(String.valueOf(car.getKilometer()));
            carColorprivate.setText(car.getColor());
            carEnginePprivate.setText(car.getEnginePower());
            carEngineCrivate.setText(car.getEngineCapacity());
            carPriceprivate.setText(String.valueOf(car.getPrice()));
            carModelprivate.setText(car.getModel());
            carStockPrivate.setText(String.valueOf(car.getStock()));
        } else {
            JOptionPane.showMessageDialog(null, "Araç bilgisi bulunamadı!");
        }
    }
    public void updateMotorcycleInfoPanel(Motorcycle motorcycle) {
        if (motorcycle != null) {
            motoMakePrivate.setText(motorcycle.getMake());
            motoCoolingPrivate.setText(motorcycle.getCooling());
            motoKmPrivate.setText(String.valueOf(motorcycle.getKilometer()));
            motoCyclinderPrivate.setText(String.valueOf(motorcycle.getCylinders()));
            motoModelPrivate.setText(motorcycle.getModel());
            motoPricePrivate.setText(String.valueOf(motorcycle.getPrice()));
            motoTypePrivate.setText(motorcycle.getType());
            motoYearPrivate.setText(String.valueOf(motorcycle.getYear()));
            motoEngineCPrivate.setText(motorcycle.getEngineCapacity());
            motoEnginePPrivate.setText(motorcycle.getEnginePower());
            motoColorPrivate.setText(motorcycle.getColor());
            motoStockPrivate.setText(String.valueOf(motorcycle.getStock()));
        } else {
            JOptionPane.showMessageDialog(null, "Motosiklet bilgisi bulunamadı!");
        }
    }
    public void updateJeepInfoPanel(Jeep jeep) {
        if (jeep != null) {
            suvMake.setText(jeep.getMake());
            suvTraction.setText(jeep.getTraction());
            suvYear.setText(String.valueOf(jeep.getYear()));
            suvKm.setText(String.valueOf(jeep.getKilometer()));
            suvFuel.setText(jeep.getFuel());
            suvSerial.setText(jeep.getSerial());
            suvModel.setText(jeep.getModel());
            suvPrice.setText(String.valueOf(jeep.getPrice()));
            suvColor.setText(jeep.getColor());
            suvEngineC.setText(jeep.getEngineCapacity());
            suvEngineP.setText(jeep.getEnginePower());
            suvStockP.setText(String.valueOf(jeep.getStock()));
        } else {
            JOptionPane.showMessageDialog(null, "SUV bilgisi bulunamadı!");
        }
    }
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public int getLastId(String tableName) {
        String selectSQL = "SELECT MAX(id) AS max_id FROM " + tableName + ";";
        int lastId = -1;  // Default değer, sorgudan değer gelmezse veya hata oluşursa

        try (Connection conn = createConnection();
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {  // Eğer sonuç dönerse
                lastId = rs.getInt("max_id");  // max_id sütunundaki değeri al
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching last ID from " + tableName + ": " + e.getMessage());
            lastId = -1;  // Hata durumunda default değer
        }

        return lastId;  // Sonucu geri döndür
    }
    public static TableModel getRentedVehicleTable(int userId) {
        // Veritabanı bağlantısı için gerekli bilgiler
        // Veritabanına bağlantı kuruyoruz
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // Kullanıcıya göre kiralanmış araçları almak için SQL sorgusu
            String query = "SELECT * FROM get_rented_vehicles_by_user(?);";

            // DefaultTableModel, verileri JTable için model olarak tutacak
            DefaultTableModel tableModel = new DefaultTableModel();

            // Tablo başlıklarını tanımlıyoruz
            tableModel.addColumn("Id");
            tableModel.addColumn("Make");
            tableModel.addColumn("Model");
            tableModel.addColumn("Fee");
            tableModel.addColumn("Purchase Date");
            tableModel.addColumn("Return Date");

            // JTable nesnesi
            JTable rentedVehicleTable = new JTable(tableModel);

            // Tablo başlıklarını siyah yapmak için
            JTableHeader tableHeader = rentedVehicleTable.getTableHeader();
            tableHeader.setForeground(Color.BLACK); // Başlıkları siyah yapıyoruz

            // İlgili sorguyu hazırlıyoruz
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);

                // Sorguyu çalıştırıyoruz
                try (ResultSet rs = stmt.executeQuery()) {
                    // ResultSet'teki verileri alıp tablo modeline ekliyoruz
                    while (rs.next()) {
                        Object[] row = new Object[6]; // Sadece 5 kolon var
                        row[0] = rs.getString("vehicle_id");
                        row[1] = rs.getString("make");  // Aracın markası
                        row[2] = rs.getString("model");  // Aracın modeli
                        row[3] = rs.getInt("fee");      // Kira ücreti
                        row[4] = rs.getDate("purchase_date"); // Satın alma tarihi
                        row[5] = rs.getDate("return_date");   // İade tarihi
                        // Satırı tabloya ekliyoruz
                        tableModel.addRow(row);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage());
            }

            return tableModel;

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage());
            return null;
        }
    }
    public static TableModel getAllRentedVehiclesTable() {
        // Veritabanı bağlantısı için gerekli bilgiler

        // SQL sorgusu
        String query = "SELECT * FROM get_all_rented_vehicles();";

        // DefaultTableModel, verileri JTable için model olarak tutacak
        DefaultTableModel tableModel = new DefaultTableModel();

        // Tablo başlıklarını tanımlıyoruz

        tableModel.addColumn("User ID");
        tableModel.addColumn("Address");
        tableModel.addColumn("Purchase Date");
        tableModel.addColumn("Return Date");
        tableModel.addColumn("Vehicle ID");
        tableModel.addColumn("Fee");

        // Veritabanına bağlantı kuruyoruz
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Verileri alıp tablo modeline ekliyoruz
            while (rs.next()) {
                Object[] row = new Object[6];

                row[0] = rs.getInt("user_id"); // Kullanıcı ID'si
                row[1] = rs.getString("address"); // Kullanıcı adresi
                row[2] = rs.getDate("purchase_date"); // Satın alma tarihi
                row[3] = rs.getDate("return_date"); // İade tarihi
                row[4] = rs.getInt("vehicle_id"); // Araç ID'si
                row[5] = rs.getInt("fee"); // Ücret

                // Satırı tabloya ekliyoruz
                tableModel.addRow(row);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage());
        }

        return tableModel; // Sadece TableModel döndürülüyor
    }
    private void createUIComponents() {
        phoneLogo = new JLabel();
        ImageIcon phonelogoIcon = new ImageIcon(new ImageIcon("images/phone.png").getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH));
        phoneLogo.setIcon(phonelogoIcon);

        locationLogo = new JLabel();
        ImageIcon locationlogoIcon = new ImageIcon(new ImageIcon("images/location.png").getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH));
        locationLogo.setIcon(locationlogoIcon);

        instaLogo = new JLabel();
        ImageIcon instalogoIcon = new ImageIcon(new ImageIcon("images/insta.png").getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH));
        instaLogo.setIcon(instalogoIcon);

        mailLogo = new JLabel();
        ImageIcon maillogoIcon = new ImageIcon(new ImageIcon("images/mail.png").getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH));
        mailLogo.setIcon(maillogoIcon);

        homeLogo = new JLabel();
        ImageIcon homelogoIcon = new ImageIcon(new ImageIcon("images/SwiftWheels.png").getImage().getScaledInstance(400,220, Image.SCALE_SMOOTH));
        homeLogo.setIcon(homelogoIcon);

        LogoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon(new ImageIcon("images/yatay.png").getImage().getScaledInstance(300,100, Image.SCALE_SMOOTH));
        LogoLabel.setIcon(logoIcon);

        car1Logo = new JLabel();
        ImageIcon carIcon = new ImageIcon(new ImageIcon("images/Car-Transparent-Background1.png").getImage().getScaledInstance(150,100, Image.SCALE_SMOOTH));
        car1Logo.setIcon(carIcon);

        car2Logo = new JLabel();
        ImageIcon car2Icon = new ImageIcon(new ImageIcon("images/Car-Transparent-Background2.png").getImage().getScaledInstance(150,100, Image.SCALE_SMOOTH));
        car2Logo.setIcon(car2Icon);

        motorLogo = new JLabel();
        ImageIcon motorIcon = new ImageIcon(new ImageIcon("images/Motorcycle-Transparent-Background1.png").getImage().getScaledInstance(150,100, Image.SCALE_SMOOTH));
        motorLogo.setIcon(motorIcon);

        ppLabel = new JLabel();
        ImageIcon ppIcon = new ImageIcon(new  ImageIcon("images/Pp.png").getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH));
        ppLabel.setIcon(ppIcon);
    }//Kullandığımız pngler burann içinde
}