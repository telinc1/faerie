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

import com.telinc1.faerie.util.locale.Warning;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The {@code Preferences} class loads, manages, and stores all preferences
 * for the application.
 */
public class Preferences {
    /**
     * The preference key for storing the scrolling speed in scroll panes.
     */
    public static final String SCROLL_SPEED = "scroll_speed";

    /**
     * The preference key for storing the last used directory in file choosers.
     */
    public static final String LAST_DIRECTORY = "last_directory";

    /**
     * The preference key for storing the X position of the {@code MainWindow}.
     */
    public static final String MAIN_WINDOW_X = "main_window_x";

    /**
     * The preference key for storing the X position of the {@code MainWindow}.
     */
    public static final String MAIN_WINDOW_Y = "main_window_y";

    /**
     * The name of the configuration file, relative to the directory in which
     * Faerie is launched.
     */
    private static final String FILE_NAME = "faerie.ini";

    /**
     * The {@code Application} which this class backs.
     */
    private final Application application;

    /**
     * All of the preferences stored in this object.
     */
    private final Map<String, String> preferences;

    /**
     * The configuration file for the object.
     */
    private File file;

    /**
     * Construct a new {@code Preferences} manager for an {@link Application}.
     */
    Preferences(Application application){
        this.application = application;
        this.preferences = new HashMap<>();
    }

    /**
     * Loads the preferences from a given {@code File}.
     *
     * @return any {@link Warning}s created while reading the file
     */
    public Warning load(){
        File file = this.getFile();

        if(file == null){
            return new Warning("core", "preferences.file");
        }

        if(!file.exists()){
            return null;
        }

        Path path = Paths.get(file.toURI());

        try(Stream<String> stream = Files.lines(path)) {
            stream.forEach(line -> {
                if(line.isEmpty()){
                    return;
                }

                String[] preference = line.split("=", 2);

                if(preference.length != 2){
                    return;
                }

                this.preferences.put(preference[0], preference[1]);
            });
        }catch(IOException exception){
            this.getApplication().getExceptionHandler().report(exception);
            return new Warning("core", "preferences.load.io");
        }catch(SecurityException exception){
            this.getApplication().getExceptionHandler().report(exception);
            return new Warning("core", "preferences.load.security");
        }

        return null;
    }

    /**
     * Returns the {@link Application} backed by these {@code Preferences}.
     */
    public Application getApplication(){
        return this.application;
    }

    /**
     * Returns the {@link File} which stores preferences, or {@code null} if
     * it isn't accessible.
     */
    private File getFile(){
        if(this.file == null){
            try {
                String path = Application.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                File file = new File(path);

                if(!file.isDirectory()){
                    path = file.getParent();
                }

                this.file = new File(path + "/" + Preferences.FILE_NAME);
            }catch(NullPointerException | URISyntaxException | SecurityException exception){
                return null;
            }
        }

        return this.file;
    }

    /**
     * Retrieves the value for a key. If the key doesn't exist, it
     * will be set to a default (fallback) value.
     *
     * @return the {@code String} for the key, or the {@code fallback} if
     * it doesn't exist
     */
    public String get(String key, String fallback){
        if(this.has(key)){
            return this.get(key);
        }

        this.set(key, fallback);
        return fallback;
    }

    /**
     * Retrieves the value for a key. If the key doesn't exist, it
     * will be set to a default (fallback) value.
     *
     * @return the {@code int} for the key, or the {@code fallback} if
     * it doesn't exist
     */
    public int get(String key, int fallback){
        if(this.has(key)){
            String value = this.get(key);

            try {
                return Integer.valueOf(value);
            }catch(NumberFormatException exception){
                // ignore and drop down to the fallback
            }
        }

        this.set(key, fallback);
        return fallback;
    }

    /**
     * Checks if a key exists in the preferences.
     */
    public boolean has(String key){
        return this.preferences.containsKey(key);
    }

    /**
     * Retrieves the value for a key.
     *
     * @return the {@code String} for the key, or {@code null} if it isn't set
     */
    public String get(String key){
        return this.preferences.get(key);
    }

    /**
     * Sets the value of a key.
     *
     * @return the {@code Preferences}, for chaining
     */
    public Preferences set(String key, String value){
        this.preferences.put(key, value);
        return this;
    }

    /**
     * Sets the integer value of a key.
     *
     * @return the {@code Preferences}, for chaining
     */
    public Preferences set(String key, int value){
        this.preferences.put(key, Integer.toString(value));
        return this;
    }

    /**
     * Stores the preferences to a given {@code File}.
     *
     * @return any {@link Warning}s created while writing the file
     */
    public Warning store(){
        File file = this.getFile();

        if(file == null){
            return new Warning("core", "preferences.file");
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for(Map.Entry<String, String> entry : this.preferences.entrySet()){
                writer.write(String.format("%s=%s%n", entry.getKey(), entry.getValue()));
            }

            writer.flush();
        }catch(IOException exception){
            this.getApplication().getExceptionHandler().report(exception);
            return new Warning("core", "preferences.store.io");
        }catch(SecurityException exception){
            this.getApplication().getExceptionHandler().report(exception);
            return new Warning("core", "preferences.store.security");
        }

        return null;
    }
}
