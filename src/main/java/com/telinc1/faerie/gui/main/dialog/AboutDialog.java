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
import com.telinc1.faerie.gui.JScaledImage;
import com.telinc1.faerie.gui.main.MainWindow;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The dialog which is displayed from the main window to show information about
 * the application.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class AboutDialog extends JDialog {
    /**
     * The {@code MainWindow} to which this {@code AboutDialog} belongs.
     */
    private MainWindow window;

    private JPanel contentPanel;
    private JTextPane websiteGitHubTextPane;
    private JTextPane httpsTelinc1ComProjectsTextPane;
    private JScaledImage logo;

    /**
     * Construct an About dialog, including all of its inner components.
     * <p>
     * The title and sizes are automatically set. Note that the window is not
     * opened.
     */
    public AboutDialog(MainWindow window){
        super(window, Resources.getString("about", "title"), true);
        this.window = window;

        this.$$$setupUI$$$();
        this.configureUIComponents();

        this.setContentPane(this.contentPanel);

        this.setSize(330, 215);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        URL iconURL = Resources.class.getResource(Resources.PACKAGE + "/images/application.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
    }

    /**
     * Configures UI components after they have been created.
     */
    private void configureUIComponents(){
        try {
            this.logo.loadImage("application.png");
        }catch(IOException exception){
            this.getWindow().getApplication().getExceptionHandler().report(exception);
        }
    }

    /**
     * Returns the {@code MainWindow} to which this {@code AboutDialog} belongs.
     */
    public MainWindow getWindow(){
        return this.window;
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
        contentPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        logo = new JScaledImage();
        contentPanel.add(logo, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(64, 64), new Dimension(64, 64), new Dimension(64, 64), 0, false));
        final JTextPane textPane1 = new JTextPane();
        textPane1.setAutoscrolls(false);
        textPane1.setEditable(false);
        textPane1.setEnabled(true);
        textPane1.setFocusable(false);
        Font textPane1Font = this.$$$getFont$$$(null, Font.BOLD, 16, textPane1.getFont());
        if(textPane1Font != null) textPane1.setFont(textPane1Font);
        textPane1.setOpaque(false);
        textPane1.setText(ResourceBundle.getBundle("com/telinc1/faerie/locale/About").getString("label.application"));
        contentPanel.add(textPane1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JTextPane textPane2 = new JTextPane();
        textPane2.setAutoscrolls(false);
        textPane2.setEditable(false);
        textPane2.setEnabled(true);
        textPane2.setFocusable(false);
        textPane2.setOpaque(false);
        textPane2.setText(ResourceBundle.getBundle("com/telinc1/faerie/locale/About").getString("label.information"));
        contentPanel.add(textPane2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        websiteGitHubTextPane = new JTextPane();
        websiteGitHubTextPane.setAutoscrolls(false);
        websiteGitHubTextPane.setEditable(false);
        websiteGitHubTextPane.setFocusable(false);
        websiteGitHubTextPane.setOpaque(false);
        websiteGitHubTextPane.setText(ResourceBundle.getBundle("com/telinc1/faerie/locale/About").getString("label.link.left"));
        contentPanel.add(websiteGitHubTextPane, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        httpsTelinc1ComProjectsTextPane = new JTextPane();
        httpsTelinc1ComProjectsTextPane.setAutoscrolls(false);
        httpsTelinc1ComProjectsTextPane.setEditable(false);
        httpsTelinc1ComProjectsTextPane.setOpaque(false);
        httpsTelinc1ComProjectsTextPane.setText(ResourceBundle.getBundle("com/telinc1/faerie/locale/About").getString("label.link.right"));
        contentPanel.add(httpsTelinc1ComProjectsTextPane, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /** @noinspection ALL */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont){
        if(currentFont == null) return null;
        String resultName;
        if(fontName == null){resultName = currentFont.getName();}else{
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if(testFont.canDisplay('a') && testFont.canDisplay('1')){resultName = fontName;}else{
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /** @noinspection ALL */
    public JComponent $$$getRootComponent$$$(){ return contentPanel; }
}
