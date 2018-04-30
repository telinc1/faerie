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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * The "File" menu of the main Faerie application window.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class FaerieFileMenu extends FaerieMenu {
    /**
     * Constructs a "File" menu.
     *
     * @param parent the menu bar which this menu will belong to
     */
    FaerieFileMenu(FaerieMenuBar parent){
        super(parent);
    }

    @Override
    String getID(){
        return "file";
    }

    /**
     * Creates and adds all of the items this menu contains.
     */
    @Override
    void setupMenu(){
        this.addItem("new", KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
        this.addItem("open", KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
        this.addItem("save", KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
        this.addItem("saveAs", KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
    }
}
