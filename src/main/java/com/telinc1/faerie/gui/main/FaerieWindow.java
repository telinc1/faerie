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
import com.telinc1.faerie.gui.main.menu.FaerieMenuBar;

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
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Insets;
import java.net.URL;
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
    private JCheckBox canBeJumpedOnCheckBox;
    private JCheckBox diesWhenJumpedOnCheckBox;
    private JCheckBox hopInKickShellsCheckBox;
    private JCheckBox disappearInACloudCheckBox;
    private JCheckBox useShellAsDeathCheckBox;
    private JCheckBox fallsStraightDownWhenCheckBox;
    private JCheckBox useSecondGraphicsPageCheckBox;
    private JComboBox paletteComboBox;
    private JCheckBox disableFireballKillingCheckBox;
    private JCheckBox disableCapeKillingCheckBox;
    private JCheckBox disableWaterSplashCheckBox;
    private JCheckBox donTInteractWithCheckBox;
    private JCheckBox executeWhileBeingKilledCheckBox;
    private JCheckBox invincibleToPlayerCheckBox;
    private JCheckBox executeIfOffscreenCheckBox;
    private JCheckBox donTChangeIntoCheckBox;
    private JCheckBox canTBeKickedCheckBox;
    private JCheckBox processPlayerInteractionEveryCheckBox;
    private JCheckBox givesPowerupWhenEatenCheckBox;
    private JCheckBox donTUseDefaultCheckBox;
    private JComboBox objectClippingComboBox;
    private JComboBox spriteClippingComboBox;
    private JCheckBox inedibleCheckBox;
    private JCheckBox stayInYoshiSCheckBox;
    private JCheckBox weirdGroundBehaviorCheckBox;
    private JCheckBox donTInteractWithCheckBox1;
    private JCheckBox donTChangeDirectionCheckBox;
    private JCheckBox donTTurnIntoCheckBox;
    private JCheckBox spawnsANewSpriteCheckBox;
    private JCheckBox donTInteractWithCheckBox2;
    private JCheckBox makePlatformPassableFromCheckBox;
    private JCheckBox donTEraseWhenCheckBox;
    private JCheckBox canTBeKilledCheckBox;
    private JCheckBox takes5FireballsToCheckBox;
    private JCheckBox canBeJumpedOnCheckBox2;
    private JCheckBox deathFrame2TilesCheckBox;
    private JCheckBox donTTurnIntoCheckBox1;
    private JCheckBox donTGetStuckCheckBox;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JTextField actsLikeTextField;
    private JTextField ASMFilesTextField;
    private JTextField propertyBytesTextField;
    private JTextField textField5;
    private JComboBox statusOverrideComboBox;
    private JTextField uniqueByteTextField;
    private JTextField extraByteAmountTextField;
    private JTextField textField3;

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
        this.setSize(760, 550);
        this.setContentPane(this.contentPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        URL iconURL = this.getClass().getResource("/com/telinc1/faerie/icons/application.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());

        this.menuBar = new FaerieMenuBar(this);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        this.$$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$(){
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
        comboBox3 = new JComboBox();
        panel1.add(comboBox3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabbedPane = new JTabbedPane();
        contentPanel.add(tabbedPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        tabbedPane.addTab(ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("content.sprite"), scrollPane1);
        spritePanel = new JPanel();
        spritePanel.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane1.setViewportView(spritePanel);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$1656"));
        canBeJumpedOnCheckBox = new JCheckBox();
        canBeJumpedOnCheckBox.setText("Can be jumped on");
        panel2.add(canBeJumpedOnCheckBox, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        diesWhenJumpedOnCheckBox = new JCheckBox();
        diesWhenJumpedOnCheckBox.setText("Dies when jumped on");
        panel2.add(diesWhenJumpedOnCheckBox, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        hopInKickShellsCheckBox = new JCheckBox();
        hopInKickShellsCheckBox.setText("Hop in/Kick shells");
        panel2.add(hopInKickShellsCheckBox, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        disappearInACloudCheckBox = new JCheckBox();
        disappearInACloudCheckBox.setText("Disappear in a cloud of smoke");
        panel2.add(disappearInACloudCheckBox, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
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
        final JLabel label2 = new JLabel();
        label2.setFocusable(false);
        label2.setIcon(new ImageIcon(getClass().getResource("/com/telinc1/faerie/icons/object/00.png")));
        label2.setIconTextGap(0);
        label2.setText("");
        panel3.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$1662"));
        useShellAsDeathCheckBox = new JCheckBox();
        useShellAsDeathCheckBox.setText("Use shell as death frame");
        panel4.add(useShellAsDeathCheckBox, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        fallsStraightDownWhenCheckBox = new JCheckBox();
        fallsStraightDownWhenCheckBox.setText("Falls straight down when killed");
        panel4.add(fallsStraightDownWhenCheckBox, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Sprite Clipping:");
        panel4.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        final JLabel label4 = new JLabel();
        label4.setFocusCycleRoot(false);
        label4.setFocusable(false);
        label4.setIcon(new ImageIcon(getClass().getResource("/com/telinc1/faerie/icons/sprite/00.png")));
        label4.setIconTextGap(0);
        label4.setText("");
        panel5.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel6, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$166E"));
        useSecondGraphicsPageCheckBox = new JCheckBox();
        useSecondGraphicsPageCheckBox.setText("Use second graphics page");
        panel6.add(useSecondGraphicsPageCheckBox, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Palette:");
        panel6.add(label5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        final JLabel label6 = new JLabel();
        label6.setIcon(new ImageIcon(getClass().getResource("/com/telinc1/faerie/icons/palette.png")));
        label6.setIconTextGap(0);
        label6.setText("");
        panel6.add(label6, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableFireballKillingCheckBox = new JCheckBox();
        disableFireballKillingCheckBox.setText("Disable fireball killing");
        panel6.add(disableFireballKillingCheckBox, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableCapeKillingCheckBox = new JCheckBox();
        disableCapeKillingCheckBox.setText("Disable cape killing");
        panel6.add(disableCapeKillingCheckBox, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableWaterSplashCheckBox = new JCheckBox();
        disableWaterSplashCheckBox.setText("Disable water splash");
        panel6.add(disableWaterSplashCheckBox, new GridConstraints(4, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTInteractWithCheckBox = new JCheckBox();
        donTInteractWithCheckBox.setText("Don't interact with secondary layers");
        panel6.add(donTInteractWithCheckBox, new GridConstraints(5, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel7, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$167A"));
        executeWhileBeingKilledCheckBox = new JCheckBox();
        executeWhileBeingKilledCheckBox.setText("Execute while being killed");
        panel7.add(executeWhileBeingKilledCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        invincibleToPlayerCheckBox = new JCheckBox();
        invincibleToPlayerCheckBox.setText("Invincible to player");
        panel7.add(invincibleToPlayerCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        executeIfOffscreenCheckBox = new JCheckBox();
        executeIfOffscreenCheckBox.setText("Execute if offscreen");
        panel7.add(executeIfOffscreenCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTChangeIntoCheckBox = new JCheckBox();
        donTChangeIntoCheckBox.setText("Don't change into a shell when stunned");
        panel7.add(donTChangeIntoCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        canTBeKickedCheckBox = new JCheckBox();
        canTBeKickedCheckBox.setText("Can't be kicked like a shell");
        panel7.add(canTBeKickedCheckBox, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        processPlayerInteractionEveryCheckBox = new JCheckBox();
        processPlayerInteractionEveryCheckBox.setText("Process player interaction every frame");
        panel7.add(processPlayerInteractionEveryCheckBox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        givesPowerupWhenEatenCheckBox = new JCheckBox();
        givesPowerupWhenEatenCheckBox.setText("Gives powerup when eaten by Yoshi (dangerous)");
        panel7.add(givesPowerupWhenEatenCheckBox, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTUseDefaultCheckBox = new JCheckBox();
        donTUseDefaultCheckBox.setText("Don't use default player interaction");
        panel7.add(donTUseDefaultCheckBox, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel8, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel8.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$1686"));
        inedibleCheckBox = new JCheckBox();
        inedibleCheckBox.setText("Inedible");
        panel8.add(inedibleCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stayInYoshiSCheckBox = new JCheckBox();
        stayInYoshiSCheckBox.setText("Stay in Yoshi's mouth");
        panel8.add(stayInYoshiSCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        weirdGroundBehaviorCheckBox = new JCheckBox();
        weirdGroundBehaviorCheckBox.setText("Weird ground behavior");
        panel8.add(weirdGroundBehaviorCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTInteractWithCheckBox1 = new JCheckBox();
        donTInteractWithCheckBox1.setText("Don't interact with other sprites");
        panel8.add(donTInteractWithCheckBox1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTChangeDirectionCheckBox = new JCheckBox();
        donTChangeDirectionCheckBox.setText("Don't change direction when touched");
        panel8.add(donTChangeDirectionCheckBox, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTTurnIntoCheckBox = new JCheckBox();
        donTTurnIntoCheckBox.setText("Don't turn into a coin when goal passed");
        panel8.add(donTTurnIntoCheckBox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spawnsANewSpriteCheckBox = new JCheckBox();
        spawnsANewSpriteCheckBox.setText("Spawns a new sprite when stunned");
        panel8.add(spawnsANewSpriteCheckBox, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTInteractWithCheckBox2 = new JCheckBox();
        donTInteractWithCheckBox2.setText("Don't interact with objects");
        panel8.add(donTInteractWithCheckBox2, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel9, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel9.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$190F"));
        makePlatformPassableFromCheckBox = new JCheckBox();
        makePlatformPassableFromCheckBox.setText("Make platform passable from below");
        panel9.add(makePlatformPassableFromCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTEraseWhenCheckBox = new JCheckBox();
        donTEraseWhenCheckBox.setText("Don't erase when goal passed");
        panel9.add(donTEraseWhenCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        canTBeKilledCheckBox = new JCheckBox();
        canTBeKilledCheckBox.setText("Can't be killed by sliding");
        panel9.add(canTBeKilledCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        takes5FireballsToCheckBox = new JCheckBox();
        takes5FireballsToCheckBox.setText("Takes 5 fireballs to kill");
        panel9.add(takes5FireballsToCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        canBeJumpedOnCheckBox2 = new JCheckBox();
        canBeJumpedOnCheckBox2.setText("Can be jumped on with upward Y speed");
        panel9.add(canBeJumpedOnCheckBox2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deathFrame2TilesCheckBox = new JCheckBox();
        deathFrame2TilesCheckBox.setText("Death frame 2 tiles high");
        panel9.add(deathFrame2TilesCheckBox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTTurnIntoCheckBox1 = new JCheckBox();
        donTTurnIntoCheckBox1.setText("Don't turn into a coin with silver P-Switch");
        panel9.add(donTTurnIntoCheckBox1, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTGetStuckCheckBox = new JCheckBox();
        donTGetStuckCheckBox.setText("Don't get stuck in walls (carryable sprites)");
        panel9.add(donTGetStuckCheckBox, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        spritePanel.add(panel10, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, 1, 1, null, null, null, 0, false));
        panel10.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Insertion Settings"));
        comboBox4 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("Tweak");
        defaultComboBoxModel4.addElement("Custom");
        comboBox4.setModel(defaultComboBoxModel4);
        panel10.add(comboBox4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox5 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        defaultComboBoxModel5.addElement("Vanilla");
        defaultComboBoxModel5.addElement("Regular");
        defaultComboBoxModel5.addElement("Shooter");
        defaultComboBoxModel5.addElement("Generator");
        defaultComboBoxModel5.addElement("Initializer");
        defaultComboBoxModel5.addElement("Scroller");
        comboBox5.setModel(defaultComboBoxModel5);
        panel10.add(comboBox5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("ASM Files:");
        panel10.add(label7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ASMFilesTextField = new JTextField();
        ASMFilesTextField.setText("");
        panel10.add(ASMFilesTextField, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField3 = new JTextField();
        panel10.add(textField3, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Acts Like:");
        panel10.add(label8, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        actsLikeTextField = new JTextField();
        panel10.add(actsLikeTextField, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        spritePanel.add(panel11, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel11.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Additional Data"));
        final JLabel label9 = new JLabel();
        label9.setText("Property Bytes:");
        panel11.add(label9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        propertyBytesTextField = new JTextField();
        panel11.add(propertyBytesTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField5 = new JTextField();
        panel11.add(textField5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Status Override:");
        panel11.add(label10, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        statusOverrideComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel6 = new DefaultComboBoxModel();
        defaultComboBoxModel6.addElement("Handle stunned");
        defaultComboBoxModel6.addElement("Handle all");
        defaultComboBoxModel6.addElement("Override all");
        statusOverrideComboBox.setModel(defaultComboBoxModel6);
        panel11.add(statusOverrideComboBox, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Unique Byte:");
        panel11.add(label11, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        uniqueByteTextField = new JTextField();
        panel11.add(uniqueByteTextField, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Extra Byte Amount:");
        panel11.add(label12, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraByteAmountTextField = new JTextField();
        panel11.add(extraByteAmountTextField, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab(ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("content.display"), displayPanel);
        label1.setLabelFor(objectClippingComboBox);
        label2.setLabelFor(scrollPane1);
        label3.setLabelFor(spriteClippingComboBox);
        label5.setLabelFor(paletteComboBox);
        label7.setLabelFor(ASMFilesTextField);
        label8.setLabelFor(actsLikeTextField);
        label9.setLabelFor(propertyBytesTextField);
        label10.setLabelFor(statusOverrideComboBox);
        label11.setLabelFor(uniqueByteTextField);
        label12.setLabelFor(extraByteAmountTextField);
    }

    /** @noinspection ALL */
    public JComponent $$$getRootComponent$$$(){
        return contentPanel;
    }
}
