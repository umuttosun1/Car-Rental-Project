package com.swiftwheels.ui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Insets;
import java.util.Map;

/** Look & feel and the colour palette shared by code-built components. */
public final class Theme {

    public static final Color ACCENT = new Color(15, 124, 131);        // brand teal
    public static final Color ACCENT_DARK = new Color(10, 92, 98);
    public static final Color PAGE = new Color(244, 248, 249);
    public static final Color CARD = Color.WHITE;
    public static final Color CARD_BORDER = new Color(221, 230, 232);
    public static final Color TEXT = new Color(28, 39, 43);
    public static final Color TEXT_MUTED = new Color(98, 113, 118);
    public static final Color SUCCESS = new Color(30, 132, 73);
    public static final Color DANGER = new Color(198, 40, 40);

    private Theme() {
    }

    /** Must run before the first Swing component is created. */
    public static void install() {
        FlatLaf.setGlobalExtraDefaults(Map.of("@accentColor", "#0F7C83"));
        FlatLightLaf.setup();

        UIManager.put("Button.arc", 10);
        UIManager.put("Component.arc", 8);
        UIManager.put("TextComponent.arc", 8);
        UIManager.put("CheckBox.arc", 4);
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Button.margin", new Insets(6, 14, 6, 14));

        UIManager.put("ScrollBar.width", 10);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("ScrollBar.showButtons", false);
        UIManager.put("ScrollPane.smoothScrolling", true);

        UIManager.put("Table.rowHeight", 30);
        UIManager.put("Table.showHorizontalLines", true);
        UIManager.put("Table.showVerticalLines", false);
        UIManager.put("Table.intercellSpacing", new java.awt.Dimension(0, 1));
        UIManager.put("Table.alternateRowColor", new Color(247, 250, 250));
        UIManager.put("Table.selectionBackground", new Color(207, 232, 234));
        UIManager.put("Table.selectionForeground", TEXT);
        UIManager.put("TableHeader.height", 34);
        UIManager.put("TableHeader.background", new Color(226, 236, 237));
        UIManager.put("TableHeader.separatorColor", CARD_BORDER);

        UIManager.put("OptionPane.buttonMinimumWidth", 90);
        UIManager.put("TitlePane.unifiedBackground", true);
    }
}
