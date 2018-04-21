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

package com.telinc1.faerie.sprite;

import com.telinc1.faerie.sprite.display.DisplayData;

/**
 * Denotes that a sprite can be saved in a configuration file.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public interface IConfigurable {
    /**
     * Returns the type of the sprite.
     *
     * @return an element of {@link EnumSpriteType} which represents this
     * sprite's type
     */
    EnumSpriteType getType();

    /**
     * Returns the subtype of the sprite.
     *
     * @return an element of {@link EnumSpriteSubType} which represents this
     * sprite's subtype
     */
    EnumSpriteSubType getSubType();

    /**
     * Returns the vanilla sprite number whose properties this sprite shares.
     *
     * @return an integer in the range [0; 255] representing a vanilla sprite
     */
    int getActsLike();

    /**
     * Returns the behavior (Tweaker settings) of the sprite.
     * <p>
     * If the subtype doesn't have behavior settings, this can return null.
     *
     * @return the {@link SpriteBehavior} of this sprite
     */
    SpriteBehavior getBehavior();

    /**
     * Returns the sprite's first property byte.
     * <p>
     * If the subtype doesn't have property bytes, this can return -1.
     *
     * @return an integer in the range [0; 255] representing the
     * first property byte
     */
    int getFirstPropertyByte();

    /**
     * Returns the sprite's second property byte.
     * <p>
     * The top two bits of the second property byte are implicitly used
     * as the status override settings and should be masked out.
     * <p>
     * If the subtype doesn't have property bytes, this can return -1.
     *
     * @return an integer in the range [0; 63] representing the
     * first extra property byte
     */
    int getSecondPropertyByte();

    /**
     * Returns the type of handling this sprite uses for its status.
     *
     * @return an element of {@link EnumStatusHandling} which represents how
     * the sprite status is handled
     */
    EnumStatusHandling getStatusHandling();

    /**
     * Returns the sprite's additional unique byte.
     * <p>
     * If the subtype doesn't have a unique byte, this can return -1.
     *
     * @return an integer in the range [0; 255] representing the unique byte (code)
     */
    int getUniqueByte();

    /**
     * Returns the amount of extra bytes this sprite has.
     * <p>
     * This should not be greater than maximum amount defined by the subtype.
     *
     * @return an integer which represents how many extra bytes the sprite uses
     * @see EnumSpriteSubType#getExtraBytes()
     */
    int getExtraBytes();

    /**
     * Returns the name of the first ASM file used by the sprite.
     * <p>
     * If the subtype doesn't use its first ASM file, this can return null.
     *
     * @return a nullable {@link String} which doesn't have to be a valid path
     */
    String getFirstASMFile();

    /**
     * Returns the name of the second ASM file used by the sprite.
     * <p>
     * If the subtype doesn't use its second ASM file, this can return null.
     *
     * @return a nullable {@link String} which doesn't have to be a valid path
     */
    String getSecondASMFile();

    /**
     * Returns the display data of the sprite.
     *
     * @return any {@link DisplayData} object which describes how the sprite
     * should display in Lunar Magic
     */
    DisplayData getDisplayData();
}
