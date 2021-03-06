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

import com.telinc1.faerie.gui.main.dialog.AboutDialog;

/**
 * The "Help" menu of the main application window.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class HelpMenu extends Menu {
    /**
     * Constructs a "Help" menu.
     *
     * @param parent the menu bar which this menu will belong to
     */
    HelpMenu(MenuBar parent){
        super(parent);
    }

    @Override
    String getID(){
        return "help";
    }

    /**
     * Creates and adds all of the items this menu contains.
     */
    @Override
    void setupMenu(){
        this.addItem("about", event -> {
            AboutDialog dialog = new AboutDialog(this.getMenuBar().getWindow());
            dialog.setVisible(true);
        });
    }
}
