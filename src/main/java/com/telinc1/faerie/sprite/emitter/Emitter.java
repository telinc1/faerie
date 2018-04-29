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

package com.telinc1.faerie.sprite.emitter;

import com.telinc1.faerie.sprite.Sprite;

import java.io.IOException;
import java.io.Writer;

/**
 * Writes a sprite to a configuration file.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public abstract class Emitter {
    /**
     * The sprite which this emitter will write.
     */
    private Sprite sprite;

    /**
     * Constructs an emitter for the given sprite.
     *
     * @param sprite the sprite to write
     */
    public Emitter(Sprite sprite){
        this.sprite = sprite;
    }

    /**
     * Returns the sprite which this emitter will write.
     *
     * @return the sprite which this emitter will write
     */
    public Sprite getSprite(){
        return this.sprite;
    }

    /**
     * Writes the emitter's sprite to the given destination.
     *
     * @param writer the {@link Writer} to emit the sprite to
     * @throws IOException if an error happens during writing
     */
    public abstract void emit(Writer writer) throws IOException;
}
