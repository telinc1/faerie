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
     * The input file to this {@code Provider}.
     */
    private File input;

    /**
     * Constructs a {@code Provider} for the given {@code File}.
     *
     * @param input the input file to the provider
     * @throws LoadingException if the file is unreadable or malformed
     */
    public Provider(File input) throws LoadingException{
        this.input = input;
    }

    /**
     * Returns the original input {@code File}.
     *
     * @return the input
     */
    public File getInput(){
        return this.input;
    }

    /**
     * Saves all available sprites to the {@code Provider}'s original input
     * file.
     *
     * @see Provider#save(File)
     */
    public void save() throws SavingException{
        this.save(this.getInput());
    }

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
     * available, the {@link Provider#Provider(File) constructor} should throw
     * a {@link LoadingException}.
     *
     * @return an array of human-readable sprite names
     */
    public abstract String[] getAvailableSprites();

    /**
     * Loads the given sprite into the {@code Provider}.
     * <p>
     * The index must matches the indexes from
     * {@link Provider#getAvailableSprites()}.
     * <p>
     * Index 0 should always be valid. Negative indexes should always be
     * invalid.
     *
     * @param index the index of the sprite to load
     * @throws ProvisionException if the sprite cannot fully load
     * @throws IndexOutOfBoundsException if the sprite index is invalid
     */
    public abstract void loadSprite(int index) throws ProvisionException, IndexOutOfBoundsException;

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
