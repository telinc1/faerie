/*
 * Copyright (c) 2018 Telinc1
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.telinc1.faerie.gui.main.dialog;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.telinc1.faerie.Resources;
import com.telinc1.faerie.display.Palette;
import com.telinc1.faerie.gui.JPaletteView;
import com.telinc1.faerie.gui.chooser.PaletteChooser;
import com.telinc1.faerie.gui.main.MainWindow;
import com.telinc1.faerie.util.locale.LocalizedException;
import com.telinc1.faerie.util.notification.EnumSeverity;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The dialog which is displayed from the main window to show the loaded
 * palette.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class PaletteDialog extends JDialog {
    /**
     * The {@code MainWindow} to which this {@code PaletteDialog} belongs.
     */
    private final MainWindow window;

    /**
     * The {@code PaletteChooser} used by this dialog.
     */
    private final PaletteChooser paletteChooser;

    private JPanel contentPanel;
    private JPaletteView paletteView;
    private JTextPane colorNAPCTextPane; // what in fuckenings intellij
    private JButton reloadButton;
    private JButton importButton;

    /**
     * Construct a Palette dialog, including all of its inner components.
     * <p>
     * The title and sizes are automatically set. Note that the window is not
     * opened.
     */
    public PaletteDialog(MainWindow window){
        super(window, Resources.getString("palette", "title"), false);
        this.window = window;
        this.paletteChooser = new PaletteChooser(this.getWindow().getApplication());

        this.setType(Type.UTILITY);

        this.$$$setupUI$$$();
        this.configureUIComponents();

        this.setContentPane(this.contentPanel);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        URL iconURL = Resources.class.getResource(Resources.PACKAGE + "/images/application.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
    }

    /**
     * Returns the {@code MainWindow} to which this {@code AboutDialog} belongs.
     */
    public MainWindow getWindow(){
        return this.window;
    }

    /**
     * Configures UI components after they have been created.
     */
    private void configureUIComponents(){
        MouseAdapter paletteAdapter = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent event){
                PaletteDialog.this.colorNAPCTextPane.setText(Resources.getString("palette", "label.initial"));
            }

            @Override
            public void mouseMoved(MouseEvent event){
                JPaletteView paletteView = PaletteDialog.this.paletteView;
                Palette palette = paletteView.getPalette();

                int x = event.getX() / paletteView.getCellSize().width;
                int y = event.getY() / paletteView.getCellSize().height;
                int index = x + y * paletteView.getRegionSize().width;

                Color pc = palette.getColor(index);
                int snes = palette.getSNESColor(index);

                PaletteDialog.this.colorNAPCTextPane.setText(Resources.getString(
                    "palette", "label.color",
                    "color", String.format("%02X", index),
                    "pc", String.format("%06X", pc.getRGB() & 0xFFFFFF),
                    "red", pc.getRed(),
                    "green", pc.getGreen(),
                    "blue", pc.getBlue(),
                    "snes", String.format("%04X", snes),
                    "snes_red", snes & 0x1F,
                    "snes_green", (snes >> 5) & 0x1F,
                    "snes_blue", (snes >> 10) & 0x1F
                ));
            }
        };

        this.paletteView.addMouseListener(paletteAdapter);
        this.paletteView.addMouseMotionListener(paletteAdapter);

        this.reloadButton.addActionListener(event -> {
            int result = JOptionPane.showConfirmDialog(
                this,
                Resources.getString("palette", "dialog.reload.content"),
                Resources.getString("palette", "dialog.reload.title"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if(result == 0){
                this.getWindow().getInterface().loadDefaultPalette();
            }
        });

        this.importButton.addActionListener(event -> {
            int result = this.getPaletteChooser().showOpen(this);

            if(result == JFileChooser.APPROVE_OPTION){
                File file = this.getPaletteChooser().getSelectedFile();

                try {
                    this.getWindow().getInterface().getPalette().loadFile(file);
                }catch(IOException exception){
                    this.getWindow().getApplication().getNotifier().notify(this, new LocalizedException(
                        exception,
                        EnumSeverity.ERROR,
                        "palette", "palette",
                        "message", exception.getMessage()
                    ));
                }
            }

            this.getPaletteChooser().setSelectedFile(null);
        });
    }

    /**
     * Returns the {@code PaletteChooser} used by this dialog.
     */
    public PaletteChooser getPaletteChooser(){
        return this.paletteChooser;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$(){
        createUIComponents();
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), 1, -1, true, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPanel.add(panel1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(30, 31), null, 0, false));
        reloadButton = new JButton();
        reloadButton.setActionCommand(ResourceBundle.getBundle("com/telinc1/faerie/locale/Palette").getString("button.reload"));
        reloadButton.setBorderPainted(true);
        reloadButton.setFocusable(false);
        reloadButton.setIcon(new ImageIcon(getClass().getResource("/com/telinc1/faerie/images/buttons/refresh.png")));
        reloadButton.setIconTextGap(0);
        reloadButton.setMargin(new Insets(0, 0, 0, 0));
        reloadButton.setMaximumSize(new Dimension(30, 30));
        reloadButton.setMinimumSize(new Dimension(30, 30));
        reloadButton.setOpaque(true);
        reloadButton.setPreferredSize(new Dimension(30, 30));
        reloadButton.setToolTipText(ResourceBundle.getBundle("com/telinc1/faerie/locale/Palette").getString("button.reload"));
        panel2.add(reloadButton);
        final JLabel label1 = new JLabel();
        label1.setEnabled(false);
        label1.setFocusable(false);
        label1.setPreferredSize(new Dimension(3, 0));
        label1.setText("");
        panel2.add(label1);
        importButton = new JButton();
        importButton.setActionCommand(ResourceBundle.getBundle("com/telinc1/faerie/locale/Palette").getString("button.import"));
        importButton.setBorderPainted(true);
        importButton.setFocusable(false);
        importButton.setIcon(new ImageIcon(getClass().getResource("/com/telinc1/faerie/images/buttons/palette_import.png")));
        importButton.setIconTextGap(0);
        importButton.setMargin(new Insets(0, 0, 0, 0));
        importButton.setMaximumSize(new Dimension(30, 30));
        importButton.setMinimumSize(new Dimension(30, 30));
        importButton.setOpaque(true);
        importButton.setPreferredSize(new Dimension(30, 30));
        importButton.setToolTipText(ResourceBundle.getBundle("com/telinc1/faerie/locale/Palette").getString("button.import"));
        panel2.add(importButton);
        colorNAPCTextPane = new JTextPane();
        colorNAPCTextPane.setEditable(false);
        colorNAPCTextPane.setFocusable(false);
        colorNAPCTextPane.setMargin(new Insets(3, 3, 0, 3));
        colorNAPCTextPane.setOpaque(false);
        colorNAPCTextPane.setText(ResourceBundle.getBundle("com/telinc1/faerie/locale/Palette").getString("label.initial"));
        panel1.add(colorNAPCTextPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(256, -1), new Dimension(256, -1), new Dimension(256, -1), 0, false));
        paletteView.setAlignmentY(0.0f);
        contentPanel.add(paletteView, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(256, 256), new Dimension(256, 256), new Dimension(256, 256), 0, false));
    }

    private void createUIComponents(){
        this.paletteView = new JPaletteView();
        this.paletteView
            .setPalette(this.getWindow().getInterface().getPalette())
            .setCellSize(16, 16)
            .setFirstIndex(0x0)
            .setRegionSize(16, 16);
    }

    /** @noinspection ALL */
    public JComponent $$$getRootComponent$$$(){ return contentPanel; }
}
