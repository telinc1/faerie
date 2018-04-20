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

import javax.swing.JOptionPane;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * The unhandled exception handler which cleanly exists when an exception
 * is thrown.
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    /**
     * The application which this exception handler manages.
     */
    private Faerie application;

    /**
     * Construct an unhandled exception handler.
     *
     * @param application the {@link Faerie} application to use
     */
    ExceptionHandler(Faerie application){
        this.application = application;
    }

    /**
     * Shows a warning dialog.
     *
     * @param message the key for the title and content from the core bundle
     */
    public void warn(String message){
        JOptionPane.showMessageDialog(
            this.application.getWindow(),
            Faerie.locale.getString("error." + message + ".content"),
            Faerie.locale.getString("error." + message + ".title"),
            JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Shows an error dialog and exits the application with error code 1.
     *
     * @param message the key for the title and content from the core bundle
     */
    public void error(String message){
        this.error(message, 1);
    }

    /**
     * Shows an error dialog and exits the application.
     *
     * @param message the key for the title and content from the core bundle
     * @param code the exit code for {@link System#exit(int)}
     */
    public void error(String message, int code){
        JOptionPane.showMessageDialog(
            this.application.getWindow(),
            Faerie.locale.getString("error." + message + ".content"),
            Faerie.locale.getString("error." + message + ".title"),
            JOptionPane.ERROR_MESSAGE
        );

        System.exit(code);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable){
        this.exception(throwable);
    }

    /**
     * Shows a detailed string representation of an exception and exits the
     * application with exit code 1.
     *
     * @param throwable the {@link Throwable} to display
     */
    public void exception(Throwable throwable){
        throwable.printStackTrace();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);

        JOptionPane.showMessageDialog(
            this.application.getWindow(),
            String.format(Faerie.locale.getString("error.exception.content"), stringWriter.toString()),
            Faerie.locale.getString("error.exception.title"),
            JOptionPane.ERROR_MESSAGE
        );

        System.exit(1);
    }
}
