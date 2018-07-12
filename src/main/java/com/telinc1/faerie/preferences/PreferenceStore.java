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

package com.telinc1.faerie.preferences;

import com.telinc1.faerie.Application;
import com.telinc1.faerie.util.locale.Warning;

/**
 * The {@code PreferenceStore} class provides methods for communicating with an
 * abstract storage medium for miscellaneous application preferences.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public abstract class PreferenceStore {
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
     * The preference key for storing the width of the {@code MainWindow}.
     */
    public static final String MAIN_WINDOW_WIDTH = "main_window_width";

    /**
     * The preference key for storing the height of the {@code MainWindow}.
     */
    public static final String MAIN_WINDOW_HEIGHT = "main_window_height";

    /**
     * The {@code Application} which this class backs.
     */
    private final Application application;

    /**
     * Construct a new preference store for an {@link Application}.
     */
    PreferenceStore(Application application){
        this.application = application;
    }

    /**
     * Returns the {@link Application} backed by this {@code PreferenceStore}.
     */
    public Application getApplication(){
        return this.application;
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
     * Checks if a key exists in the preference store.
     */
    public abstract boolean has(String key);

    /**
     * Retrieves the value for a key.
     *
     * @return the {@code String} for the key, or {@code null} if it isn't set
     */
    public abstract String get(String key);

    /**
     * Sets the value of a key.
     *
     * @return the {@code PreferenceStore}, for chaining
     */
    public abstract PreferenceStore set(String key, String value);

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
     * Sets the integer value of a key.
     *
     * @return the {@code PreferenceStore}, for chaining
     */
    public PreferenceStore set(String key, int value){
        return this.set(key, Integer.toString(value));
    }

    /**
     * Loads the preferences from the storage medium.
     *
     * @return any {@link Warning}s created while reading
     */
    public abstract Warning load();

    /**
     * Stores the preferences to the storage medium.
     *
     * @return any {@link Warning}s created while writing
     */
    public abstract Warning store();
}
