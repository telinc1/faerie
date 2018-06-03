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

package com.telinc1.faerie.sprite.provider;

import com.telinc1.faerie.sprite.Sprite;
import com.telinc1.faerie.util.locale.Warning;

import java.io.File;

/**
 * A {@code Provider}'s main job is to provide the main application window with
 * sprites.
 * <p>
 * It is given a {@link java.io.File} which it has to use to provide a list of
 * available sprites. It's also the {@code Provider}'s responsibility to manage
 * which sprite is currently being edited, to track changes to it, and to
 * ultimately save those changes to a {@code File} (be it the original one or
 * another one).
 */
public abstract class Provider {
    /**
     * Returns the original input {@code File}.
     *
     * @return the nullable input
     */
    public abstract File getInput();

    /**
     * Saves every available sprite to the given file.
     * <p>
     * If the file doesn't exist, this method should create it.
     * <p>
     * If the extension of the output file isn't compatible with this provider,
     * the method should throw a {@link SavingException}. Any other exceptions
     * should also be converted to {@code SavingException}s.
     *
     * @param file the file to save to
     * @throws SavingException if an I/O error occurs
     */
    public abstract void save(File file) throws SavingException;

    /**
     * Returns an array of available sprites.
     * <p>
     * Each {@code String} is displayed directly to the user in the combo box
     * at the top of the main window. The indexes of this array should match
     * the indexes used by {@link Provider#loadSprite(int)}.
     * <p>
     * There must always be at least one sprite available. If no sprites are
     * available, the constructor should throw a {@link LoadingException}.
     *
     * @return an array of human-readable sprite names
     */
    public abstract String[] getAvailableSprites();

    /**
     * Loads the given sprite into the {@code Provider}.
     * <p>
     * The index must match the indexes from {@link #getAvailableSprites()}.
     * <p>
     * Index 0 should always be valid. Negative indexes should always be
     * invalid.
     *
     * @param index the index of the sprite to load
     * @throws ProvisionException if the sprite is out of bounds or cannot fully load
     */
    public abstract void loadSprite(int index) throws ProvisionException;

    /**
     * Returns any warnings created during the provision.
     *
     * @return an array of {@link Warning}s which were created
     */
    public abstract Warning[] getWarnings();

    /**
     * Returns the currently loaded sprite index.
     * <p>
     * The integer returned by this method has to be valid according to
     * {@link Provider#loadSprite(int)}.
     *
     * @return the loaded index
     */
    public abstract int getLoadedIndex();

    /**
     * Returns the currently loaded sprite.
     * <p>
     * The returned object should not be modified in any way.
     *
     * @return the currently loaded sprite
     * @see Provider#startModification()
     */
    public abstract Sprite getCurrentSprite();

    /**
     * Returns the currently loaded sprite and prepares the {@code Provider}
     * for changes to it.
     * <p>
     * Unlike {@link Provider#getCurrentSprite()}, the returned object should
     * be safely modifiable. There should be no risk of undefined behavior or
     * discarding of changes.
     *
     * @return the currently loaded sprite
     */
    public abstract Sprite startModification();

    /**
     * Checks whether any sprite available in the provider has been modified.
     *
     * @return whether the provider has been modified
     */
    public abstract boolean isModified();
}
