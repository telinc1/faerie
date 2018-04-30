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

package com.telinc1.faerie.gui.main;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.telinc1.faerie.Resources;
import com.telinc1.faerie.display.Palette;
import com.telinc1.faerie.gui.DecimalFormatter;
import com.telinc1.faerie.gui.HexadecimalFormatter;
import com.telinc1.faerie.gui.JPaletteView;
import com.telinc1.faerie.gui.JScaledImage;
import com.telinc1.faerie.gui.main.menu.FaerieMenuBar;
import com.telinc1.faerie.sprite.EnumSpriteSubType;
import com.telinc1.faerie.sprite.EnumSpriteType;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The main Faerie editor window.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class FaerieWindow extends JFrame {
    /**
     * The menu bar which the window displays.
     */
    private FaerieMenuBar menuBar;

    /**
     * The main content panel of the window which houses everything in it.
     */
    private JPanel contentPanel;

    /**
     * The tabbed pane of window which provides the separate views.
     */
    private JTabbedPane tabbedPane;

    /**
     * The main tab which houses the functional sprite information.
     */
    private JPanel spritePanel;

    /**
     * The main tab which houses the display information for the sprite.
     */
    private JPanel displayPanel;

    /**
     * The currently loaded palette.
     */
    private Palette palette;

    private JComboBox spriteSelectionComboBox;

    private JComboBox typeComboBox;
    private JComboBox subtypeComboBox;
    private JFormattedTextField actsLikeTextField;
    private JTextField firstASMTextField;
    private JTextField secondASMTextField;

    private JComboBox objectClippingComboBox;
    private JCheckBox canBeJumpedOnCheckBox;
    private JCheckBox diesWhenJumpedOnCheckBox;
    private JCheckBox hopInKickShellsCheckBox;
    private JCheckBox disappearInACloudCheckBox;

    private JComboBox spriteClippingComboBox;
    private JCheckBox useShellAsDeathCheckBox;
    private JCheckBox fallsWhenKilledCheckBox;

    private JCheckBox useSecondGraphicsPageCheckBox;
    private JComboBox paletteComboBox;
    private JCheckBox disableFireballKillingCheckBox;
    private JCheckBox disableCapeKillingCheckBox;
    private JCheckBox disableWaterSplashCheckBox;
    private JCheckBox disableSecondaryInteractionCheckBox;

    private JCheckBox executeWhileBeingKilledCheckBox;
    private JCheckBox invincibleToPlayerCheckBox;
    private JCheckBox executeIfOffscreenCheckBox;
    private JCheckBox donTChangeIntoCheckBox;
    private JCheckBox canTBeKickedCheckBox;
    private JCheckBox processInteractionEveryFrameCheckBox;
    private JCheckBox givesPowerupWhenEatenCheckBox;
    private JCheckBox donTUseDefaultCheckBox;

    private JCheckBox inedibleCheckBox;
    private JCheckBox stayInYoshiSCheckBox;
    private JCheckBox weirdGroundBehaviorCheckBox;
    private JCheckBox donTInteractWithCheckBox;
    private JCheckBox donTChangeDirectionCheckBox;
    private JCheckBox donTTurnIntoCheckBox;
    private JCheckBox spawnsANewSpriteCheckBox;
    private JCheckBox donTInteractWithCheckBox1;

    private JCheckBox makePlatformPassableFromCheckBox;
    private JCheckBox donTEraseWhenCheckBox;
    private JCheckBox canTBeKilledCheckBox;
    private JCheckBox takes5FireballsToCheckBox;
    private JCheckBox canBeJumpedOnCheckBox1;
    private JCheckBox deathFrame2TilesCheckBox;
    private JCheckBox ignoreSilverPSwitchCheckBox;
    private JCheckBox donTGetStuckCheckBox;

    private JFormattedTextField firstPropertyTextField;
    private JFormattedTextField secondPropertyTextField;
    private JComboBox statusOverrideComboBox;
    private JFormattedTextField uniqueByteTextField;
    private JFormattedTextField extraByteAmountTextField;
    private JScaledImage objectClippingImage;
    private JScaledImage spriteClippingImage;
    private JPaletteView paletteView;

    /**
     * Construct an application window, including all of its inner components.
     * <p>
     * The title and sizes are automatically set.
     * <p>
     * Note that the window is not opened.
     */
    public FaerieWindow(){
        super(Resources.getString("main", "title"));

        this.$$$setupUI$$$();
        this.configureUIComponents();

        this.disableAllInput();
        this.setContentPane(this.contentPanel);

        this.setSize(760, 600);
        this.setMinimumSize(new Dimension(760, 600));

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        URL iconURL = this.getClass().getResource("/com/telinc1/faerie/icons/application.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());

        this.menuBar = new FaerieMenuBar(this);

        this.palette = new Palette();

        try {
            URL palette = this.getClass().getResource("/com/telinc1/faerie/binary/generic.mw3");
            this.getPalette().loadMW3File(new File(palette.getPath()));
            this.paletteView.setPalette(this.getPalette());
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Configures UI components after they have been created.
     */
    private void configureUIComponents(){
        HexadecimalFormatter.apply(this.actsLikeTextField, 0, 255);
        HexadecimalFormatter.apply(this.firstPropertyTextField, 0, 255);
        HexadecimalFormatter.apply(this.secondPropertyTextField, 0, 63);
        HexadecimalFormatter.apply(this.uniqueByteTextField, 0, 255);
        DecimalFormatter.apply(this.extraByteAmountTextField, 0, 255);
    }

    /**
     * Disables all user input elements.
     * <p>
     * This includes text fields, checkboxes, and combo boxes.
     */
    private void disableAllInput(){
        Field[] fields = this.getClass().getDeclaredFields();

        for(Field field : fields){
            Class<?> type = field.getType();

            if(type == JComboBox.class || type == JTextField.class || type == JFormattedTextField.class || type == JCheckBox.class){
                try {
                    JComponent component = (JComponent)field.get(this);
                    component.setEnabled(false);
                }catch(IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Returns the color palette used by the window.
     *
     * @return the SNES {@link Palette} used by the window
     */
    public Palette getPalette(){
        return this.palette;
    }

    /**
     * Returns the menu bar for the application window.
     *
     * @return an instance of {@link FaerieMenuBar} which represents the
     * menu bar
     */
    public FaerieMenuBar getMenu(){
        return this.menuBar;
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
        contentPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, 0));
        panel1.setEnabled(true);
        panel1.setFocusable(true);
        panel1.setOpaque(true);
        panel1.setVisible(true);
        contentPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, 1, 1, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("content.spriteSelection")));
        panel1.add(spriteSelectionComboBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabbedPane = new JTabbedPane();
        contentPanel.add(tabbedPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setEnabled(true);
        tabbedPane.addTab(ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("content.tab.sprite"), scrollPane1);
        spritePanel = new JPanel();
        spritePanel.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane1.setViewportView(spritePanel);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, 0));
        panel2.setEnabled(true);
        spritePanel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$1656"));
        canBeJumpedOnCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(canBeJumpedOnCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.canBeJumpedOn"));
        panel2.add(canBeJumpedOnCheckBox, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        diesWhenJumpedOnCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(diesWhenJumpedOnCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.diesWhenJumpedOn"));
        panel2.add(diesWhenJumpedOnCheckBox, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        hopInKickShellsCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(hopInKickShellsCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.hopInShells"));
        panel2.add(hopInKickShellsCheckBox, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        disappearInACloudCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(disappearInACloudCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disappearInSmoke"));
        panel2.add(disappearInACloudCheckBox, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        this.$$$loadLabelText$$$(label1, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.objectClipping"));
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        objectClippingComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("00");
        defaultComboBoxModel1.addElement("01");
        defaultComboBoxModel1.addElement("02");
        defaultComboBoxModel1.addElement("03");
        defaultComboBoxModel1.addElement("04");
        defaultComboBoxModel1.addElement("05");
        defaultComboBoxModel1.addElement("06");
        defaultComboBoxModel1.addElement("07");
        defaultComboBoxModel1.addElement("08");
        defaultComboBoxModel1.addElement("09");
        defaultComboBoxModel1.addElement("0A");
        defaultComboBoxModel1.addElement("0B");
        defaultComboBoxModel1.addElement("0C");
        defaultComboBoxModel1.addElement("0D");
        defaultComboBoxModel1.addElement("0E");
        defaultComboBoxModel1.addElement("0F");
        objectClippingComboBox.setModel(defaultComboBoxModel1);
        panel2.add(objectClippingComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.visualizedObjectClipping")));
        objectClippingImage.setFocusable(false);
        panel3.add(objectClippingImage, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(84, 84), null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$1662"));
        useShellAsDeathCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(useShellAsDeathCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.shellAsDeathFrame"));
        panel4.add(useShellAsDeathCheckBox, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        fallsWhenKilledCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(fallsWhenKilledCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.fallsWhenKilled"));
        panel4.add(fallsWhenKilledCheckBox, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        this.$$$loadLabelText$$$(label2, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.spriteClipping"));
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spriteClippingComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("00");
        defaultComboBoxModel2.addElement("01");
        defaultComboBoxModel2.addElement("02");
        defaultComboBoxModel2.addElement("03");
        defaultComboBoxModel2.addElement("04");
        defaultComboBoxModel2.addElement("05");
        defaultComboBoxModel2.addElement("06");
        defaultComboBoxModel2.addElement("07");
        defaultComboBoxModel2.addElement("08");
        defaultComboBoxModel2.addElement("09");
        defaultComboBoxModel2.addElement("0A");
        defaultComboBoxModel2.addElement("0B");
        defaultComboBoxModel2.addElement("0C");
        defaultComboBoxModel2.addElement("0D");
        defaultComboBoxModel2.addElement("0E");
        defaultComboBoxModel2.addElement("0F");
        defaultComboBoxModel2.addElement("10");
        defaultComboBoxModel2.addElement("11");
        defaultComboBoxModel2.addElement("12");
        defaultComboBoxModel2.addElement("13");
        defaultComboBoxModel2.addElement("14");
        defaultComboBoxModel2.addElement("15");
        defaultComboBoxModel2.addElement("16");
        defaultComboBoxModel2.addElement("17");
        defaultComboBoxModel2.addElement("18");
        defaultComboBoxModel2.addElement("19");
        defaultComboBoxModel2.addElement("1A");
        defaultComboBoxModel2.addElement("1B");
        defaultComboBoxModel2.addElement("1C");
        defaultComboBoxModel2.addElement("1D");
        defaultComboBoxModel2.addElement("1E");
        defaultComboBoxModel2.addElement("1F");
        defaultComboBoxModel2.addElement("20");
        defaultComboBoxModel2.addElement("21");
        defaultComboBoxModel2.addElement("22");
        defaultComboBoxModel2.addElement("23");
        defaultComboBoxModel2.addElement("24");
        defaultComboBoxModel2.addElement("25");
        defaultComboBoxModel2.addElement("26");
        defaultComboBoxModel2.addElement("27");
        defaultComboBoxModel2.addElement("28");
        defaultComboBoxModel2.addElement("29");
        defaultComboBoxModel2.addElement("2A");
        defaultComboBoxModel2.addElement("2B");
        defaultComboBoxModel2.addElement("2C");
        defaultComboBoxModel2.addElement("2D");
        defaultComboBoxModel2.addElement("2E");
        defaultComboBoxModel2.addElement("2F");
        defaultComboBoxModel2.addElement("30");
        defaultComboBoxModel2.addElement("31");
        defaultComboBoxModel2.addElement("32");
        defaultComboBoxModel2.addElement("33");
        defaultComboBoxModel2.addElement("34");
        defaultComboBoxModel2.addElement("35");
        defaultComboBoxModel2.addElement("36");
        defaultComboBoxModel2.addElement("37");
        defaultComboBoxModel2.addElement("38");
        defaultComboBoxModel2.addElement("39");
        defaultComboBoxModel2.addElement("3A");
        defaultComboBoxModel2.addElement("3B");
        defaultComboBoxModel2.addElement("3C");
        defaultComboBoxModel2.addElement("3D");
        defaultComboBoxModel2.addElement("3E");
        defaultComboBoxModel2.addElement("3F");
        spriteClippingComboBox.setModel(defaultComboBoxModel2);
        panel4.add(spriteClippingComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel5, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.visualizedSpriteClipping")));
        spriteClippingImage.setFocusable(false);
        panel5.add(spriteClippingImage, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(84, 84), null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel6, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$166E"));
        useSecondGraphicsPageCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(useSecondGraphicsPageCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.useSecondGraphicsPage"));
        panel6.add(useSecondGraphicsPageCheckBox, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        this.$$$loadLabelText$$$(label3, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.palette"));
        panel6.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        paletteComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("8");
        defaultComboBoxModel3.addElement("9");
        defaultComboBoxModel3.addElement("A");
        defaultComboBoxModel3.addElement("B");
        defaultComboBoxModel3.addElement("C");
        defaultComboBoxModel3.addElement("D");
        defaultComboBoxModel3.addElement("E");
        defaultComboBoxModel3.addElement("F");
        paletteComboBox.setModel(defaultComboBoxModel3);
        panel6.add(paletteComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 2, false));
        disableFireballKillingCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(disableFireballKillingCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableFireballKilling"));
        panel6.add(disableFireballKillingCheckBox, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableCapeKillingCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(disableCapeKillingCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableCapeKilling"));
        panel6.add(disableCapeKillingCheckBox, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableWaterSplashCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(disableWaterSplashCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableWaterSplash"));
        panel6.add(disableWaterSplashCheckBox, new GridConstraints(4, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableSecondaryInteractionCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(disableSecondaryInteractionCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableSecondaryInteraction"));
        panel6.add(disableSecondaryInteractionCheckBox, new GridConstraints(5, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        paletteView.setFocusable(false);
        panel6.add(paletteView, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(128, 16), null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel7, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$167A"));
        executeWhileBeingKilledCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(executeWhileBeingKilledCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.processIfDead"));
        panel7.add(executeWhileBeingKilledCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        invincibleToPlayerCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(invincibleToPlayerCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.invincibleToPlayer"));
        panel7.add(invincibleToPlayerCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        executeIfOffscreenCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(executeIfOffscreenCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.executeIfOffscreen"));
        panel7.add(executeIfOffscreenCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTChangeIntoCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTChangeIntoCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.skipShellWhenStunned"));
        panel7.add(donTChangeIntoCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        canTBeKickedCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(canTBeKickedCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableKicking"));
        panel7.add(canTBeKickedCheckBox, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        processInteractionEveryFrameCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(processInteractionEveryFrameCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.processInteractionEveryFrame"));
        panel7.add(processInteractionEveryFrameCheckBox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        givesPowerupWhenEatenCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(givesPowerupWhenEatenCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.isPowerup"));
        panel7.add(givesPowerupWhenEatenCheckBox, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTUseDefaultCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTUseDefaultCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableDefaultInteraction"));
        panel7.add(donTUseDefaultCheckBox, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel8, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel8.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$1686"));
        inedibleCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(inedibleCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.inedible"));
        panel8.add(inedibleCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stayInYoshiSCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(stayInYoshiSCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.stayInMouth"));
        panel8.add(stayInYoshiSCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        weirdGroundBehaviorCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(weirdGroundBehaviorCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.weirdGroundBehavior"));
        panel8.add(weirdGroundBehaviorCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTInteractWithCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTInteractWithCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableSpriteInteraction"));
        panel8.add(donTInteractWithCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTChangeDirectionCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTChangeDirectionCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.preserveDirection"));
        panel8.add(donTChangeDirectionCheckBox, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTTurnIntoCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTTurnIntoCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disappearWhenGoalPassed"));
        panel8.add(donTTurnIntoCheckBox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spawnsANewSpriteCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(spawnsANewSpriteCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.spawnsNewSprite"));
        panel8.add(spawnsANewSpriteCheckBox, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTInteractWithCheckBox1 = new JCheckBox();
        this.$$$loadButtonText$$$(donTInteractWithCheckBox1, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableObjectInteraction"));
        panel8.add(donTInteractWithCheckBox1, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel9, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel9.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$190F"));
        makePlatformPassableFromCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(makePlatformPassableFromCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.platformPassableFromBelow"));
        panel9.add(makePlatformPassableFromCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTEraseWhenCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTEraseWhenCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.ignoreGoal"));
        panel9.add(donTEraseWhenCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        canTBeKilledCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(canTBeKilledCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableSlideKilling"));
        panel9.add(canTBeKilledCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        takes5FireballsToCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(takes5FireballsToCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.takesFiveFireballs"));
        panel9.add(takes5FireballsToCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        canBeJumpedOnCheckBox1 = new JCheckBox();
        this.$$$loadButtonText$$$(canBeJumpedOnCheckBox1, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.canBeJumpedOnFromBelow"));
        panel9.add(canBeJumpedOnCheckBox1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deathFrame2TilesCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(deathFrame2TilesCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.tallDeathFrame"));
        panel9.add(deathFrame2TilesCheckBox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ignoreSilverPSwitchCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(ignoreSilverPSwitchCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.ignoreSilverPSwitch"));
        panel9.add(ignoreSilverPSwitchCheckBox, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTGetStuckCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTGetStuckCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.ignoreWalls"));
        panel9.add(donTGetStuckCheckBox, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        spritePanel.add(panel10, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, 1, 1, null, null, null, 0, false));
        panel10.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.insertion.title")));
        panel10.add(typeComboBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel10.add(subtypeComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        this.$$$loadLabelText$$$(label4, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.insertion.asm"));
        panel10.add(label4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        firstASMTextField = new JTextField();
        firstASMTextField.setText("");
        panel10.add(firstASMTextField, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        secondASMTextField = new JTextField();
        panel10.add(secondASMTextField, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        this.$$$loadLabelText$$$(label5, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.insertion.actsLike"));
        panel10.add(label5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        actsLikeTextField = new JFormattedTextField();
        panel10.add(actsLikeTextField, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        spritePanel.add(panel11, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel11.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.data.title")));
        final JLabel label6 = new JLabel();
        this.$$$loadLabelText$$$(label6, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.data.propertyBytes"));
        panel11.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        firstPropertyTextField = new JFormattedTextField();
        panel11.add(firstPropertyTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        secondPropertyTextField = new JFormattedTextField();
        panel11.add(secondPropertyTextField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        this.$$$loadLabelText$$$(label7, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.data.statusOverride"));
        panel11.add(label7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        statusOverrideComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("Handle stunned");
        defaultComboBoxModel4.addElement("Handle all");
        defaultComboBoxModel4.addElement("Override all");
        statusOverrideComboBox.setModel(defaultComboBoxModel4);
        panel11.add(statusOverrideComboBox, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        this.$$$loadLabelText$$$(label8, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.data.uniqueByte"));
        panel11.add(label8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        uniqueByteTextField = new JFormattedTextField();
        panel11.add(uniqueByteTextField, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        this.$$$loadLabelText$$$(label9, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.data.extraBytes"));
        panel11.add(label9, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraByteAmountTextField = new JFormattedTextField();
        panel11.add(extraByteAmountTextField, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab(ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("content.tab.display"), displayPanel);
        label1.setLabelFor(objectClippingComboBox);
        label2.setLabelFor(spriteClippingComboBox);
        label3.setLabelFor(paletteComboBox);
        label4.setLabelFor(firstASMTextField);
        label5.setLabelFor(actsLikeTextField);
        label6.setLabelFor(firstPropertyTextField);
        label7.setLabelFor(statusOverrideComboBox);
        label8.setLabelFor(uniqueByteTextField);
        label9.setLabelFor(extraByteAmountTextField);
    }

    /**
     * Creates the UI components which need specific constructor parameters.
     */
    private void createUIComponents(){
        // Create sprite selection combobox.
        // XXX: temporary placeholder, will be replaced by the yet unwritten Repository system later
        try {
            URL listURL = this.getClass().getResource("/com/telinc1/faerie/sprites/list.txt");
            File listFile = new File(listURL.getFile());
            List<String> sprites = Files.readAllLines(listFile.toPath(), StandardCharsets.UTF_8);

            this.spriteSelectionComboBox = new JComboBox<>(sprites.toArray());
        }catch(IOException exception){
            throw new RuntimeException(exception);
        }

        // Create type combobox.
        // XXX: should these be localized
        this.typeComboBox = new JComboBox<>(Arrays
            .stream(EnumSpriteType.values())
            .map(EnumSpriteType::readable)
            .toArray()
        );

        // Create subtype combobox.
        // TODO: yeah, better localize them
        this.subtypeComboBox = new JComboBox<>(Arrays
            .stream(EnumSpriteSubType.values())
            .map(EnumSpriteSubType::readable)
            .toArray()
        );

        try {
            // Create object clipping image.
            this.objectClippingImage = new JScaledImage();
            this.objectClippingImage.loadImage("object/0A.png");

            // Create sprite clipping image.
            this.spriteClippingImage = new JScaledImage();
            this.spriteClippingImage.loadImage("sprite/00.png");

            // Create palette image.
            this.paletteView = new JPaletteView();
            this.paletteView
                .setCellSize(16, 16)
                .setFirstIndex(0x80)
                .setRegionSize(8, 1);
            this.paletteView.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }catch(IOException exception){
            throw new RuntimeException(exception);
        }
    }

    /** @noinspection ALL */
    private void $$$loadLabelText$$$(JLabel component, String text){
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == '&'){
                i++;
                if(i == text.length()) break;
                if(!haveMnemonic && text.charAt(i) != '&'){
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if(haveMnemonic){
            component.setDisplayedMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /** @noinspection ALL */
    private void $$$loadButtonText$$$(AbstractButton component, String text){
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == '&'){
                i++;
                if(i == text.length()) break;
                if(!haveMnemonic && text.charAt(i) != '&'){
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if(haveMnemonic){
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /** @noinspection ALL */
    public JComponent $$$getRootComponent$$$(){
        return contentPanel;
    }
}
