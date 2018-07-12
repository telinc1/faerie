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

import com.telinc1.faerie.cli.CommandLineInterface;
import com.telinc1.faerie.gui.GraphicalInterface;
import com.telinc1.faerie.preferences.FlatFileStore;
import com.telinc1.faerie.preferences.PreferenceStore;
import com.telinc1.faerie.sprite.provider.ROMProvider;
import com.telinc1.faerie.util.locale.LocalizedException;
import com.telinc1.faerie.util.locale.Warning;
import com.telinc1.faerie.util.notification.Notifier;
import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This is the main class of Faerie, responsible for opening the main program
 * userInterface and reading command line arguments (if any).
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class Application {
    /**
     * The command line argument store for the application.
     */
    private final Arguments arguments;

    /**
     * The preference store for the application.
     */
    private final PreferenceStore preferences;

    /**
     * The application's {@link Notifier}.
     */
    private final Notifier notifier;

    /**
     * The handler for unhandled exceptions.
     */
    private final ExceptionHandler exceptionHandler;

    /**
     * The main program userInterface.
     */
    private final UserInterface userInterface;

    /**
     * Construct and initialize the application.
     *
     * @param args the command line arguments given to the application
     */
    private Application(String[] args){
        this.arguments = new Arguments(args);
        this.preferences = new FlatFileStore(this);
        this.notifier = new Notifier(this);
        this.exceptionHandler = new ExceptionHandler(this);

        try {
            this.getArguments().parse();
        }catch(ParseException exception){
            this.getArguments().printFormattedHelp();
            this.exit(1);
        }

        if(this.getArguments().isHeadless()){
            this.userInterface = new CommandLineInterface(this);
        }else{
            this.userInterface = new GraphicalInterface(this);
        }

        try {
            Thread.setDefaultUncaughtExceptionHandler(this.exceptionHandler);
        }catch(SecurityException exception){
            this.getExceptionHandler().report(exception);
            this.getNotifier().warn("core", "launch.handler");
        }

        Warning preferenceWarning = this.getPreferences().load();

        if(preferenceWarning != null){
            this.getNotifier().notify(preferenceWarning);
        }

        this.getUserInterface().init();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(Resources.getResource("data/sprites/list.txt")))) {
            String line;

            while((line = reader.readLine()) != null){
                ROMProvider.NAMES.add(line);
            }
        }catch(IOException exception){
            throw new LocalizedException(exception, "core", "launch.sprites");
        }

        this.getUserInterface().start();
    }

    /**
     * Returns the {@link Notifier} for the application.
     */
    public Notifier getNotifier(){
        return this.notifier;
    }

    /**
     * Return the command line {@code Arguments} store.
     */
    public Arguments getArguments(){
        return this.arguments;
    }

    /**
     * Cleanly exits out of the application with the a status code.
     */
    public void exit(int status){
        if(status == 0){
            PreferenceStore preferences = this.getPreferences();
            Warning warning = preferences.store();

            if(warning != null){
                this.getNotifier().notify(warning);
            }
        }

        UserInterface window = this.getUserInterface();

        if(window != null && !window.stop() && status == 0){
            return;
        }

        System.exit(status);
    }

    /**
     * Returns the {@code PreferenceStore} object which manages this application.
     */
    public PreferenceStore getPreferences(){
        return this.preferences;
    }

    /**
     * Returns the {@link ExceptionHandler} for the application.
     *
     * @return the {@code ExceptionHandler} for unhandled exceptions
     */
    public ExceptionHandler getExceptionHandler(){
        return this.exceptionHandler;
    }

    /**
     * Returns the main program user interface. This can be {@code null} if the
     * application hasn't been fully initialized.
     */
    public UserInterface getUserInterface(){
        return this.userInterface;
    }

    /**
     * Creates and starts the application by opening the main user interface.
     *
     * @param args the command line arguments given to the application
     */
    public static void main(String[] args){
        Application application = new Application(args);
    }
}
