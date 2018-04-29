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
import com.telinc1.faerie.gui.JScaledImage;
import com.telinc1.faerie.gui.main.menu.FaerieMenuBar;
import com.telinc1.faerie.sprite.EnumSpriteSubType;
import com.telinc1.faerie.sprite.EnumSpriteType;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
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

    private JComboBox spriteSelectionComboBox;

    private JComboBox typeComboBox;
    private JComboBox subtypeComboBox;
    private JTextField actsLikeTextField;
    private JTextField firstASMTextField;
    private JTextField secondASMTextField;

    private JComboBox objectClippingComboBox;
    private JCheckBox canBeJumpedOnCheckBox;
    private JCheckBox diesWhenJumpedOnCheckBox;
    private JCheckBox hopInShellsCheckBox;
    private JCheckBox disappearInSmokeCheckBox;

    private JComboBox spriteClippingComboBox;
    private JCheckBox shellsAsDeathFrameCheckBox;
    private JCheckBox fallWhenKilledCheckBox;

    private JCheckBox useSecondGraphicsPageCheckBox;
    private JComboBox paletteComboBox;
    private JCheckBox disableFireballKillingCheckBox;
    private JCheckBox disableCapeKillingCheckBox;
    private JCheckBox disableWaterSplashCheckBox;
    private JCheckBox disableSecondaryInteractionCheckBox;

    private JCheckBox processIfDeadCheckBox;
    private JCheckBox invincibleToPlayerCheckBox;
    private JCheckBox processWhileOffscreenCheckBox;
    private JCheckBox skipShellIfStunnedCheckBox;
    private JCheckBox disableKickingCheckBox;
    private JCheckBox processPlayerInteractionEveryFrameCheckBox;
    private JCheckBox isPowerupCheckbox;
    private JCheckBox disableDefaultInteractionCheckBox;

    private JCheckBox inedibleCheckBox;
    private JCheckBox stayInMouthCheckBox;
    private JCheckBox weirdGroundBehaviorCheckBox;
    private JCheckBox disableSpriteInteractionCheckBox;
    private JCheckBox preserveDirectionCheckBox;
    private JCheckBox disappearWhenGoalPassedCheckBox;
    private JCheckBox spawnsANewSpriteCheckBox;
    private JCheckBox disableObjectInteractionCheckBox;

    private JCheckBox platformPassableFromBelowCheckBox;
    private JCheckBox ignoreGoalCheckBox;
    private JCheckBox disableSlideKillingCheckBox;
    private JCheckBox needsFiveFireballsCheckBox;
    private JCheckBox canBeJumpedOnFromBelowCheckBox;
    private JCheckBox tallDeathFrameCheckbox;
    private JCheckBox immuneToSilverPOWCheckBox;
    private JCheckBox escapeWallsCheckBox;

    private JTextField firstPropertyTextField;
    private JTextField secondPropertyTextField;
    private JComboBox statusOverrideComboBox;
    private JTextField uniqueByteTextField;
    private JTextField extraByteAmountTextField;
    private JScaledImage objectClippingImage;
    private JScaledImage spriteClippingImage;

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
     * Construct an application window, including all of its inner components.
     * <p>
     * The title and sizes are automatically set.
     * <p>
     * Note that the window is not opened.
     */
    public FaerieWindow(){
        super(Resources.getString("main", "title"));
        this.$$$setupUI$$$();
        this.setContentPane(this.contentPanel);
        this.disableAllInput();

        this.setSize(760, 600);
        this.setMinimumSize(new Dimension(760, 600));

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        URL iconURL = this.getClass().getResource("/com/telinc1/faerie/icons/application.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());

        this.menuBar = new FaerieMenuBar(this);
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

            if(type == JComboBox.class || type == JTextField.class || type == JCheckBox.class){
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
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Sprite Selection"));
        panel1.add(spriteSelectionComboBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabbedPane = new JTabbedPane();
        contentPanel.add(tabbedPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setEnabled(true);
        tabbedPane.addTab(ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("content.sprite"), scrollPane1);
        spritePanel = new JPanel();
        spritePanel.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane1.setViewportView(spritePanel);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, 0));
        panel2.setEnabled(true);
        spritePanel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$1656"));
        canBeJumpedOnCheckBox = new JCheckBox();
        canBeJumpedOnCheckBox.setText("Can be jumped on");
        panel2.add(canBeJumpedOnCheckBox, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        diesWhenJumpedOnCheckBox = new JCheckBox();
        diesWhenJumpedOnCheckBox.setText("Dies when jumped on");
        panel2.add(diesWhenJumpedOnCheckBox, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        hopInShellsCheckBox = new JCheckBox();
        hopInShellsCheckBox.setText("Hop in/Kick shells");
        panel2.add(hopInShellsCheckBox, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        disappearInSmokeCheckBox = new JCheckBox();
        disappearInSmokeCheckBox.setText("Disappear in a cloud of smoke");
        panel2.add(disappearInSmokeCheckBox, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Object Clipping:");
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
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Visualized Clipping"));
        panel3.add(objectClippingImage, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(84, 84), null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$1662"));
        shellsAsDeathFrameCheckBox = new JCheckBox();
        shellsAsDeathFrameCheckBox.setText("Use shell as death frame");
        panel4.add(shellsAsDeathFrameCheckBox, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        fallWhenKilledCheckBox = new JCheckBox();
        fallWhenKilledCheckBox.setText("Falls straight down when killed");
        panel4.add(fallWhenKilledCheckBox, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Sprite Clipping:");
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
        panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Visualized Clipping"));
        panel5.add(spriteClippingImage, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(84, 84), null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel6, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$166E"));
        useSecondGraphicsPageCheckBox = new JCheckBox();
        useSecondGraphicsPageCheckBox.setText("Use second graphics page");
        panel6.add(useSecondGraphicsPageCheckBox, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Palette:");
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
        final JLabel label4 = new JLabel();
        label4.setIcon(new ImageIcon(getClass().getResource("/com/telinc1/faerie/icons/palette.png")));
        label4.setIconTextGap(0);
        label4.setText("");
        panel6.add(label4, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableFireballKillingCheckBox = new JCheckBox();
        disableFireballKillingCheckBox.setText("Disable fireball killing");
        panel6.add(disableFireballKillingCheckBox, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableCapeKillingCheckBox = new JCheckBox();
        disableCapeKillingCheckBox.setText("Disable cape killing");
        panel6.add(disableCapeKillingCheckBox, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableWaterSplashCheckBox = new JCheckBox();
        disableWaterSplashCheckBox.setText("Disable water splash");
        panel6.add(disableWaterSplashCheckBox, new GridConstraints(4, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableSecondaryInteractionCheckBox = new JCheckBox();
        disableSecondaryInteractionCheckBox.setText("Don't interact with secondary layers");
        panel6.add(disableSecondaryInteractionCheckBox, new GridConstraints(5, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel7, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$167A"));
        processIfDeadCheckBox = new JCheckBox();
        processIfDeadCheckBox.setText("Execute while being killed");
        panel7.add(processIfDeadCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        invincibleToPlayerCheckBox = new JCheckBox();
        invincibleToPlayerCheckBox.setText("Invincible to player");
        panel7.add(invincibleToPlayerCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        processWhileOffscreenCheckBox = new JCheckBox();
        processWhileOffscreenCheckBox.setText("Execute if offscreen");
        panel7.add(processWhileOffscreenCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        skipShellIfStunnedCheckBox = new JCheckBox();
        skipShellIfStunnedCheckBox.setText("Don't change into a shell when stunned");
        panel7.add(skipShellIfStunnedCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableKickingCheckBox = new JCheckBox();
        disableKickingCheckBox.setText("Can't be kicked like a shell");
        panel7.add(disableKickingCheckBox, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        processPlayerInteractionEveryFrameCheckBox = new JCheckBox();
        processPlayerInteractionEveryFrameCheckBox.setText("Process player interaction every frame");
        panel7.add(processPlayerInteractionEveryFrameCheckBox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        isPowerupCheckbox = new JCheckBox();
        isPowerupCheckbox.setText("Gives powerup when eaten by Yoshi (dangerous)");
        panel7.add(isPowerupCheckbox, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableDefaultInteractionCheckBox = new JCheckBox();
        disableDefaultInteractionCheckBox.setText("Don't use default player interaction");
        panel7.add(disableDefaultInteractionCheckBox, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel8, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel8.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$1686"));
        inedibleCheckBox = new JCheckBox();
        inedibleCheckBox.setText("Inedible");
        panel8.add(inedibleCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stayInMouthCheckBox = new JCheckBox();
        stayInMouthCheckBox.setText("Stay in Yoshi's mouth");
        panel8.add(stayInMouthCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        weirdGroundBehaviorCheckBox = new JCheckBox();
        weirdGroundBehaviorCheckBox.setText("Weird ground behavior");
        panel8.add(weirdGroundBehaviorCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableSpriteInteractionCheckBox = new JCheckBox();
        disableSpriteInteractionCheckBox.setText("Don't interact with other sprites");
        panel8.add(disableSpriteInteractionCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        preserveDirectionCheckBox = new JCheckBox();
        preserveDirectionCheckBox.setText("Don't change direction when touched");
        panel8.add(preserveDirectionCheckBox, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disappearWhenGoalPassedCheckBox = new JCheckBox();
        disappearWhenGoalPassedCheckBox.setText("Don't turn into a coin when goal passed");
        panel8.add(disappearWhenGoalPassedCheckBox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spawnsANewSpriteCheckBox = new JCheckBox();
        spawnsANewSpriteCheckBox.setText("Spawns a new sprite when stunned");
        panel8.add(spawnsANewSpriteCheckBox, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableObjectInteractionCheckBox = new JCheckBox();
        disableObjectInteractionCheckBox.setText("Don't interact with objects");
        panel8.add(disableObjectInteractionCheckBox, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel9, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel9.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$190F"));
        platformPassableFromBelowCheckBox = new JCheckBox();
        platformPassableFromBelowCheckBox.setText("Make platform passable from below");
        panel9.add(platformPassableFromBelowCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ignoreGoalCheckBox = new JCheckBox();
        ignoreGoalCheckBox.setText("Don't erase when goal passed");
        panel9.add(ignoreGoalCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableSlideKillingCheckBox = new JCheckBox();
        disableSlideKillingCheckBox.setText("Can't be killed by sliding");
        panel9.add(disableSlideKillingCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        needsFiveFireballsCheckBox = new JCheckBox();
        needsFiveFireballsCheckBox.setText("Takes 5 fireballs to kill");
        panel9.add(needsFiveFireballsCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        canBeJumpedOnFromBelowCheckBox = new JCheckBox();
        canBeJumpedOnFromBelowCheckBox.setText("Can be jumped on with upward Y speed");
        panel9.add(canBeJumpedOnFromBelowCheckBox, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tallDeathFrameCheckbox = new JCheckBox();
        tallDeathFrameCheckbox.setText("Death frame 2 tiles high");
        panel9.add(tallDeathFrameCheckbox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        immuneToSilverPOWCheckBox = new JCheckBox();
        immuneToSilverPOWCheckBox.setText("Don't turn into a coin with silver P-Switch");
        panel9.add(immuneToSilverPOWCheckBox, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        escapeWallsCheckBox = new JCheckBox();
        escapeWallsCheckBox.setText("Don't get stuck in walls (carryable sprites)");
        panel9.add(escapeWallsCheckBox, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        spritePanel.add(panel10, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, 1, 1, null, null, null, 0, false));
        panel10.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Insertion Settings"));
        panel10.add(typeComboBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel10.add(subtypeComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("ASM Files:");
        panel10.add(label5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        firstASMTextField = new JTextField();
        firstASMTextField.setText("");
        panel10.add(firstASMTextField, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        secondASMTextField = new JTextField();
        panel10.add(secondASMTextField, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Acts Like:");
        panel10.add(label6, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        actsLikeTextField = new JTextField();
        panel10.add(actsLikeTextField, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        spritePanel.add(panel11, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel11.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Additional Data"));
        final JLabel label7 = new JLabel();
        label7.setText("Property Bytes:");
        panel11.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        firstPropertyTextField = new JTextField();
        panel11.add(firstPropertyTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        secondPropertyTextField = new JTextField();
        panel11.add(secondPropertyTextField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Status Override:");
        panel11.add(label8, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        statusOverrideComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("Handle stunned");
        defaultComboBoxModel4.addElement("Handle all");
        defaultComboBoxModel4.addElement("Override all");
        statusOverrideComboBox.setModel(defaultComboBoxModel4);
        panel11.add(statusOverrideComboBox, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Unique Byte:");
        panel11.add(label9, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        uniqueByteTextField = new JTextField();
        panel11.add(uniqueByteTextField, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Extra Byte Amount:");
        panel11.add(label10, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraByteAmountTextField = new JTextField();
        panel11.add(extraByteAmountTextField, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab(ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("content.display"), displayPanel);
        label1.setLabelFor(objectClippingComboBox);
        label2.setLabelFor(spriteClippingComboBox);
        label3.setLabelFor(paletteComboBox);
        label5.setLabelFor(firstASMTextField);
        label6.setLabelFor(actsLikeTextField);
        label7.setLabelFor(firstPropertyTextField);
        label8.setLabelFor(statusOverrideComboBox);
        label9.setLabelFor(uniqueByteTextField);
        label10.setLabelFor(extraByteAmountTextField);
    }

    private void createUIComponents(){
        // Create sprite selection combobox.
        try {
            URL listURL = this.getClass().getResource("/com/telinc1/faerie/sprites/list.txt");
            File listFile = new File(listURL.getFile());
            List<String> sprites = Files.readAllLines(listFile.toPath(), StandardCharsets.UTF_8);

            this.spriteSelectionComboBox = new JComboBox<>(sprites.toArray());
        }catch(IOException exception){
            throw new RuntimeException(exception);
        }

        // Create type combobox.
        this.typeComboBox = new JComboBox<>(Arrays
            .stream(EnumSpriteType.values())
            .map(EnumSpriteType::readable)
            .toArray()
        );

        // Create subtype combobox.
        this.subtypeComboBox = new JComboBox<>(Arrays
            .stream(EnumSpriteSubType.values())
            .map(EnumSpriteSubType::readable)
            .toArray()
        );

        // Create object clipping image.
        try {
            this.objectClippingImage = new JScaledImage(84, 84);
            this.objectClippingImage.setHorizontalAlignment(SwingConstants.CENTER);
            this.objectClippingImage.setVerticalAlignment(SwingConstants.TOP);
            this.objectClippingImage.setFocusable(false);

            this.objectClippingImage.loadImage("object/00.png");
        }catch(IOException exception){
            throw new RuntimeException(exception);
        }

        // Create sprite clipping image.
        try {
            this.spriteClippingImage = new JScaledImage(84, 84);
            this.spriteClippingImage.setHorizontalAlignment(SwingConstants.CENTER);
            this.spriteClippingImage.setVerticalAlignment(SwingConstants.TOP);
            this.spriteClippingImage.setFocusable(false);

            this.spriteClippingImage.loadImage("sprite/00.png");
        }catch(IOException exception){
            throw new RuntimeException(exception);
        }
    }

    /** @noinspection ALL */
    public JComponent $$$getRootComponent$$$(){
        return contentPanel;
    }
}
