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

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * This is the common ancestor of all Faerie menus. It provides basic utility
 * methods.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public abstract class FaerieMenu extends JMenu {
    /**
     * The menu bar which owns this menu.
     */
    private FaerieMenuBar parent;

    /**
     * Constructs a menu.
     *
     * @param parent the menu bar which this menu will belong to
     */
    FaerieMenu(FaerieMenuBar parent){
        super();
        this.parent = parent;

        this.setTitle(this, this.getID());
        this.setupMenu();
    }

    /**
     * Sets the title and mnemonic of the given {@code JMenuItem} based on the
     * string key from the resource bundle.
     * <p>
     * this method totally isn't a carbon copy of idea's gui designer methods
     *
     * @param item the item whose name and mnemonic should be set
     * @param key the key from the resource bundle
     */
    protected void setTitle(JMenuItem item, String key){
        String text = Resources.getString("main", "menu." + key);
        StringBuilder result = new StringBuilder();
        boolean hasMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;

        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == '&'){
                i++;

                if(i == text.length()){
                    break;
                }

                if(!hasMnemonic && text.charAt(i) != '&'){
                    hasMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }

            result.append(text.charAt(i));
        }

        item.setText(result.toString());

        if(hasMnemonic){
            item.setMnemonic(mnemonic);
            item.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * Returns a unique ID which identifies this menu.
     *
     * @return the ID, usually just the menu's name
     */
    abstract String getID();

    /**
     * Creates and adds all of the items this menu contains.
     */
    abstract void setupMenu();

    /**
     * Creates and adds a menu item with an accelerator shortcut.
     *
     * @param key the key from the resource bundle for the name
     * @param keyCode the key for the accelerator from {@link java.awt.event.KeyEvent}
     * @param modifiers the modifiers for the accelerator from {@link java.awt.event.InputEvent}
     * @return the created menu item
     */
    protected JMenuItem addItem(String key, int keyCode, int modifiers){
        JMenuItem item = this.addItem(key);
        item.setAccelerator(KeyStroke.getKeyStroke(keyCode, modifiers));

        return item;
    }

    /**
     * Creates and adds an item to this menu.
     * <p>
     * The name and mnemonic are automatically set based on the resource bundle
     * key.
     *
     * @param key the key from the resource bundle for the name
     * @return the created menu item
     */
    protected JMenuItem addItem(String key){
        JMenuItem item = new JMenuItem();
        this.setTitle(item, this.getID() + "." + key);
        this.add(item);

        return item;
    }

    /**
     * Returns the parent menu bar for this "File" menu.
     *
     * @return an instance of the {@link FaerieMenuBar} which owns this menu
     */
    public FaerieMenuBar getMenuBar(){
        return this.parent;
    }
}