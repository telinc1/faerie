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

import com.telinc1.faerie.notification.Notifier;
import com.telinc1.faerie.sprite.provider.Provider;

import java.io.File;

/**
 * This is the base class for all possible user interfaces of the application.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public abstract class UserInterface {
    /**
     * The application which owns and uses this user interface.
     */
    private final Application application;

    /**
     * The user interface's {@code Notifier}.
     */
    private final Notifier notifier;

    /**
     * The currently loaded sprite provider.
     */
    private Provider provider;

    /**
     * Creates a new user interface for the given {@code Application}.
     */
    public UserInterface(Application application){
        this.application = application;
        this.notifier = this.createNotifier();
    }

    /**
     * Creates and returns a usable fresh instance of this user interface's
     * respective notifier.
     */
    protected abstract Notifier createNotifier();

    /**
     * Initializes all of the user interface's specific components. At this
     * point, the exception handler, the notifier, and the preference store
     * have been initialized and can be reliably used.
     */
    public abstract void init();

    /**
     * Starts the user interface after the entire application has been
     * initialized.
     */
    public abstract void start();

    /**
     * Opens a {@code File} through an appropriate provider.
     */
    public abstract void openFile(File file);

    /**
     * Saves the current provider to the given file.
     * <p>
     * If a {@code null} {@code file} parameter is provided, the currently
     * opened file should be saved in-place. If no file is currently open, the
     * user should be prompted to open one.
     *
     * @param file the file to save to
     */
    public abstract void saveProvider(File file);

    /**
     * Unloads the current provider, first making sure all changes are saved.
     * <p>
     * Unloads the currently loaded provider, if any. Intermediate user
     * intervention can prevent the provider from being unloaded.
     *
     * @return whether the provider was unloaded
     */
    public abstract boolean unloadProvider();

    /**
     * Returns the {@code Application} which owns this user interface.
     */
    public Application getApplication(){
        return this.application;
    }

    /**
     * Returns the {@code Notifier} for the user interface.
     */
    public Notifier getNotifier(){
        return this.notifier;
    }

    /**
     * Returns the current sprite provider loaded by the interface.
     *
     * @return a nullable {@link Provider}
     */
    public Provider getProvider(){
        return this.provider;
    }

    /**
     * Replaces the interface's currently loaded provider. Any changes required
     * should be handled accordingly.
     * <p>
     * This method should just change the provider without requiring any user
     * intervention.
     *
     * @param provider the new provider
     * @return the interface, for chaining
     */
    public UserInterface setProvider(Provider provider){
        this.provider = provider;
        return this;
    }

    /**
     * Cleans up anything created by the interface before quitting the
     * application.
     *
     * @return whether the interface can exit cleanly
     */
    public abstract boolean stop();
}
