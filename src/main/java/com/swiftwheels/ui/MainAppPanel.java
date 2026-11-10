package com.swiftwheels.ui;

import com.swiftwheels.model.Car;
import com.swiftwheels.model.Customer;
import com.swiftwheels.model.CustomerRepository;
import com.swiftwheels.model.Jeep;
import com.swiftwheels.model.Motorcycle;
import com.swiftwheels.model.RentalPricing;
import com.swiftwheels.model.RentalRepository;
import com.swiftwheels.model.Vehicle;
import com.swiftwheels.model.VehicleRepository;
import com.swiftwheels.model.VehicleType;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/** The application window after login. The layout lives in MainAppPanel.form (IntelliJ GUI Designer). */
public class MainAppPanel {
    private static final Color ACCENT = new Color(76, 147, 152);
    private static final Color CARD_BACKGROUND = new Color(214, 240, 247);
    private static final int MAX_RENTAL_DAYS = 365;
    private static final DateTimeFormatter RETURN_DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private final int userId;
    private final boolean userIsAdmin;
    private boolean adminMode;
    private boolean vehiclesLoaded;
    /** Vehicle whose detail page / rental contract is currently open. */
    private Vehicle currentVehicle;
    /** Photo picked on the "add vehicle" page, saved after the vehicle got its id. */
    private File pendingVehiclePhoto;
    private JScrollPane myRentalsScroll;
    private JScrollPane allRentalsScroll;

    // ---- components bound from MainAppPanel.form (names must match the form) ----
    private JPanel mainPanel1;
    private JLabel LogoLabel;
    private JButton homeButton;
    private JButton accountButton;
    private JButton contractButton;
    private JButton vehicleButton;
    private JPanel InnerLogoPanel;
    private JPanel MenuPanel;
    private JPanel parentPanel;
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
    private JTextArea nameArea;
    private JTextArea surnameArea;
    private JTextArea phoneArea;
    private JTextArea emailArea;
    private JTextArea passwordArea;
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
    private JTextArea carMakeprivate;
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
    private JTextField addressField;
    private JButton rentButton;
    private JTextField feeField;
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
    private JLabel adminKeyLabel;
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

    public MainAppPanel(int userId, boolean userIsAdmin) {
        this.userId = userId;
        this.userIsAdmin = userIsAdmin;

        feeField.setEditable(false);
        cinfo.setEditable(false);
        installInputMasks();

        setupMenu();
        setupAccountPage();
        setupVehicleBrowsing();
        setupAdminVehicleEditing();
        setupAddVehiclePage();
        setupRentalPage();
        setupContractsPage();
        setupAdminAccess();
    }

    public static void showWindow(Customer customer) {
        LoginRegisterPanel.closeAllFrames();
        JFrame frame = new JFrame("SwiftWheels - " + customer.name() + " " + customer.surname()
                + (customer.admin() ? " (admin)" : ""));
        frame.setContentPane(new MainAppPanel(customer.id(), customer.admin()).mainPanel1);
        frame.setIconImage(Images.icon("SwiftWheels.png", 64, 64).getImage());
        frame.setPreferredSize(new Dimension(1250, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // =====================================================================
    //  Navigation
    // =====================================================================

    private void setupMenu() {
        menu.addActionListener(e -> {
            menuOpen.setVisible(false);
            MenuClose.setVisible(true);
            MenuPanel.setVisible(true);
        });
        menuCloseButton.addActionListener(e -> {
            MenuClose.setVisible(true);
            menuOpen.setVisible(true);
            MenuPanel.setVisible(false);
        });
        homeButton.addActionListener(e -> showPage(homePanel, false));
        vehicleButton.addActionListener(e -> openVehicles());
        contractButton.addActionListener(e -> openContracts());
        accountButton.addActionListener(e -> openAccount());
        adminPanelButton.addActionListener(e -> openAdminDashboard());
        logOutButton.addActionListener(e -> LoginRegisterPanel.closeAllFrames());
    }

    private void showPage(JComponent page, boolean showVehicleTypeBar) {
        parentPanel.removeAll();
        parentPanel.add(page);
        parentPanel.revalidate();
        parentPanel.repaint();
        vehicleTypePanel.setVisible(showVehicleTypeBar);
    }

    private void showVehiclePage(JPanel page) {
        vehicleParentPanel.removeAll();
        vehicleParentPanel.add(page);
        vehicleParentPanel.revalidate();
        vehicleParentPanel.repaint();
    }

    private VehicleType selectedType() {
        if (motorcycleRadioButton.isSelected()) {
            return VehicleType.MOTORCYCLE;
        }
        if (suvRadioButton.isSelected()) {
            return VehicleType.JEEP;
        }
        return VehicleType.CAR;
    }

    private void selectType(VehicleType type) {
        switch (type) {
            case CAR -> carRadioButton.setSelected(true);
            case JEEP -> suvRadioButton.setSelected(true);
            case MOTORCYCLE -> motorcycleRadioButton.setSelected(true);
        }
    }

    private JPanel listPanelOf(VehicleType type) {
        return switch (type) {
            case CAR -> carPanel;
            case JEEP -> suvPanel;
            case MOTORCYCLE -> motorcyclePanel;
        };
    }

    private JPanel infoPanelOf(VehicleType type) {
        return switch (type) {
            case CAR -> carInfoPanel;
            case JEEP -> suvInfoPanel;
            case MOTORCYCLE -> motorInfoPanel;
        };
    }

    // =====================================================================
    //  Vehicle list + detail pages
    // =====================================================================

    private void setupVehicleBrowsing() {
        if (!carRadioButton.isSelected() && !suvRadioButton.isSelected() && !motorcycleRadioButton.isSelected()) {
            carRadioButton.setSelected(true);
        }
        carRadioButton.addActionListener(e -> showVehiclePage(carPanel));
        suvRadioButton.addActionListener(e -> showVehiclePage(suvPanel));
        motorcycleRadioButton.addActionListener(e -> showVehiclePage(motorcyclePanel));
        backButton.addActionListener(e -> showVehiclePage(listPanelOf(selectedType())));
        seeVehiclesButton.addActionListener(e -> openVehicles());
    }

    private void openVehicles() {
        if (!vehiclesLoaded && !reloadVehicles()) {
            return;
        }
        showPage(vehiclesMenuPanel, true);
        showVehiclePage(listPanelOf(selectedType()));
    }

    /** Re-reads all vehicles from the database and rebuilds the three card lists. */
    private boolean reloadVehicles() {
        try {
            renderCards(carPanel, VehicleRepository.findAllCars(), c -> "Serial: " + c.getSerial());
            renderCards(suvPanel, VehicleRepository.findAllJeeps(), j -> "Serial: " + j.getSerial());
            renderCards(motorcyclePanel, VehicleRepository.findAllMotorcycles(), m -> "Type: " + m.getMotorType());
            vehiclesLoaded = true;
            return true;
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel1, ex);
            return false;
        }
    }

    private <T extends Vehicle> void renderCards(JPanel target, List<T> vehicles, Function<T, String> detailLine) {
        target.removeAll();
        target.setLayout(new GridLayout(0, 2, 10, 10));
        if (vehicles.isEmpty()) {
            target.add(new JLabel("Bu kategoride henüz araç yok.", SwingConstants.CENTER));
        }
        for (T vehicle : vehicles) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            card.setBackground(CARD_BACKGROUND);

            JLabel info = new JLabel(summaryHtml(vehicle, detailLine.apply(vehicle)), SwingConstants.CENTER);
            info.setForeground(Color.BLACK);

            JButton details = new JButton("Show Details");
            details.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            details.setBackground(ACCENT);
            details.setForeground(Color.WHITE);
            details.addActionListener(e -> openDetails(vehicle.getType(), vehicle.getId()));

            card.add(new JLabel(Images.vehicleIcon(vehicle, 150, 100)), BorderLayout.WEST);
            card.add(info, BorderLayout.CENTER);
            card.add(details, BorderLayout.SOUTH);
            target.add(card);
        }
        target.revalidate();
        target.repaint();
    }

    private static String summaryHtml(Vehicle v, String detailLine) {
        String stock = v.getStock() > 0 ? "Stock: " + v.getStock() : "<font color='#b00020'>Stokta yok</font>";
        return "<html><b>" + escape(v.getMake()) + " " + escape(v.getModel()) + "</b>"
                + "<br>" + escape(detailLine)
                + "<br>Price: " + v.getPrice() + " $ / day"
                + "<br>" + stock + "</html>";
    }

    private static String escape(String s) {
        return s == null ? "" : s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    /** Loads a fresh copy of the vehicle (stock may have changed) and shows its detail page. */
    private void openDetails(VehicleType type, int id) {
        Optional<Vehicle> found;
        try {
            found = VehicleRepository.findById(type, id);
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel1, ex);
            return;
        }
        if (found.isEmpty()) {
            Dialogs.error(mainPanel1, "Araç bulunamadı (silinmiş olabilir). Liste yenileniyor.");
            reloadVehicles();
            showVehiclePage(listPanelOf(type));
            return;
        }
        Vehicle v = found.get();
        currentVehicle = v;
        ImageIcon big = Images.vehicleIcon(v, 450, 300);
        switch (type) {
            case CAR -> {
                Car c = (Car) v;
                setTexts(carFields(), c.getMake(), c.getModel(), c.getYear(), c.getKilometer(), c.getColor(),
                        c.getEnginePower(), c.getEngineCapacity(), c.getPrice(), c.getStock(), c.getSerial(), c.getFuel());
                carbigInfo.setIcon(big);
            }
            case JEEP -> {
                Jeep j = (Jeep) v;
                setTexts(jeepFields(), j.getMake(), j.getModel(), j.getYear(), j.getKilometer(), j.getColor(),
                        j.getEnginePower(), j.getEngineCapacity(), j.getPrice(), j.getStock(), j.getSerial(), j.getFuel(),
                        j.getTraction());
                suvbigInfo.setIcon(big);
            }
            case MOTORCYCLE -> {
                Motorcycle m = (Motorcycle) v;
                setTexts(motorcycleFields(), m.getMake(), m.getModel(), m.getYear(), m.getKilometer(), m.getColor(),
                        m.getEnginePower(), m.getEngineCapacity(), m.getPrice(), m.getStock(), m.getMotorType(),
                        m.getCooling(), m.getCylinders());
                motorbigInfo.setIcon(big);
            }
        }
        contractImage.setIcon(Images.vehicleIcon(v, 150, 100));
        vehicleInfo.setText("<html><b>" + escape(v.getMake()) + " " + escape(v.getModel()) + "</b>"
                + "<br>Year: " + v.getYear() + "<br>Price: " + v.getPrice() + " $ / day</html>");
        setEditing(type, false);
        showVehiclePage(infoPanelOf(type));
    }

    // The order of these arrays is the order used by setTexts / parsing below.
    private JTextArea[] carFields() {
        return new JTextArea[]{carMakeprivate, carModelprivate, carYearprivate, carKmprivate, carColorprivate,
                carEnginePprivate, carEngineCrivate, carPriceprivate, carStockPrivate, carSerialprivate, carFuelprivate};
    }

    private JTextArea[] jeepFields() {
        return new JTextArea[]{suvMake, suvModel, suvYear, suvKm, suvColor, suvEngineP, suvEngineC, suvPrice,
                suvStockP, suvSerial, suvFuel, suvTraction};
    }

    private JTextArea[] motorcycleFields() {
        return new JTextArea[]{motoMakePrivate, motoModelPrivate, motoYearPrivate, motoKmPrivate, motoColorPrivate,
                motoEnginePPrivate, motoEngineCPrivate, motoPricePrivate, motoStockPrivate, motoTypePrivate,
                motoCoolingPrivate, motoCyclinderPrivate};
    }

    private JTextArea[] fieldsOf(VehicleType type) {
        return switch (type) {
            case CAR -> carFields();
            case JEEP -> jeepFields();
            case MOTORCYCLE -> motorcycleFields();
        };
    }

    private static void setTexts(JTextArea[] fields, Object... values) {
        for (int i = 0; i < fields.length; i++) {
            fields[i].setText(values[i] == null ? "" : String.valueOf(values[i]));
        }
    }

    // =====================================================================
    //  Admin: edit / update / delete / add vehicles
    // =====================================================================

    private void setupAdminVehicleEditing() {
        adminEdit1.addActionListener(e -> setEditing(VehicleType.CAR, true));
        adminEdit2.addActionListener(e -> setEditing(VehicleType.MOTORCYCLE, true));
        adminEdit3.addActionListener(e -> setEditing(VehicleType.JEEP, true));
        adminUpdate1.addActionListener(e -> saveEdits(VehicleType.CAR));
        adminUpdate2.addActionListener(e -> saveEdits(VehicleType.MOTORCYCLE));
        adminUpdate3.addActionListener(e -> saveEdits(VehicleType.JEEP));
        adminDelete1.addActionListener(e -> deleteCurrentVehicle());
        adminDelete2.addActionListener(e -> deleteCurrentVehicle());
        adminDelete3.addActionListener(e -> deleteCurrentVehicle());
    }

    private JButton editButtonOf(VehicleType t) {
        return switch (t) {case CAR -> adminEdit1; case MOTORCYCLE -> adminEdit2; case JEEP -> adminEdit3;};
    }

    private JButton updateButtonOf(VehicleType t) {
        return switch (t) {case CAR -> adminUpdate1; case MOTORCYCLE -> adminUpdate2; case JEEP -> adminUpdate3;};
    }

    private JButton deleteButtonOf(VehicleType t) {
        return switch (t) {case CAR -> adminDelete1; case MOTORCYCLE -> adminDelete2; case JEEP -> adminDelete3;};
    }

    private JButton contractButtonOf(VehicleType t) {
        return switch (t) {case CAR -> carContractButton; case MOTORCYCLE -> motoContractButton; case JEEP -> suvContractButton;};
    }

    private void setEditing(VehicleType type, boolean editing) {
        for (JTextArea field : fieldsOf(type)) {
            field.setEditable(editing);
        }
        editButtonOf(type).setVisible(adminMode && !editing);
        updateButtonOf(type).setVisible(adminMode && editing);
        deleteButtonOf(type).setVisible(adminMode && !editing);
    }

    private void saveEdits(VehicleType type) {
        if (currentVehicle == null) {
            return;
        }
        try {
            Vehicle edited = readVehicle(type, currentVehicle.getId(), fieldsOf(type));
            VehicleRepository.save(edited);
            reloadVehicles();
            openDetails(type, edited.getId());
            Dialogs.info(mainPanel1, "Araç bilgileri güncellendi.");
        } catch (InvalidInputException ex) {
            Dialogs.error(mainPanel1, ex.getMessage());
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel1, ex);
        }
    }

    private void deleteCurrentVehicle() {
        Vehicle v = currentVehicle;
        if (v == null || !Dialogs.confirm(mainPanel1, v + " (ID " + v.getId() + ") silinsin mi?", "Aracı Sil")) {
            return;
        }
        try {
            VehicleRepository.delete(v.getType(), v.getId());
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel1, ex);
            return;
        }
        Images.deleteVehiclePhoto(v.getId());
        currentVehicle = null;
        reloadVehicles();
        showVehiclePage(listPanelOf(v.getType()));
        Dialogs.info(mainPanel1, "Araç silindi.");
    }

    private void setupAddVehiclePage() {
        addVehicle1Button.addActionListener(e -> openAddVehiclePage());
        imageUploadButton.addActionListener(e -> chooseVehiclePhoto());
        addVehicle2Button.addActionListener(e -> addVehicle());
    }

    private JTextField[] addVehicleFields() {
        return new JTextField[]{addVehicleMakeText, addVehicleModelText, addVehicleYearText, addVehicleKilometerText,
                addVehicleColorText, addVehicleEnginePowerText, addVehicleEngineCapacityText, addVehiclePriceText,
                addVehicleStockText, addVehicleSerialText, addVehicleFuelText, addJeepTractionText,
                addMotoTypeText, addMotoCoolingText, addMotoCylinderText};
    }

    private void openAddVehiclePage() {
        VehicleType type = selectedType();
        pendingVehiclePhoto = null;
        imageLabel.setIcon(Images.icon("pictureimage.png", 400, 300));
        for (JTextField f : addVehicleFields()) {
            f.setText("");
        }
        boolean serialAndFuel = type != VehicleType.MOTORCYCLE;
        for (JComponent c : new JComponent[]{addVehicleSerial, addVehicleSerialText, addVehicleFuel, addVehicleFuelText}) {
            c.setVisible(serialAndFuel);
        }
        addJeepTraction.setVisible(type == VehicleType.JEEP);
        addJeepTractionText.setVisible(type == VehicleType.JEEP);
        for (JComponent c : new JComponent[]{addMotoType, addMotoTypeText, addMotoCooling, addMotoCoolingText,
                addMotoCylinder, addMotoCylinderText}) {
            c.setVisible(type == VehicleType.MOTORCYCLE);
        }
        showVehiclePage(addVehiclePanel);
    }

    private void chooseVehiclePhoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Araç fotoğrafı seçin");
        chooser.setFileFilter(new FileNameExtensionFilter("Resim dosyaları (png, jpg, gif, bmp)",
                "png", "jpg", "jpeg", "gif", "bmp"));
        if (chooser.showOpenDialog(mainPanel1) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        try {
            imageLabel.setIcon(Images.fromFile(file, 450, 200));
            pendingVehiclePhoto = file;
        } catch (IOException ex) {
            Dialogs.error(mainPanel1, "Resim yüklenemedi: " + ex.getMessage());
        }
    }

    private void addVehicle() {
        VehicleType type = selectedType();
        JTextField[] f = addVehicleFields();
        JTextComponent[] fields = switch (type) {
            case CAR -> new JTextComponent[]{f[0], f[1], f[2], f[3], f[4], f[5], f[6], f[7], f[8], f[9], f[10]};
            case JEEP -> new JTextComponent[]{f[0], f[1], f[2], f[3], f[4], f[5], f[6], f[7], f[8], f[9], f[10], f[11]};
            case MOTORCYCLE -> new JTextComponent[]{f[0], f[1], f[2], f[3], f[4], f[5], f[6], f[7], f[8], f[12], f[13], f[14]};
        };
        int id;
        try {
            id = VehicleRepository.save(readVehicle(type, 0, fields));
        } catch (InvalidInputException ex) {
            Dialogs.error(mainPanel1, ex.getMessage());
            return;
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel1, ex);
            return;
        }
        String photoNote = "";
        if (pendingVehiclePhoto != null) {
            try {
                Images.saveVehiclePhoto(pendingVehiclePhoto, id);
            } catch (IOException ex) {
                photoNote = "\n(Fotoğraf kaydedilemedi: " + ex.getMessage() + ")";
            }
        }
        reloadVehicles();
        showVehiclePage(listPanelOf(type));
        Dialogs.info(mainPanel1, "Araç eklendi (ID " + id + ")." + photoNote);
    }

    /**
     * Builds a vehicle from text fields ordered like {@link #carFields()} / {@link #jeepFields()} /
     * {@link #motorcycleFields()}: make, model, year, km, color, power, capacity, price, stock, then type-specific.
     */
    private static Vehicle readVehicle(VehicleType type, int id, JTextComponent[] f) throws InvalidInputException {
        String make = required(f[0], "Make");
        String model = required(f[1], "Model");
        int year = number(f[2], "Year", 1950, Year.now().getValue() + 1);
        int km = number(f[3], "Kilometer", 0, 5_000_000);
        String color = required(f[4], "Color");
        String power = required(f[5], "Engine Power");
        String capacity = required(f[6], "Engine Capacity");
        int price = number(f[7], "Price", 1, 1_000_000);
        int stock = number(f[8], "Stock", 0, 1000);
        return switch (type) {
            case CAR -> new Car(id, required(f[9], "Serial"), required(f[10], "Fuel"),
                    make, model, year, km, color, power, capacity, price, stock);
            case JEEP -> new Jeep(id, required(f[9], "Serial"), required(f[10], "Fuel"), required(f[11], "Traction"),
                    make, model, year, km, color, power, capacity, price, stock);
            case MOTORCYCLE -> new Motorcycle(id, required(f[9], "Type"), required(f[10], "Cooling"),
                    required(f[11], "Cylinder"), make, model, year, km, color, power, capacity, price, stock);
        };
    }

    private static String required(JTextComponent field, String label) throws InvalidInputException {
        String value = field.getText().trim();
        if (value.isEmpty()) {
            throw new InvalidInputException("\"" + label + "\" alanı boş bırakılamaz.");
        }
        return value;
    }

    private static int number(JTextComponent field, String label, int min, int max) throws InvalidInputException {
        String value = field.getText().trim().replace(".", "").replace(",", "");
        try {
            int n = Integer.parseInt(value);
            if (n < min || n > max) {
                throw new InvalidInputException("\"" + label + "\" " + min + " ile " + max + " arasında olmalı.");
            }
            return n;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("\"" + label + "\" alanına sayı girin.");
        }
    }

    private static final class InvalidInputException extends Exception {
        InvalidInputException(String message) {
            super(message);
        }
    }

    // =====================================================================
    //  Renting
    // =====================================================================

    private void installInputMasks() {
        installMask(cardNumberField, "####-####-####-####");
        installMask(cardDateField, "##/##");
        installMask(cardCvcField, "###");
        installMask(vehicleReturnField, "##/##/####");
    }

    private static void installMask(JFormattedTextField field, String pattern) {
        try {
            MaskFormatter mask = new MaskFormatter(pattern);
            mask.setPlaceholderCharacter('_');
            field.setFormatterFactory(new DefaultFormatterFactory(mask));
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    private void setupRentalPage() {
        for (VehicleType t : VehicleType.values()) {
            contractButtonOf(t).addActionListener(e -> openRentalForm());
        }
        acceptCheckbox.addActionListener(e -> updateFeePreview());
        wantCheckbox.addActionListener(e -> updateFeePreview());
        vehicleReturnField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) {updateFeePreview();}
            @Override public void removeUpdate(DocumentEvent e) {updateFeePreview();}
            @Override public void changedUpdate(DocumentEvent e) {updateFeePreview();}
        });
        rentButton.addActionListener(e -> submitRental());
    }

    private void openRentalForm() {
        if (currentVehicle == null) {
            return;
        }
        if (currentVehicle.getStock() <= 0) {
            Dialogs.error(mainPanel1, "Bu aracın stoğu tükendi, şu an kiralanamaz.");
            return;
        }
        clearRentalForm();
        showPage(contractActionPanel, false);
    }

    private void clearRentalForm() {
        for (JFormattedTextField f : new JFormattedTextField[]{cardNumberField, cardDateField, cardCvcField, vehicleReturnField}) {
            f.setValue(null);
            f.setText("");
        }
        addressField.setText("");
        feeField.setText("");
        wantCheckbox.setSelected(false);
        acceptCheckbox.setSelected(false);
    }

    /** Rental length in days, or empty if the return date is missing / invalid / not in the future. */
    private Optional<Integer> rentalDays() {
        String text = vehicleReturnField.getText();
        if (text == null || text.contains("_") || text.isBlank()) {
            return Optional.empty();
        }
        try {
            LocalDate returnDate = LocalDate.parse(text, RETURN_DATE_FORMAT);
            long days = ChronoUnit.DAYS.between(LocalDate.now(), returnDate);
            return days >= 1 ? Optional.of((int) days) : Optional.empty();
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    private void updateFeePreview() {
        Optional<Integer> days = rentalDays();
        if (currentVehicle == null || days.isEmpty()) {
            feeField.setText("");
            return;
        }
        int fee = RentalPricing.calculateFee(currentVehicle.getPrice(), days.get(), wantCheckbox.isSelected());
        feeField.setText(fee + " $  (" + days.get() + " gün)");
    }

    private void submitRental() {
        Vehicle v = currentVehicle;
        if (v == null) {
            return;
        }
        String cardNumber = cardNumberField.getText().replaceAll("[^0-9]", "");
        String cvc = cardCvcField.getText().replaceAll("[^0-9]", "");
        String address = addressField.getText().trim();
        Optional<Integer> days = rentalDays();

        String error = null;
        if (cardNumber.length() != 16) {
            error = "Kart numarası 16 haneli olmalı.";
        } else if (!cardDateValid(cardDateField.getText())) {
            error = "Kartın son kullanma tarihi (AA/YY) geçersiz veya geçmiş.";
        } else if (cvc.length() != 3) {
            error = "CVC 3 haneli olmalı.";
        } else if (days.isEmpty()) {
            error = "İade tarihini GG/AA/YYYY biçiminde, bugünden sonraki bir gün olarak girin.";
        } else if (days.get() > MAX_RENTAL_DAYS) {
            error = "En fazla " + MAX_RENTAL_DAYS + " gün kiralanabilir.";
        } else if (address.isEmpty()) {
            error = "Adres alanı boş bırakılamaz.";
        } else if (!acceptCheckbox.isSelected()) {
            error = "Devam etmek için sözleşmeyi kabul edin.";
        }
        if (error != null) {
            Dialogs.error(mainPanel1, error);
            return;
        }

        int fee = RentalPricing.calculateFee(v.getPrice(), days.get(), wantCheckbox.isSelected());
        try {
            RentalRepository.rent(userId, address, v.getId(), days.get(), fee);
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel1, ex);
            return;
        }
        clearRentalForm();
        vehiclesLoaded = false; // stock changed
        showPage(homePanel, false);
        Dialogs.info(mainPanel1, v + " " + days.get() + " günlüğüne kiralandı. Toplam ücret: " + fee + " $");
    }

    private static boolean cardDateValid(String text) {
        String digits = text == null ? "" : text.replaceAll("[^0-9]", "");
        if (digits.length() != 4) {
            return false;
        }
        int month = Integer.parseInt(digits.substring(0, 2));
        int year = 2000 + Integer.parseInt(digits.substring(2));
        return month >= 1 && month <= 12 && !YearMonth.of(year, month).isBefore(YearMonth.now());
    }

    // =====================================================================
    //  Contracts (my rentals / all rentals) + returning a vehicle
    // =====================================================================

    private void setupContractsPage() {
        myRentalsScroll = wrapInScrollPane(myRentedVehicleTable);
        allRentalsScroll = wrapInScrollPane(adminContractTable);
        iadeButton.addActionListener(e -> returnVehicle());
        myRentedVehicleTable.getSelectionModel().addListSelectionListener(e -> {
            int row = myRentedVehicleTable.getSelectedRow();
            if (!e.getValueIsAdjusting() && row >= 0) {
                iadeField.setText(String.valueOf(myRentedVehicleTable.getValueAt(row, 0)));
            }
        });
    }

    private void openContracts() {
        try {
            if (adminMode) {
                adminContractTable.setModel(RentalRepository.allRentals());
            } else {
                myRentedVehicleTable.setModel(RentalRepository.activeRentalsOf(userId));
            }
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel1, ex);
            return;
        }
        allRentalsScroll.setVisible(adminMode);
        myRentalsScroll.setVisible(!adminMode);
        // contractActionPanel may have been moved out by openRentalForm(); always start on the list card.
        ((CardLayout) contractParentPanel.getLayout()).first(contractParentPanel);
        showPage(contractParentPanel, false);
    }

    /**
     * A JTable only shows its column header inside a JScrollPane. The form places the tables directly
     * in a panel, so swap each one for a scroll pane at the same grid position.
     */
    private static JScrollPane wrapInScrollPane(JTable table) {
        Container parent = table.getParent();
        int index = parent.getComponentZOrder(table);
        Object constraints = parent.getLayout() instanceof GridLayoutManager grid
                ? grid.getConstraintsForComponent(table)
                : null;
        parent.remove(table);
        JScrollPane scroll = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        parent.add(scroll, constraints, index);
        return scroll;
    }

    private void returnVehicle() {
        int vehicleId;
        try {
            vehicleId = Integer.parseInt(iadeField.getText().trim());
        } catch (NumberFormatException e) {
            Dialogs.error(mainPanel1, "İade etmek istediğiniz aracın ID'sini girin (tablodaki satıra tıklayabilirsiniz).");
            return;
        }
        try {
            RentalRepository.returnVehicle(userId, vehicleId);
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel1, ex);
            return;
        }
        iadeField.setText("");
        vehiclesLoaded = false; // stock changed
        openContracts();
        Dialogs.info(mainPanel1, "Araç başarıyla iade edildi.");
    }

    // =====================================================================
    //  Account page
    // =====================================================================

    private void setupAccountPage() {
        editButton.addActionListener(e -> setAccountEditing(true));
        updateButton.addActionListener(e -> saveProfile());
        deleteButton.addActionListener(e -> deleteAccount());
        signOutButton.addActionListener(e -> LoginRegisterPanel.showWindow());
        emailArea.setEditable(false);
        passwordArea.setToolTipText("Yeni şifre (boş bırakırsanız şifreniz değişmez)");
    }

    private void openAccount() {
        if (!loadProfile()) {
            return;
        }
        setAccountEditing(false);
        showPage(accountPanel, false);
    }

    private boolean loadProfile() {
        try {
            Optional<String[]> profile = CustomerRepository.readProfile(userId);
            if (profile.isEmpty()) {
                Dialogs.error(mainPanel1, "Hesap bulunamadı.");
                LoginRegisterPanel.showWindow();
                return false;
            }
            String[] p = profile.get();
            nameArea.setText(p[0]);
            surnameArea.setText(p[1]);
            phoneArea.setText(p[2]);
            emailArea.setText(p[3]);
            return true;
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel1, ex);
            return false;
        }
    }

    private void setAccountEditing(boolean editing) {
        nameArea.setEditable(editing);
        surnameArea.setEditable(editing);
        phoneArea.setEditable(editing);
        passwordArea.setEditable(editing);
        passwordArea.setText(editing ? "" : "********");

        editButton.setVisible(!editing);
        signOutButton.setVisible(!editing);
        deleteButton.setVisible(!editing);
        adminButton.setVisible(userIsAdmin && !editing);
        updateButton.setVisible(editing);
    }

    private void saveProfile() {
        String name = nameArea.getText().trim();
        String surname = surnameArea.getText().trim();
        String phone = phoneArea.getText().replaceAll("[^0-9]", "");
        String newPassword = passwordArea.getText().isEmpty() ? null : passwordArea.getText();

        String error = Customer.validate(name, surname, phone, emailArea.getText().trim(), newPassword);
        if (error != null) {
            Dialogs.error(mainPanel1, error);
            return;
        }
        try {
            CustomerRepository.updateProfile(userId, name, surname, phone, newPassword);
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel1, ex);
            return;
        }
        setAccountEditing(false);
        loadProfile();
        Dialogs.info(mainPanel1, newPassword == null ? "Bilgileriniz güncellendi." : "Bilgileriniz ve şifreniz güncellendi.");
    }

    private void deleteAccount() {
        if (!Dialogs.confirm(mainPanel1, "Hesabınız kalıcı olarak silinsin mi?", "Hesap Silme")) {
            return;
        }
        try {
            CustomerRepository.delete(userId);
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel1, ex);
            return;
        }
        Dialogs.info(mainPanel1, "Hesabınız silindi.");
        LoginRegisterPanel.showWindow();
    }

    // =====================================================================
    //  Admin mode + dashboard
    // =====================================================================

    /**
     * Admin rights come from the {@code is_admin} column, not from a typed keyword:
     * the old "admin" / "admincikis" text box let any user become admin.
     */
    private void setupAdminAccess() {
        adminKey.setVisible(false);
        adminKeyLabel.setVisible(false);
        adminButton.setVisible(userIsAdmin);
        adminButton.addActionListener(e -> {
            adminMode = !adminMode;
            applyAdminMode();
            Dialogs.info(mainPanel1, adminMode ? "Admin modu açıldı." : "Admin modu kapatıldı.");
        });
        applyAdminMode();
    }

    private void applyAdminMode() {
        adminButton.setText(adminMode ? "Admin Mode: ON" : "Admin Mode: OFF");
        for (VehicleType t : VehicleType.values()) {
            contractButtonOf(t).setVisible(!adminMode);
            setEditing(t, false);
        }
        addVehicle1Button.setVisible(adminMode);
        adminPanelButton.setVisible(adminMode);
        iadeField.setEditable(!adminMode);
        iadeButton.setEnabled(!adminMode);
    }

    private void openAdminDashboard() {
        try {
            RentalRepository.AdminStats s = RentalRepository.adminStats();
            adminVehicleType.setText(String.valueOf(s.vehicleCount()));
            adminUserNumber.setText(String.valueOf(s.userCount()));
            adminOnRentedVehicle.setText(String.valueOf(s.activeRentals()));
            adminRevenue.setText(s.totalRevenue() + " $");
            adminVehicleStock.setText(String.valueOf(s.totalStock()));
        } catch (SQLException ex) {
            Dialogs.databaseError(mainPanel1, ex);
            return;
        }
        showPage(adminPanel, false);
    }

    // =====================================================================

    /** Called by the GUI Designer for components marked "custom create". */
    private void createUIComponents() {
        phoneLogo = new JLabel(Images.icon("phone.png", 20, 20));
        locationLogo = new JLabel(Images.icon("location.png", 20, 20));
        instaLogo = new JLabel(Images.icon("insta.png", 20, 20));
        mailLogo = new JLabel(Images.icon("mail.png", 20, 20));
        homeLogo = new JLabel(Images.icon("SwiftWheels.png", 400, 220));
        LogoLabel = new JLabel(Images.icon("yatay.png", 300, 100));
        car1Logo = new JLabel(Images.icon("Car-Transparent-Background1.png", 150, 100));
        car2Logo = new JLabel(Images.icon("Car-Transparent-Background2.png", 150, 100));
        motorLogo = new JLabel(Images.icon("Motorcycle-Transparent-Background1.png", 150, 100));
        ppLabel = new JLabel(Images.icon("Pp.png", 200, 200));
    }
}
