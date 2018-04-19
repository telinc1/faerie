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

import com.telinc1.faerie.gui.main.FaerieWindow;

import javax.swing.*;

/**
 * The menu bar for the main Faerie application window.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class FaerieMenuBar extends JMenuBar {
    /**
     * The application window which owns this menu bar.
     */
    private FaerieWindow parent;

    /**
     * The "File" menu of the menu bar.
     */
    private FaerieFileMenu fileMenu;

    /**
     * Constructs a main window menu bar.
     * <p>
     * All menus are automatically created. The menu bar is automatically added
     * to the parent.
     *
     * @param parent the application window which owns this menu bar
     */
    public FaerieMenuBar(FaerieWindow parent){
        this.parent = parent;

        this.fileMenu = new FaerieFileMenu(this);

        this.getParent().setJMenuBar(this);
    }

    /**
     * Returns the parent window of the menu bar.
     *
     * @return an instance of the {@link FaerieWindow} which owns the menu bar
     */
    @Override
    public FaerieWindow getParent(){
        return this.parent;
    }
}
