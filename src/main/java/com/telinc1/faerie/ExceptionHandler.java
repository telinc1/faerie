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

import com.telinc1.faerie.util.locale.ILocalizable;

import javax.swing.JFrame;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * The unhandled exception handler which cleanly exits when an exception
 * is thrown.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    /**
     * The application which this exception handler manages.
     */
    private Application application;

    /**
     * Construct an unhandled exception handler.
     *
     * @param application the {@link Application} application to use
     */
    ExceptionHandler(Application application){
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
     * Shows a detailed string representation of an exception and reports it.
     *
     * If the {@link Throwable} is {@link ILocalizable}, it will directly be
     * displayed as a notification. If not, it will be shown as a fatal error.
     *
     * @param throwable the {@link Throwable} to handle
     */
    public void handle(Throwable throwable){
        // this.handle(this.getApplication().getUserInterface(), throwable);
        this.handle(null, throwable);
    }

    /**
     * Shows a detailed string representation of an exception and reports it.
     * <p>
     * If the {@link Throwable} is {@link ILocalizable}, it will directly be
     * displayed as a notification. If not, it will be shown as a fatal error.
     *
     * @param parent the {@code JFrame} which directly or indirectly produced
     * this exception
     * @param throwable the {@link Throwable} to handle
     */
    public void handle(JFrame parent, Throwable throwable){
        this.report(throwable);

        if(throwable instanceof ILocalizable){
            this.getApplication().getNotifier().notify(parent, (ILocalizable)throwable);
            return;
        }

        this.getApplication().getNotifier().fatal(parent, "core", "exception", throwable);
    }

    /**
     * Returns the application of the exception handler.
     *
     * @return the {@code Application} which owns this {@code ExceptionHandler}
     */
    public Application getApplication(){
        return this.application;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable){
        this.report(throwable);

        if(throwable instanceof ILocalizable){
            this.getApplication().getNotifier().notify((ILocalizable)throwable);
            return;
        }

        this.getApplication().getNotifier().fatal("core", "threadedException", "thread", thread.getName(), throwable);
    }

    /**
     * Reports a {@link Throwable} to the application log and saves a detailed
     * crash report.
     *
     * @param throwable the {@code Throwable} to report
     */
    public void report(Throwable throwable){
        // TODO: actually report and save the exception correctly
        throwable.printStackTrace();
    }
}
