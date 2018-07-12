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
 * A {@code Provider} serves as a repository of one or many sprites within a
 * given file. {@code Provider}s have to load a file of a given format and
 * parse it into a {@link Sprite} which can be used by the rest of the
 * application, specifically the
 * {@link com.telinc1.faerie.UserInterface UserInterface}. The {@code Provider}
 * tracks which sprites have been loaded and what changes have been made to
 * them.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public abstract class Provider {
    /**
     * Returns the original input {@code File}.
     *
     * @return the nullable input
     */
    public abstract File getInput();

    /**
     * Saves every available sprite to the given file. If the file doesn't
     * exist, it should be created if possible.
     * <p>
     * If the extension of the output file isn't compatible with this provider,
     * a {@link SavingException} should be thrown. Any other exceptions should
     * also be converted to {@link SavingException}s.
     * <p>
     * If the method returns a non-{@code null} {@code Provider}, the
     * interface's loaded provider should be replaced with it. This allows
     * saving to formats handled by other providers. Note that this
     * {@code Provider} should not be left in an invalid, undefined, or
     * unusable state after the save even if the method doesn't return
     * {@code null}.
     *
     * @param file the file to save to
     * @return a new {@code Provider} for the handler
     * @throws SavingException if an I/O error occurs
     */
    public abstract Provider save(File file) throws SavingException;

    /**
     * Returns an array of available sprites. Each returned {@code String}
     * should be human-readable. The indexes of this array should match the
     * indexes used by {@link #loadSprite(int)} but do not have to represent
     * all possible sprites.
     * <p>
     * There must always be at least one sprite available. If no sprites are
     * available, the constructor should throw a {@link LoadingException}.
     *
     * @return an array of human-readable sprite names
     */
    public abstract String[] getAvailableSprites();

    /**
     * Loads the given sprite into the {@code Provider}. The index must match
     * the indexes from {@link #getAvailableSprites()}. Index {@code 0} should
     * always be valid. Negative indexes should always be invalid.
     *
     * @param index the index of the sprite to load
     * @throws ProvisionException if the sprite is out of bounds or cannot fully load
     */
    public abstract void loadSprite(int index) throws ProvisionException;

    /**
     * Returns any {@code Warning}s created during the provision.
     */
    public abstract Warning[] getWarnings();

    /**
     * Returns the index of the currently loaded sprite. The integer returned
     * by this method has to be valid according to {@link #loadSprite(int)}.
     *
     * @see #loadSprite(int)
     * @see #getCurrentSprite()
     */
    public abstract int getLoadedIndex();

    /**
     * Returns the currently loaded sprite. The returned object could be
     * mutable but should be treated as read-only. Do not modify it to ensure
     * the correct behavior of the application.
     *
     * @return the currently loaded sprite
     * @see Provider#startModification()
     */
    public abstract Sprite getCurrentSprite();

    /**
     * Returns the currently loaded sprite and prepares the {@code Provider}
     * for changes to be made to it. Unlike {@link #getCurrentSprite()}, the
     * returned mutable object should be completely safe to modify with no
     * resulting unexpected behavior or discarded changes.
     *
     * @return the currently loaded sprite
     */
    public abstract Sprite startModification();

    /**
     * Checks whether any of the sprites available in the provider have been
     * modified.
     *
     * @see #startModification()
     */
    public abstract boolean isModified();
}
