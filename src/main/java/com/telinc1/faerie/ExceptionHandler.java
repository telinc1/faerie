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

import com.telinc1.faerie.util.IMinorException;
import com.telinc1.faerie.util.locale.ILocalizable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The {@code ExceptionHandler} class handles and reports exceptions. It also
 * serves as the default unhandled exception handler and makes sure to exit
 * cleanly if an unhandled exception is thrown.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    /**
     * The {@link SimpleDateFormat} used to create the file names of error logs
     * and crash reports.
     */
    private static final String FILE_NAME = "'faerie'-yyyy-MM-dd-HH-mm.'log'";

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
     * Tries to set this {@code ExceptionHandler} as the default handler for
     * uncaught exceptions for all threads in the current runtime. If this
     * fails, the exception will be silently reported and shown to the user.
     */
    public void setAsDefaultHandler(){
        try {
            Thread.setDefaultUncaughtExceptionHandler(this);
        }catch(SecurityException exception){
            this.report(exception);
            this.getApplication().getInterface().getNotifier().warn("core", "launch.handler");
        }
    }

    /**
     * Reports a {@link Throwable} to the application log and saves a detailed
     * crash report. If the given {@code throwable} is
     * {@link IMinorException minor} or the {@code minor} argument is
     * {@code true}, a crash report will only be saved if the application is
     * running in verbose mode.
     *
     * @param throwable the {@code Throwable} to report
     * @param minor whether to treat the exception as minor
     */
    public void report(Throwable throwable, boolean minor){
        throwable.printStackTrace();

        if((minor || (throwable instanceof IMinorException && ((IMinorException)throwable).isMinor())) && !this.getApplication().getArguments().isVerbose()){
            return;
        }

        String name = new SimpleDateFormat(ExceptionHandler.FILE_NAME).format(new Date());
        File file = Resources.getFile(name);

        if(file == null){
            this.getApplication().getInterface().getNotifier().error("core", "report");
            return;
        }

        boolean exists = file.exists();

        // oh holy java gods, why art thee so shitty
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            if(!exists){
                writer.println(Resources.getString("core", "report"));
                writer.println();
                writer.println("--------------------------------------------------------------------------------");
                writer.println();

                this.writeSystemDetails(writer);
            }

            writer.println("--------------------------------------------------------------------------------");
            writer.println();
            throwable.printStackTrace(writer);
            writer.println();
            writer.flush();
        }catch(IOException exception){
            exception.printStackTrace();
            this.getApplication().getInterface().getNotifier().error("core", "report");
        }
    }

    /**
     * Reports a {@link Throwable} to the application log and saves a detailed
     * crash report.
     *
     * @param throwable the {@code Throwable} to report
     * @see #report(Throwable, boolean)
     */
    public void report(Throwable throwable){
        this.report(throwable, false);
    }

    /**
     * Shows a detailed string representation of an exception and reports it.
     * If the {@link Throwable} is {@link ILocalizable}, it will directly be
     * displayed as a notification. If not, it will be shown as a fatal error.
     *
     * @param throwable the {@link Throwable} to handle
     */
    public void handle(Throwable throwable){
        this.report(throwable);

        if(throwable instanceof ILocalizable){
            this.getApplication().getInterface().getNotifier().notify((ILocalizable)throwable);
            return;
        }

        this.getApplication().getInterface().getNotifier().fatal("core", "exception", throwable);
    }

    /**
     * Returns the application of the exception handler.
     *
     * @return the {@code Application} which owns this {@code ExceptionHandler}
     */
    public Application getApplication(){
        return this.application;
    }

    /**
     * Writes information about the user's system to a {@code PrintWriter}.
     */
    private void writeSystemDetails(PrintWriter writer){
        Runtime runtime = Runtime.getRuntime();
        String jvmFlags;

        try {
            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
            List<String> jvm = runtimeMXBean.getInputArguments();

            StringBuilder builder = new StringBuilder();

            for(String argument : jvm){
                builder.append(argument);
                builder.append(" ");
            }

            jvmFlags = builder.toString();
        }catch(Exception exception){
            jvmFlags = exception.getClass().getName() + ": " + exception.getMessage();
        }

        writer.println(String.format(
            "Application: %s with %s",
            Resources.getString("core", "report.version"),
            String.join(" ", this.getApplication().getArguments().getInput())
        ));
        writer.println(String.format(
            "Operating System: %s v%s (%s)",
            this.getSystemProperty("os.name"),
            this.getSystemProperty("os.version"),
            this.getSystemProperty("os.arch")
        ));
        writer.println(String.format(
            "Java: %s from %s (%s) at %s",
            this.getSystemProperty("java.version"),
            this.getSystemProperty("java.vendor"),
            this.getSystemProperty("java.vendor.url"),
            this.getSystemProperty("java.home")
        ));
        writer.println(String.format(
            "Java VM: %s v%s from %s with %s",
            this.getSystemProperty("java.vm.name"),
            this.getSystemProperty("java.vm.version"),
            this.getSystemProperty("java.vm.vendor"),
            jvmFlags
        ));
        writer.println(String.format(
            "Memory: %d total, %d maximum, %d free",
            runtime.totalMemory(),
            runtime.maxMemory(),
            runtime.freeMemory()
        ));

        writer.println();
    }

    /**
     * Retrieve a system property without throwing exceptions.
     *
     * @return the system property's value or the name of a caught exception
     * @see System#getProperty(String)
     */
    private String getSystemProperty(String key){
        try {
            return System.getProperty(key);
        }catch(Exception exception){
            return exception.getClass().getName() + ": " + exception.getMessage();
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable){
        this.report(throwable);

        if(throwable instanceof ILocalizable){
            this.getApplication().getInterface().getNotifier().notify((ILocalizable)throwable);
            return;
        }

        this.getApplication().getInterface().getNotifier().fatal("core", "threadedException", new Object[]{"thread", thread.getName(), throwable});
    }
}
