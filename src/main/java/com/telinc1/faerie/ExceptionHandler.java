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
 *
 * @author Telinc1
 * @since 1.0.0
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
     * Creates a string representation of an exception's trace log.
     *
     * @param throwable the exception to get the stack trace of
     * @return the formatted stack trace
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);

        return stringWriter.toString();
    }

    /**
     * Shows a warning dialog.
     *
     * @param source the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void warn(String source, String subkey, Object... arguments){
        String title = Resources.getString(source, "error." + subkey + ".title", arguments);
        String content = Resources.getString(source, "error." + subkey + ".content", arguments);

        JOptionPane.showMessageDialog(this.application.getWindow(), content, title, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Shows a detailed string representation of an exception and exits the
     * application with exit code 1.
     *
     * @param throwable the {@link Throwable} to display
     */
    public void exception(Throwable throwable){
        throwable.printStackTrace();
        this.error("core", "exception", throwable);
    }

    /**
     * Shows an error dialog and exits the application with error code 1.
     *
     * @param source the bundle to look in
     * @param subkey the base key to look for
     * @param arguments the arguments to format the message with
     */
    public void error(String source, String subkey, Object... arguments){
        String title = Resources.getString(source, "error." + subkey + ".title", arguments);
        String content = Resources.getString(source, "error." + subkey + ".content", arguments);

        JOptionPane.showMessageDialog(this.application.getWindow(), content, title, JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable){
        throwable.printStackTrace();
        this.error("core", "threadedException", "thread", thread.getName(), throwable);
    }
}
