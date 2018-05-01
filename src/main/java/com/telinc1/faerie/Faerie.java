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

package com.telinc1.faerie;

import com.telinc1.faerie.display.Palette;
import com.telinc1.faerie.gui.main.FaerieWindow;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * This is the main class of Faerie, responsible for opening the main program
 * window and reading command line arguments (if any).
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class Faerie {
    /**
     * The command line arguments given to the application.
     */
    private final String[] args;

    /**
     * The handler for unhandled exceptions.
     */
    private final ExceptionHandler exceptionHandler;

    /**
     * The main program window.
     */
    private final FaerieWindow window;

    /**
     * The currently loaded palette.
     */
    private final Palette palette;

    /**
     * Construct and initialize Faerie.
     *
     * @param args the command line arguments given to the application
     */
    private Faerie(String[] args){
        this.args = args;
        this.exceptionHandler = new ExceptionHandler(this);

        try {
            Thread.setDefaultUncaughtExceptionHandler(this.exceptionHandler);
        }catch(SecurityException exception){
            exception.printStackTrace();
            this.getExceptionHandler().warn("core", "launch.handler");
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(ReflectiveOperationException | UnsupportedLookAndFeelException exception){
            this.getExceptionHandler().warn("core", "launch.lookAndFeel", exception);
        }

        this.palette = new Palette();

        try {
            URL palette = this.getClass().getResource("/com/telinc1/faerie/binary/generic.mw3");
            this.getPalette().loadMW3File(new File(palette.getPath()));
        }catch(IOException exception){
            this.getExceptionHandler().error("core", "launch.palette", exception);
        }

        this.window = new FaerieWindow(this);
        this.getWindow().setVisible(true);
    }

    /**
     * Returns the currently loaded {@link Palette}.
     *
     * @return the currently loaded palette
     */
    public Palette getPalette(){
        return this.palette;
    }

    /**
     * Creates and starts Faerie by opening the main window.
     *
     * @param args the command line arguments given to the application
     */
    public static void main(String[] args){
        Faerie faerie = new Faerie(args);
    }

    /**
     * Returns the used exception handler.
     */
    public ExceptionHandler getExceptionHandler(){
        return this.exceptionHandler;
    }

    /**
     * Returns the main program window.
     * <p>
     * Note that this can be null if the application hasn't fully initialized.
     *
     * @return the main {@link FaerieWindow} of the application
     */
    public FaerieWindow getWindow(){
        return this.window;
    }

    /**
     * Returns the command line arguments passed to the application.
     *
     * @return the array of command line arguments
     */
    public String[] getArgs(){
        return this.args;
    }
}
