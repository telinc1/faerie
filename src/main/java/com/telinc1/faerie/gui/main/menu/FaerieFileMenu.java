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

package com.telinc1.faerie.gui.main.menu;

import com.telinc1.faerie.Resources;
import com.telinc1.faerie.gui.chooser.ConfigurationChooser;
import com.telinc1.faerie.gui.main.FaerieWindow;
import com.telinc1.faerie.sprite.provider.ConfigurationProvider;
import com.telinc1.faerie.sprite.provider.LoadingException;
import com.telinc1.faerie.util.TypeUtils;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * The "File" menu of the main Faerie application window.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class FaerieFileMenu extends FaerieMenu {
    /**
     * The {@link JFileChooser} used for all opening and saving operations in
     * the menu.
     */
    private ConfigurationChooser configurationChooser;

    /**
     * Constructs a "File" menu.
     *
     * @param parent the menu bar which this menu will belong to
     */
    FaerieFileMenu(FaerieMenuBar parent){
        super(parent);

        this.configurationChooser = new ConfigurationChooser();
    }

    /**
     * Creates and adds all of the items this menu contains.
     */
    @Override
    void setupMenu(){
        FaerieWindow window = this.getMenuBar().getWindow();

        this.addItem("new", KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK, null);
        this.addItem("open", KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK, event -> {
            int result = this.getConfigurationChooser().showOpen(window);

            if(result == JFileChooser.APPROVE_OPTION){
                File file = this.getConfigurationChooser().getSelectedFile();

                if(TypeUtils.isConfiguration(file)){
                    try {
                        ConfigurationProvider provider = new ConfigurationProvider(file);
                        window.setProvider(provider);
                    }catch(LoadingException exception){
                        JOptionPane.showMessageDialog(
                            window,
                            exception.getMessage(),
                            Resources.getString("chooser", "chooser.configuration.loading.title"),
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }else if(TypeUtils.isROM(file)){
                    // TODO: open and edit ROM files
                }else{
                    JOptionPane.showMessageDialog(
                        window,
                        Resources.getString("chooser", "chooser.configuration.type.content"),
                        Resources.getString("chooser", "chooser.configuration.type.title"),
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }

            this.getConfigurationChooser().setSelectedFile(null);
        });

        this.addItem("save", KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK, null);
        this.addItem("saveAs", KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK, null);
    }

    @Override
    String getID(){
        return "file";
    }

    /**
     * Returns the {@link JFileChooser} for the File menu.
     *
     * @return the shared file chooser used for opening and saving
     */
    public ConfigurationChooser getConfigurationChooser(){
        return this.configurationChooser;
    }
}
